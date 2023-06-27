package htms.service.impl;

import htms.api.response.ScheduleResponse;
import htms.model.Schedule;
import htms.repository.ScheduleRepository;
import htms.service.ScheduleService;
import htms.util.ScheduleUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ModelMapper modelMapper;
    private final ScheduleRepository scheduleRepository;

    @Override
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

    @Override
    public List<ScheduleResponse> getScheduleOfClass(UUID id) {
        return scheduleRepository.findAllByClazz_Id(id)
                .orElse(List.of())
                .stream()
                .map((element) -> modelMapper.map(
                        element,
                        ScheduleResponse.class))
                .toList();
    }

    @Override
    public List<ScheduleResponse> getScheduleOfTrainer(UUID id) {
        return scheduleRepository.findAllByTrainer_Id(id)
                .orElse(List.of())
                .stream()
                .map((element) -> modelMapper.map(
                        element,
                        ScheduleResponse.class))
                .toList();
    }
}
