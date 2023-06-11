package htms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionBank {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String password;

    //    @OneToMany(mappedBy = "questionBank", fetch = FetchType.LAZY)
//    private List<Question> questions;
    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
}
