package htms.api.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import htms.common.constants.ClassApprovalStatus;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassesApprovalResponse {
    private UUID id;
    private String code;
    private String programCode;

    private ClassApprovalStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date createdDate;
}
