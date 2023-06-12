package htms.util;

import htms.repository.ProgramPerClassRepository;
import htms.service.ProgramService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClassUtilTest {
    @Mock
    private ProgramPerClassRepository programPerClassRepository;
    @Mock
    private ProgramService programService;
    @InjectMocks
    private ClassUtil util;

    @Test
    public void generateClassCodeTest() {
//        UUID classId = UUID.fromString("9d95667e-e48f-48c3-ab3b-83c65232002f");
//        UUID programId = UUID.fromString("c2c6401d-b0ab-42b8-af7d-980260fcd963");
//        var actualResult = util.generateClassCode(classId, programId);
//        Assertions.assertEquals("KNO20231", actualResult);
    }
}
