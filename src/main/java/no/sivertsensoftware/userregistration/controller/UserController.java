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

import no.sivertsensoftware.userregistration.model.User;
import no.sivertsensoftware.userregistration.repository.UserRepository;
import no.sivertsensoftware.userregistration.service.UserService;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public Iterable<User> findAll() { // Principal principal

        // JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        // String userName = (String) token.getTokenAttributes().get("name");
        // String userName = principal.getName();
        // String userEmail = (String) token.getTokenAttributes().get("email");
        // String userEmail = principal.toString();

        // System.out.println(ResponseEntity.ok("\nHello Admin \nUser Name : " +
        // userName + "\nUser Email : " + userEmail));

        return userService.findAll();
    }

    @GetMapping("/api/users/lastname/{last_name}")
    public List<User> findByLastname(@PathVariable("last_name") String last_name) {
        List<User> listOfUsers = userService.findByLastname(last_name);
        return listOfUsers;
    }

    @GetMapping("/api/users/email/{email}")
    public List<User> findByEmail(@PathVariable("email") String email) {
        List<User> listOfUsers = userService.findByEmail(email);
        return listOfUsers;
    }

    @GetMapping("/api/users/{id}")
    public Optional<User> findByID(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/users")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @Transactional
    @DeleteMapping("/api/users/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/api/users/{id}")
    public void update(@RequestBody User user, @PathVariable("id") Long id) {
        if (!userService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found.");
        }
        userService.updateUser(id, user);
    }
}
