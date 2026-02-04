// package com.example.security.controller;

// import com.example.security.dto.LoginRequest;
// import com.example.security.dto.SignupRequest;
// import com.example.security.dto.JwtResponse;
// import com.example.security.dto.MessageResponse;
// import com.example.security.model.Role;
// import com.example.security.model.RoleType;
// import com.example.security.model.User;
// import com.example.security.repository.RoleRepository;
// import com.example.security.repository.UserRepository;
// import com.example.security.security.JwtTokenProvider;
// import com.example.security.service.CustomUserDetails;
// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.*;

// import java.util.HashSet;
// import java.util.Set;
// import java.util.stream.Collectors;

// @RestController
// @RequestMapping("/api/auth")
// @RequiredArgsConstructor
// @CrossOrigin(origins = "*", maxAge = 3600)
// public class AuthController {

//     private final AuthenticationManager authenticationManager;
//     private final UserRepository userRepository;
//     private final RoleRepository roleRepository;
//     private final PasswordEncoder passwordEncoder;
//     private final JwtTokenProvider jwtTokenProvider;

//     /**
//      * Login endpoint - authenticates user and returns JWT token
//      */
//     @PostMapping("/login")
//     public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

//         // Authenticate the user
//         Authentication authentication = authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(
//                         loginRequest.getUsername(),
//                         loginRequest.getPassword()
//                 )
//         );

//         // Set authentication in security context
//         SecurityContextHolder.getContext().setAuthentication(authentication);

//         // Generate JWT token
//         String jwt = jwtTokenProvider.generateToken(authentication);

//         // Get user details
//         CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//         Set<String> roles = userDetails.getAuthorities().stream()
//                 .map(GrantedAuthority::getAuthority)
//                 .collect(Collectors.toSet());

//         // Return JWT response
//         return ResponseEntity.ok(new JwtResponse(
//                 jwt,
//                 userDetails.getId(),
//                 userDetails.getUsername(),
//                 userDetails.getEmail(),
//                 roles
//         ));
//     }

//     /**
//      * Signup endpoint - registers new user
//      */
//     @PostMapping("/signup")
//     public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

//         // Check if username already exists
//         if (userRepository.existsByUsername(signupRequest.getUsername())) {
//             return ResponseEntity.badRequest()
//                     .body(new MessageResponse("Error: Username is already taken!"));
//         }

//         // Check if email already exists
//         if (userRepository.existsByEmail(signupRequest.getEmail())) {
//             return ResponseEntity.badRequest()
//                     .body(new MessageResponse("Error: Email is already in use!"));
//         }

//         // Create new user
//         User user = new User(
//                 signupRequest.getUsername(),
//                 signupRequest.getEmail(),
//                 passwordEncoder.encode(signupRequest.getPassword())
//         );

//         // Assign roles
//         Set<String> strRoles = signupRequest.getRoles();
//         Set<Role> roles = new HashSet<>();

//         if (strRoles == null || strRoles.isEmpty()) {
//             // Default role is USER
//             Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
//                     .orElseThrow(() -> new RuntimeException("Error: Role not found."));
//             roles.add(userRole);
//         } else {
//             strRoles.forEach(role -> {
//                 switch (role.toLowerCase()) {
//                     case "admin":
//                         Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
//                                 .orElseThrow(() -> new RuntimeException("Error: Role not found."));
//                         roles.add(adminRole);
//                         break;
//                     case "mod":
//                     case "moderator":
//                         Role modRole = roleRepository.findByName(RoleType.ROLE_MODERATOR)
//                                 .orElseThrow(() -> new RuntimeException("Error: Role not found."));
//                         roles.add(modRole);
//                         break;
//                     default:
//                         Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
//                                 .orElseThrow(() -> new RuntimeException("Error: Role not found."));
//                         roles.add(userRole);
//                 }
//             });
//         }

//         user.setRoles(roles);
//         userRepository.save(user);

//         return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//     }
// }