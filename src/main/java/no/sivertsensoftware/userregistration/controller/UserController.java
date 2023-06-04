package no.sivertsensoftware.userregistration.controller;

import java.util.List;
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
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    public Iterable<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/lastname/{last_name}")
    public List<User> findByLastname(@PathVariable("last_name") String last_name){
        List<User> listOfUsers = userService.findByLastname(last_name);
        return listOfUsers;
    }

    @GetMapping("/email/{email}")
    public List<User> findByEmail(@PathVariable("email") String email){
        List<User> listOfUsers = userService.findByEmail(email);
        return listOfUsers;
    }

    @Deprecated
    @GetMapping("/{id}")
    public User findByID(@PathVariable("id") Long id){
        return userService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id){
        userRepository.deleteById(id);
    }

    //@ResponseStatus(HttpStatus.NO_CONTENT)
    // @PutMapping("/{id}")
    // public void update(@RequestBody User user, @PathVariable("id") Long id){
    //     //userRepository.updateUser(id, first_name, last_name, email);
    //     if(!userRepository.existsById(id)) {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found.");
    //     }
    //     userRepository.save(user);
    // }

    @Deprecated
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody User user, @PathVariable("id") Long id) {
        if(!userService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found.");
        }
        userService.updateUser(id, user);
    }
}
