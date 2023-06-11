package htms.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(length = 1000)
    private String description;
    private Integer duration;
    private Integer unitNo;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
}
