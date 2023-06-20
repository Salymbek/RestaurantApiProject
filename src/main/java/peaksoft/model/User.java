package peaksoft.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peaksoft.enums.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_id_gen",
            sequenceName = "user_id_seq",
            allocationSize = 1)
    @GeneratedValue(
            generator = "user_id_gen",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private long dateOfBirth;
    @Column(unique = true, name = "email")
    private String email;
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private int experience;

    private Boolean accept;

    @OneToMany(mappedBy = "user",cascade = {PERSIST, MERGE, REFRESH, DETACH},orphanRemoval = true)
    private List<Cheque>cheques = new ArrayList<>();

    @ManyToOne(cascade = {PERSIST,REFRESH,MERGE,DETACH})
    private Restaurant restaurant;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }


    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
