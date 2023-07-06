package htms.service.impl;

import htms.api.response.*;
import htms.common.constants.AttendanceStatus;
import htms.model.Attendance;
import htms.model.Class;
import htms.model.Schedule;
import htms.model.Trainee;
import htms.repository.AttendanceRepository;
import htms.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    private ClassService classService;
    private ProgramService programService;

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setTraineeService(@Lazy TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Autowired
    public void setClassService(@Lazy ClassService classService) {
        this.classService = classService;
    }

    @Autowired
    public void setProgramService(ProgramService programService) {
        this.programService = programService;
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
        // Get all taking classes (just OPENING)
        var classes = classService.getAllCurrentTakingClassesByTrainee(traineeId);
        // Get all taking programs
        var programs = programService.getProgramsTheTraineeIsTaking(traineeId);
        List<TraineeAttendanceResponse> traineeAttendanceResponse = new ArrayList<>();
        for (ProgramResponse program : programs) {
            var attendanceBuilder = TraineeAttendanceResponse.builder();
            for (Class aClass : classes) {
                if (aClass.getProgram().getId().equals(program.getId())) {
                    var list = attendanceRepository.findAllByTraineeIdAndClassId(traineeId, aClass.getId())
                            .orElse(List.of())
                            .stream()
                            .map((element) -> modelMapper.map(element, AttendanceDetail.class))
                            .toList();

                    attendanceBuilder.classId(aClass.getId())
                            .classCode(aClass.getCode())
                            .className(aClass.getName())
                            .attendances(list);
                }
            }

            attendanceBuilder.programId(program.getId())
                    .programName(program.getName())
                    .programCode(program.getCode());

            traineeAttendanceResponse.add(attendanceBuilder.build());
        }
        return traineeAttendanceResponse;
    }
}
