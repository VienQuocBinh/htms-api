package htms.util;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
public class ScheduleUtilTest {
    @InjectMocks
    private ScheduleUtil util;

    @Test
    public void generateListOfScheduleTest() {
        LocalDate startLocalDate = LocalDate.of(2023, 6, 17);
        String generalSchedule = "Start{10:00,MON};Stop{11:00,MON};Start{17:00,TUE};Stop{19:00,TUE};Start{13:00,FRI};Stop{15:00,FRI}";
        UUID trainerId = UUID.randomUUID();
        UUID classId = UUID.randomUUID();
        UUID roomId = UUID.randomUUID();
        int numberOfSlots = 31;

//        ScheduleUtil.createSchedules(classId, trainerId, roomId, generalSchedule, startLocalDate, numberOfSlots);
    }
}
