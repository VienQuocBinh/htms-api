package htms.service.impl;

import htms.api.response.ScheduleResponse;
import htms.api.response.TraineeAttendanceResponse;
import htms.api.response.TraineeResponse;
import htms.common.constants.AttendanceStatus;
import htms.model.Attendance;
import htms.model.Schedule;
import htms.model.Trainee;
import htms.repository.AttendanceRepository;
import htms.service.AttendanceService;
import htms.service.ScheduleService;
import htms.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final ModelMapper modelMapper;
    private ScheduleService scheduleService;
    private TraineeService traineeService;

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setTraineeService(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Override
    public void createAttendancesOfClass(UUID classId) {
        // Get schedule of the class
        var schedules = scheduleService.getScheduleOfClass(classId);
        var trainees = traineeService.getTraineesByClassId(classId);
        List<Attendance> attendances = new ArrayList<>();
        // todo: created by
        for (ScheduleResponse schedule : schedules) {
            for (TraineeResponse trainee : trainees) {
                attendances.add(Attendance.builder()
                        .createdBy(UUID.fromString("3bdb9fdd-40a0-4bd4-93aa-3462c2164d08"))
                        .status(AttendanceStatus.NOT_START)
                        .schedule(Schedule.builder()
                                .id(schedule.getId())
                                .build())
                        .trainee(Trainee.builder()
                                .id(trainee.getId())
                                .build())
                        .build());
            }
        }
        attendanceRepository.saveAll(attendances);
    }

    @Override
    public List<TraineeAttendanceResponse> getAttendancesByClassId(UUID classId) {
        return attendanceRepository.findAllByClassId(classId)
                .orElse(List.of())
                .parallelStream()
                .map((element) -> modelMapper.map(
                        element,
                        TraineeAttendanceResponse.class))
                .toList();
    }

    @Override
    public List<TraineeAttendanceResponse> getAttendancesByTraineeId(UUID traineeId) {
        return attendanceRepository.findAllByTrainee_Id(traineeId)
                .orElse(List.of())
                .parallelStream()
                .map((element) -> modelMapper.map(
                        element,
                        TraineeAttendanceResponse.class))
                .toList();
    }
}
