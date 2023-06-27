package htms.service.impl;

import htms.model.Schedule;
import htms.repository.ScheduleRepository;
import htms.service.ScheduleService;
import htms.util.ScheduleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableAsync
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Override
    @Async
    public void createSchedulesOfClass(UUID classId, UUID trainerId, UUID roomId, String generalSchedule, Date startDate, Date endDate) {
        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int day = start.getDayOfMonth();
        int month = start.getMonthValue();
        int year = start.getYear();
        LocalDate startLocalDate = LocalDate.of(year, month, day);
        // todo: get number of slots from syllabus
        int numberOfSlots = 30;

        // Todo: create by uuid
        List<Schedule> schedules = ScheduleUtil.createSchedules(classId, trainerId, roomId, generalSchedule, startLocalDate, numberOfSlots);

        scheduleRepository.saveAll(schedules);
    }
}
