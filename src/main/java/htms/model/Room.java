package htms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Integer maxCapacity;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Schedule> schedules;
}
