package htms.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class GroupedApprovalStatus {
    private UUID classId;
    private Date filteredDate;

    public GroupedApprovalStatus(UUID classId, Date filteredDate) {
        this.classId = classId;
        this.filteredDate = filteredDate;
    }

}
