package peaksoft.model;


import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "menu_items")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {
    @Id
    @SequenceGenerator(
            name = "menu_item_id_gen",
            sequenceName = "menu_item_id_seq",
            allocationSize = 1)
    @GeneratedValue(
            generator = "menu_item_id_gen",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    private Boolean isVegetarian;

    @OneToMany(mappedBy = "menuItems",cascade = ALL, orphanRemoval = true)
    private List<StopList> stopList = new ArrayList<>();

    @ManyToOne(cascade = ALL,fetch = FetchType.EAGER)
    private Subcategory subcategory;

    @ManyToOne(cascade = {PERSIST,REFRESH,MERGE,DETACH})
    private Restaurant restaurant;

    @ManyToMany(cascade = {PERSIST,REFRESH,MERGE,DETACH})
    @JoinTable(name = "menu_items_cheques",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "cheques_id"))
    private List<Cheque>cheques = new ArrayList<>();

    public void addCheque(Cheque cheque){
        if (cheques == null){
            cheques = new ArrayList<>();
        }
        cheques.add(cheque);
    }


}