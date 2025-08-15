package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.UserAuthDto;
import ru.pizzahut.pizzamaker.model.User;
import ru.pizzahut.pizzamaker.repo.UserRepository;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByName(username)
                .orElseThrow(() -> new NoSuchElementException("Нет пользователя с таким именем"));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }

    public void saveUser(UserAuthDto userAuthDto) {
        User user = new User();
        user.setName(userAuthDto.name());
        user.setPassword(encoder.encode(userAuthDto.password()));

        this.userRepository.save(user);
    }
}
