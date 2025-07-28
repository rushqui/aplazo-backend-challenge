package com.bnpl.aplazo.repository;

import com.bnpl.aplazo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByFirstName(String name);

    Client findByFirstName(String name);

    Client findByFirstNameAndLastNameAndSecondLastName(String firstName, String lastName,String secondLastName);
}
