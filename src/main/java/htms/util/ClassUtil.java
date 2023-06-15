package htms.util;

import htms.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClassUtil {
    private final ProgramService programService;

    public String generateClassCode(UUID programId, Date programStartDate) {
        String result = "";
        var departmentCode = programService.getProgramDetails(programId).getDepartment().getCode();
//        Long index = programPerClassRepository.countAllById_Program_Id(programId).orElse(0L);
//        result.append(departmentCode);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(programStartDate);
//        result.append(calendar.get(Calendar.YEAR) % 100);
//        if (index / 10 == 0) {
//            result.append(0);
//        }
//        result.append(index);
        return result;
    }
}
