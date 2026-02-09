package com.barAndRestaurants.service;

import com.barAndRestaurants.model.AppUser;
import com.barAndRestaurants.model.Role;
import com.barAndRestaurants.payload.JwtResponse;
import com.barAndRestaurants.payload.LoginRequest;
import com.barAndRestaurants.payload.SignupRequest;
import com.barAndRestaurants.repository.AppUserRepository;
import com.barAndRestaurants.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager, AppUserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            roles);
    }

    public void registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already taken!");
        }

        // Create new user's account
        AppUser user = new AppUser();
        // keep username field in sync for backward compatibility
        user.setUsername(signUpRequest.getEmail());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Role role = Role.ROLE_USER; // Default

        if (strRoles != null && !strRoles.isEmpty()) {
             // Simple logic: if admin is requested, give admin. In production, this should be guarded.
             if (strRoles.contains("admin") || strRoles.contains("ROLE_ADMIN")) {
                 role = Role.ROLE_ADMIN;
             }
        }

        user.setRole(role);
        userRepository.save(user);
    }
}
