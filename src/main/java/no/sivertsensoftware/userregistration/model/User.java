package no.sivertsensoftware.userregistration.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import com.helger.commons.annotation.Nonempty;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Table("usertbl")
public final class User {

    @Id
    //@JsonIgnore
    private Long id;
    
    @NonNull
    @Nonempty
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String first_name;

    @NonNull
    @Nonempty
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String last_name;

    @NonNull
    @Nonempty
    @Email
    private String email;

    public User(Long id, String first_name, String last_name, String email) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}