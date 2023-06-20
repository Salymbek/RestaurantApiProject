package peaksoft.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "restaurant")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @SequenceGenerator(
            name = "restaurant_id_gen",
            sequenceName = "restaurant_id_seq",
            allocationSize = 1)
    @GeneratedValue(
            generator = "restaurant_id_gen",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String name;
    private String location;
    private String restType;
    private int numberOfEmployee;
    private int service;

    @OneToMany( mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MenuItem> menuItems = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<User>users = new ArrayList<>();

}