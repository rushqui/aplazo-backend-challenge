package com.bnpl.aplazo.service.client.impl;

import com.bnpl.aplazo.dto.client.ClientRq;
import com.bnpl.aplazo.dto.client.ClientRs;
import com.bnpl.aplazo.dto.GenericResponse;
import com.bnpl.aplazo.exception.BusinessException;
import com.bnpl.aplazo.model.Client;
import com.bnpl.aplazo.model.PayScheme;
import com.bnpl.aplazo.repository.PaySchemeRespository;
import com.bnpl.aplazo.service.client.IClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.bnpl.aplazo.repository.ClientRepository;

import java.time.LocalDate;
import java.time.Period;

@Service
public class ClientService implements IClientService {

    private static Logger logger = LogManager.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    private final PaySchemeRespository paySchemeRespository;

    public ClientService(ClientRepository clientRepository, PaySchemeRespository paySchemeRespository) {
        this.clientRepository = clientRepository;
        this.paySchemeRespository = paySchemeRespository;
    }

    @Override
    public GenericResponse<ClientRs> registerClient(ClientRq clientRq) {
        GenericResponse<ClientRs> response = new GenericResponse<>();
        ClientRs clientRs;
        float creditLine = 0f;

        if (clientRepository.findByFirstNameAndLastNameAndSecondLastName(
                clientRq.getFirstName(),
                clientRq.getLastName(),
                clientRq.getSecondLastName()) != null){

            throw new BusinessException("Name client already exists", "APZ0000333","CLIENT_EXISTS");
        }

        //logica para calcular credit line
        //validar si el cliente es menor de 18 o mayor de 65
        int age = Period.between(clientRq.getDateOfBirth(), LocalDate.now()).getYears();
        if(age < 18 || age >= 65){
            throw new BusinessException("Invalid client age", "APZ0000444" ,"INVALID_AGE");
        }

        if (age <= 25){
            creditLine = 3000.00f;
        } else if (age <= 30) {
            creditLine = 5000.00f;
        } else {
            creditLine = 8000.00f;
        }


        Client client = new Client(clientRq.getFirstName(),
                clientRq.getLastName(),
                clientRq.getSecondLastName(),
                creditLine,
                clientRq.getDateOfBirth());

        client = clientRepository.save(client);

        assignPayScheme(client);

        clientRs = new ClientRs(client.getId(), client.getCreditLine());


        response.setCode("200");
        response.setMessage("Client created successfully");
        response.setResponse(clientRs);
        return response;

    }


    private void assignPayScheme(Client client){

        try {

            PayScheme payScheme;

            //scheme1 if name starts C, L, H
            //scheme 2 if client id is greater than 25
            //apply the first valid rule
            //If no rule applies, scheme2 by default
            if (client.getFirstName().startsWith("C") ||
                    client.getFirstName().startsWith("L") ||
                    client.getFirstName().startsWith("H")){
                payScheme = paySchemeRespository.getReferenceById(1L);


            }else if (client.getId() > 25){

                payScheme = paySchemeRespository.getReferenceById(2L);

            } else{
                payScheme = paySchemeRespository.getReferenceById(2L);
            }

            logger.debug("Assigned scheme: {}", payScheme);

            client.setPayScheme(payScheme);

            clientRepository.save(client);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
