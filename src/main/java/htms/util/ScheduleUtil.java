package htms.util;

import htms.api.domain.OverlappedSchedule;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                ZoneId asiaBangkokZone = ZoneId.of("Asia/Bangkok");
                schedule.setDate(java.sql.Date.valueOf(nearestStartDate));
                schedule.setStartTime(Date.from(nearestStartDate.atTime(startPartTime).atZone(asiaBangkokZone).toInstant()));
                schedule.setEndTime(Date.from(nearestStopDate.atTime(stopPartTime).atZone(asiaBangkokZone).toInstant()));
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

    public static OverlappedSchedule getOverlappedSchedule(String classGeneralSchedule, String userGeneralSchedule, UUID userId) {
        List<String> overlappingDayTimes = new ArrayList<>();
        List<String> classSchedule = extractTimesDaysPart(classGeneralSchedule); // ["10:00,MON", "11:00,MON"]
        List<String> userSchedule = extractTimesDaysPart(userGeneralSchedule); // ["10:00,MON", "11:00,MON"]

        for (int i = 0; i < classSchedule.size(); i += 2) {
            for (int j = 0; j < userSchedule.size(); j += 2) {
                if (hasOverlap(classSchedule.get(i), classSchedule.get(i + 1),
                        userSchedule.get(j), userSchedule.get(j + 1))) {
                    overlappingDayTimes.add(userSchedule.get(j) + " - " + userSchedule.get(j + 1));
                }
            }
        }

        return OverlappedSchedule.builder()
                .id(userId)
                .overlappedDayTimes(overlappingDayTimes)
                .build();
    }

    private static List<String> extractTimesDaysPart(String input) {
        List<String> timesAndDays = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{(.*?)}");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String timeAndDay = matcher.group(1);
            timesAndDays.add(timeAndDay);
        }
        return timesAndDays;
    }

    private static boolean hasOverlap(String timeAndDayStart1, String timeAndDayEnd1, String timeAndDayStart2, String timeAndDayEnd2) {
        String[] startPart1 = timeAndDayStart1.split(",");
        String[] endPart1 = timeAndDayEnd1.split(",");

        String[] startTime2 = timeAndDayStart2.split(",");
        String[] endTime2 = timeAndDayEnd2.split(",");

        String startTime1 = startPart1[0];
        String endTime1 = endPart1[0];
        String day1 = startPart1[1];

        String startTime2Part = startTime2[0];
        String endTime2Part = endTime2[0];
        String day2 = startTime2[1];

        return day1.equals(day2) && !(endTime1.compareTo(startTime2Part) <= 0 || endTime2Part.compareTo(startTime1) <= 0);
    }
}
