package ru.pizzahut.pizzamaker.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pizzahut.pizzamaker.controller.payload.UserAuthDto;
import ru.pizzahut.pizzamaker.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserAuthDto userAuthDto) {
        this.authService.saveUser(userAuthDto);

        return ResponseEntity.ok(userAuthDto.name());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody UserAuthDto userAuthDto,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthDto.name(), userAuthDto.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);

        return ResponseEntity.ok(userAuthDto.name());
    }
}
