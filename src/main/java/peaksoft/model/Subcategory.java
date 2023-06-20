package peaksoft.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Setter
@Getter
@Table(name = "subcategories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subcategory {
    @Id
    @SequenceGenerator(name = "subcategory_id_gen", sequenceName = "subcategory_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "subcategory_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @ManyToOne(cascade = {PERSIST,REFRESH,MERGE,DETACH},fetch = FetchType.EAGER)
    private Category category;

    @OneToMany(mappedBy = "subcategory",cascade = ALL,orphanRemoval = true)
    private List<MenuItem>menuItems = new ArrayList<>();

}