package no.sivertsensoftware.userregistration.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import no.sivertsensoftware.userregistration.model.User;
import no.sivertsensoftware.userregistration.repository.UserRepository;

@Service
public class UserService {

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

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Long id, User user) {
        userRepository.updateUser(id, user.getFirst_name(), user.getLast_name(), user.getEmail());
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

}
