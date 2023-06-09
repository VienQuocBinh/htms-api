package htms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Trainer extends BaseEntityAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    @Column(unique = true)
    private String code;
    @NotNull
    private String phone;
    private Date birthdate;

    @OneToOne
    private Account account;
    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    private List<Schedule> schedules;
    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    private List<Class> classes;
    @OneToMany(mappedBy = "id.trainer")
    private List<ProgramContent> programContents;
    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    private List<QuestionCategory> questionCategories;
    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    private List<Question> questions;
    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    private List<Quiz> quizzes;
    public Trainer(BaseEntityAuditingBuilder<?, ?> builder, UUID id, @NotNull String name, @NotNull String phone, Date birthdate, Account account) {
        super(builder);
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.birthdate = birthdate;
        this.account = account;
    }
}
