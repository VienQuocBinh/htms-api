package htms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import htms.model.Department;
import htms.model.Role;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private UUID id;
    private String email;
    private String title;
    private Department department;
    private Role role;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Bangkok")
    private Date createdDate;
    private UUID createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Bangkok")
    private Date updatedDate;
    private UUID updatedBy;
    private boolean isDeleted;
}
