package peaksoft.model;


import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "stop_lists")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopList {
    @Id
    @SequenceGenerator(
            name = "stop_list_id_gen",
            sequenceName = "stop_list_id_seq",
            allocationSize = 1)
    @GeneratedValue(
            generator = "stop_list_id_gen",
            strategy = GenerationType.SEQUENCE
    )

    private Long id;
    private String reason;
    private LocalDate date;
    @ManyToOne(cascade = {PERSIST,REFRESH,MERGE,DETACH})
    private MenuItem menuItems;

}