package com.GestionCommande.Service;

import com.GestionCommande.Entity.User;
import com.GestionCommande.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService, IUserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public String setRole(long id, String role) {
        User user =repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé "));
        user.setRole(role);
        repository.save(user);
        return user.getRole();
    }

    @Override
    public void deleteUser(long id) {
        User user = repository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé "));
        repository.delete(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
