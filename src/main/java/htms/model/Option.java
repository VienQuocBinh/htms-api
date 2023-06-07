package htms.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;
    private Character optionChar; // A/B/C/D
    private Boolean isRightAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
