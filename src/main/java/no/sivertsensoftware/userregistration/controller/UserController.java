package no.sivertsensoftware.userregistration.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.hilla.BrowserCallable;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import no.sivertsensoftware.userregistration.model.User;
import no.sivertsensoftware.userregistration.repository.UserRepository;
import no.sivertsensoftware.userregistration.service.UserService;

@BrowserCallable
@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userService = userService;
    }

    @RolesAllowed("ADMIN")
    public boolean isAdmin() {
        return true;
    }

    @PermitAll
    @RolesAllowed({"USERS", "ADMIN"})
    @GetMapping("/api/users")
    public Iterable<User> findAll() {
        return userService.findAll();
    }

    @RolesAllowed({"USERS", "ADMIN"})
    @GetMapping("/api/users/lastname/{last_name}")
    public List<User> findByLastname(@PathVariable("last_name") String last_name) {
        List<User> listOfUsers = userService.findByLastname(last_name);
        return listOfUsers;
    }

    @RolesAllowed({"USERS", "ADMIN"})
    @GetMapping("/api/users/email/{email}")
    public List<User> findByEmail(@PathVariable("email") String email) {
        List<User> listOfUsers = userService.findByEmail(email);
        return listOfUsers;
    }

    @RolesAllowed({"USERS", "ADMIN"})
    @GetMapping("/api/users/{id}")
    public Optional<User> findByID(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @RolesAllowed("ADMIN")
    @PermitAll
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/users")
    public User createUser(@RequestBody User user) {
        User saved = userService.createUser(user);
        return saved;
    }

    @RolesAllowed("ADMIN")
    @Transactional
    @DeleteMapping("/api/users/{id}")
    public boolean deleteById(@PathVariable("id") Long id) {
        return userService.deleteById(id);
    }

    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/api/users/{id}")
    public void update(@RequestBody User user, @PathVariable("id") Long id) {
        if (!userService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found.");
        }
        userService.updateUser(id, user);
    }
}
