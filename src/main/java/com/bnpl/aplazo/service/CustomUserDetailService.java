package com.bnpl.aplazo.service;

import com.bnpl.aplazo.model.Client;
import com.bnpl.aplazo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Value("${client.api.pass}")
    private String pass;

    @Value("${client.api.user}")
    private String user;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        Client client = clientRepository.findByFirstName(name);
//        if(client == null){
//            throw new UsernameNotFoundException("Client Not Found with name: " + name);
//        }

        return new org.springframework.security.core.userdetails.User(
                user,
                encoder.encode(pass),
                Collections.emptyList()
        );
    }
}
