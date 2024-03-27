package no.sivertsensoftware.userregistration.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import no.sivertsensoftware.userregistration.model.User;
import no.sivertsensoftware.userregistration.repository.UserRepository;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findByLastname(String last_name) {
        return userRepository.findByLastname(last_name);
    }

    @PreAuthorize("@userController.isAdmin()")
    public User createUser(User user) {
        User saved = userRepository.save(user);
        return saved;
    }

    @PreAuthorize("@userController.isAdmin()")
    public void updateUser(Long id, User user) {
        userRepository.updateUser(id, user.getFirst_name(), user.getLast_name(), user.getEmail());
    }
    
    @PreAuthorize("@userController.isAdmin()")
    public boolean deleteById(Long id){
        Boolean deleted = userRepository.deleteByLongId(id);
        if (deleted){
            logger.info("----- UserService ----- User with id " + id + " deleted!");
        }
        return deleted;
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

}
