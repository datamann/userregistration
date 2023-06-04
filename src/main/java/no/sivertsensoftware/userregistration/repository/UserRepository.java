package no.sivertsensoftware.userregistration.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import no.sivertsensoftware.userregistration.model.User;

@Repository
public class UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static User mapRow (ResultSet rs, int rowNum) throws SQLException {
        return new User (
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email")
        );
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM usertbl";
        return jdbcTemplate.query(sql, UserRepository::mapRow);
    }

    @Deprecated
    public User findById(Long id){
        String sql = "SELECT * FROM usertbl WHERE id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new User(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                ));
    }

    @Deprecated
    public List<User> findByLastname(String last_name){
        String sql = "SELECT * FROM usertbl WHERE last_name=?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{last_name}, UserRepository::mapRow);
        return users;
    }

    @Deprecated
    public List<User> findByEmail(String email){
        String sql = "SELECT * FROM usertbl WHERE email=?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{email}, UserRepository::mapRow);
        return users;
    }

    public void createUser(User user){
        String sql = "INSERT INTO usertbl (first_name, last_name, email) VALUES (?,?,?)";
        jdbcTemplate.update(sql, user.getFirst_name(), user.getLast_name(), user.getEmail());
    }

    public void updateUser(Long id, User user) {
        String sql = "UPDATE usertbl SET first_name=?, last_name=?, email=? WHERE id=?";
        jdbcTemplate.update(sql, user.getFirst_name(), user.getLast_name(), user.getEmail(), id);
    }

    public void deleteById(Long id){
        String sql = "DELETE FROM usertbl WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Deprecated
    public boolean existsById(Long id){
        String sql = "SELECT count(*) FROM usertbl WHERE id=?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count > 0;
    }
}
