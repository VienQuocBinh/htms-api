package htms.util;

import htms.repository.ClassApprovalRepository;
import htms.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClassUtil {
    private final ProgramService programService;
    private final ClassApprovalRepository classApprovalRepository;

    public String generateClassCode(UUID programId, Date startDate) {
        StringBuilder result = new StringBuilder();
        var departmentCode = programService.getProgramDetails(programId).getDepartment().getCode();
        // count all by program id
        Long index = 1L;
        Optional<Long> latestId = classApprovalRepository.getLatestId();
        if (latestId.isPresent()) {
            index = latestId.get() + 1;
        }
        result.append(departmentCode);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        result.append(calendar.get(Calendar.YEAR) % 100);
        if (index / 10 == 0) {
            result.append(0);
        }
        result.append(index);
        return result.toString();
    }
}
