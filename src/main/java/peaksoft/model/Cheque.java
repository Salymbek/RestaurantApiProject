package peaksoft.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "cheques")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cheque {
    @Id
    @SequenceGenerator(
            name = "cheque_id_gen",
            sequenceName = "cheque_id_seq",
            allocationSize = 1)
    @GeneratedValue(
            generator = "cheque_id_gen",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private LocalDate createdAt;

    @ManyToOne(cascade = {PERSIST,REFRESH,MERGE,DETACH})
    private User user;

    @ManyToMany(mappedBy = "cheques",cascade = {PERSIST,REFRESH,MERGE,DETACH})
    private List<MenuItem>menuItems = new ArrayList<>();

}