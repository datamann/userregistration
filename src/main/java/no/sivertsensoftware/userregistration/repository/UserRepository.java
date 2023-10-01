package no.sivertsensoftware.userregistration.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import no.sivertsensoftware.userregistration.model.User;


public interface UserRepository extends CrudRepository<User, Long> {
    
    @Modifying
    @Query("Delete FROM usertbl WHERE id = :id")
    boolean deleteByLongId(@Param("id") Long id);

    @Query("SELECT * FROM usertbl WHERE email = :email")
    List<User> findByEmail(String email);

    @Query("SELECT * FROM usertbl WHERE last_name = :last_name")
    List<User> findByLastname(@Param("last_name") String last_name);

    @Modifying
    @Query("UPDATE usertbl SET first_name = :first_name, last_name = :last_name, email = :email WHERE id = :id")
    void updateUser(@Param("id") Long id, @Param("first_name") String first_name, @Param("last_name") String last_name, @Param("email") String email);

}
