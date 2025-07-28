package com.bnpl.aplazo.controller;

import com.bnpl.aplazo.dto.AuthRq;
import com.bnpl.aplazo.dto.GenericResponse;
import com.bnpl.aplazo.dto.LoginRs;
import com.bnpl.aplazo.model.Client;
import com.bnpl.aplazo.repository.ClientRepository;
import com.bnpl.aplazo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
//    @Autowired
//    ClientRepository clientRepository;
//    @Autowired
//    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;


    @PostMapping("/signin")
    public GenericResponse<LoginRs> authenticateUser(@RequestBody AuthRq user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUser(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateToken(userDetails.getUsername());
        LoginRs loginRs = new LoginRs(jwtToken, jwtUtils.getExpirationMs());

        GenericResponse<LoginRs> genericResponse = new GenericResponse<>();
        genericResponse.setCode("200");
        genericResponse.setMessage("");
        genericResponse.setResponse(loginRs);

        return genericResponse;
    }
}
