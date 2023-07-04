package htms.util;

import htms.repository.ClassRepository;
import htms.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClassUtil {
    private final ProgramService programService;
    private final ClassRepository classRepository;

    public String generateClassCode(UUID programId, Date startDate) {
        StringBuilder result = new StringBuilder();
        var departmentCode = programService.getProgramDetails(programId).getDepartment().getCode();
        result.append(departmentCode);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        result.append(calendar.get(Calendar.YEAR) % 100);
        // Indexing the class by older classes in the year
        int index = classRepository.findAllByCodeStartsWith(result.toString())
                .orElse(List.of())
                .size();
        if (index / 10 == 0) {
            result.append(0);
        }
        result.append(index);
        return result.toString();
    }
}
