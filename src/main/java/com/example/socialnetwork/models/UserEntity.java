package com.example.socialnetwork.models;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user_entity", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id","username", "password", "email", "name", "surname", "online", "city"})
@EqualsAndHashCode(of = {"id","username", "password", "email", "name", "surname", "online", "city"})
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 7, message = "Username must be between 3 and 7 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
//    @Size(min = 5, max = 30, message = "Password must be between 5 and 30 characters")
    private String password;

    @Transient
    private String matchingPassword;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;

    @NotNull(message = "Surname cannot be null")
    @Size(min = 3, max = 30, message = "Surname must be between 3 and 30 characters")
    private String surname;

    private String avatar;
    private String cover;
    private Boolean online = false;
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String city;

    @OneToMany(mappedBy = "user")
    private List<Note> notes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
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

