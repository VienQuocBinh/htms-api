package htms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "answer_text", columnDefinition = "text")
    @NotNull(message = "answer cannot be null")
    private String answerText;

    @Column(name = "fraction", columnDefinition = "double precision")
    @DecimalMax(value = "1", message = "Fraction cannot larger than 100%")
    @DecimalMin(value = "-1", message = "Fraction cannot smaller than -100%")
    private double fraction;

    @Column(name = "position")
    private int position;

    @Column(name = "feedback", columnDefinition = "text")
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "question")
    private Question question;

}
