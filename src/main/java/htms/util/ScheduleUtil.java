package htms.util;

import htms.model.Class;
import htms.model.Room;
import htms.model.Schedule;
import htms.model.Trainer;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class ScheduleUtil {

    public static List<Schedule> createSchedules(UUID classId, UUID trainerId, UUID roomId, String scheduleString, LocalDate startDate, int numberOfSchedules) {
        List<Schedule> schedules = new ArrayList<>();

        String[] scheduleParts = scheduleString.split(";");
        LocalDate currentDate = startDate;

        while (schedules.size() < numberOfSchedules) {
            for (int j = 0; j < scheduleParts.length; j += 2) {
                String startPart = scheduleParts[j];
                String stopPart = scheduleParts[j + 1];

                String[] startPartTimeAndWeekday = extractTimeAndWeekday(startPart);
                String[] stopPartTimeAndWeekday = extractTimeAndWeekday(stopPart);

                LocalTime startPartTime = LocalTime.parse(startPartTimeAndWeekday[0]);
                LocalTime stopPartTime = LocalTime.parse(stopPartTimeAndWeekday[0]);

                DayOfWeek startPartWeekday = convertToWeekDay(startPartTimeAndWeekday[1]);
                DayOfWeek stopPartWeekday = convertToWeekDay(stopPartTimeAndWeekday[1]);

                // Find the nearest date that matches the start time and weekday
                LocalDate nearestStartDate = currentDate.with(TemporalAdjusters.nextOrSame(startPartWeekday));
                if (nearestStartDate.getDayOfWeek() != startPartWeekday || nearestStartDate.atTime(startPartTime).isBefore(LocalDateTime.now())) {
                    nearestStartDate = currentDate.with(TemporalAdjusters.next(startPartWeekday));
                }

                // Find the nearest date that matches the stop time and weekday
                LocalDate nearestStopDate = currentDate.with(TemporalAdjusters.nextOrSame(stopPartWeekday));
                if (nearestStopDate.getDayOfWeek() != stopPartWeekday || nearestStopDate.atTime(stopPartTime).isBefore(LocalDateTime.now())) {
                    nearestStopDate = currentDate.with(TemporalAdjusters.next(stopPartWeekday));
                }

                Schedule schedule = new Schedule();
                schedule.setId(UUID.randomUUID());
                schedule.setClazz(Class.builder()
                        .id(classId)
                        .build());
                schedule.setTrainer(Trainer.builder()
                        .id(trainerId)
                        .build());
                schedule.setRoom(Room.builder()
                        .id(roomId)
                        .build());
                schedule.setDate(java.sql.Date.valueOf(nearestStartDate));
                schedule.setStartTime(Date.from(nearestStartDate.atTime(startPartTime).atZone(ZoneId.systemDefault()).toInstant()));
                schedule.setEndTime(Date.from(nearestStopDate.atTime(stopPartTime).atZone(ZoneId.systemDefault()).toInstant()));
                // Todo: create by uuid
                schedule.setCreatedBy(UUID.fromString("b3a3aa34-0c11-4a0b-b036-f146f8c15aa3"));

                schedules.add(schedule);
            }

            currentDate = currentDate.plusWeeks(1);
        }

        // Remove redundant schedules
        if (schedules.size() > numberOfSchedules) {
            int difference = schedules.size() - numberOfSchedules;
            for (int i = 1; i <= difference; i++) {
                schedules.remove(schedules.size() - i);
            }
        }

        return schedules;
    }

    private static String[] extractTimeAndWeekday(String schedulePart) {
        String extractedValue = schedulePart.substring(schedulePart.indexOf("{") + 1, schedulePart.indexOf("}"));
        return extractedValue.split(",");
    }

    private static DayOfWeek convertToWeekDay(String weekDay) {
        return switch (weekDay) {
            case "Monday" -> DayOfWeek.MONDAY;
            case "Tuesday" -> DayOfWeek.TUESDAY;
            case "Wednesday" -> DayOfWeek.WEDNESDAY;
            case "Thursday" -> DayOfWeek.THURSDAY;
            case "Friday" -> DayOfWeek.FRIDAY;
            case "Saturday" -> DayOfWeek.SATURDAY;
            case "Sunday" -> DayOfWeek.SUNDAY;
            default -> null;
        };
    }
}
