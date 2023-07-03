package htms.api.domain;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailDetails {
    private String recipient;
    private String subject;
    private String body;
    private String attachment;
}
