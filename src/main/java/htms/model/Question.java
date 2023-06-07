package htms.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private char singleAnswerInput;
    @Range(min = 0, max = 10)
    private double mark;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "test_question",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id"))
    private List<Test> tests;
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<Option> options;
    @ManyToOne
    @JoinColumn(name = "question_bank_id")
    private QuestionBank questionBank;
}
