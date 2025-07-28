package com.bnpl.aplazo.controller;

import com.bnpl.aplazo.dto.client.ClientRq;
import com.bnpl.aplazo.dto.client.ClientRs;
import com.bnpl.aplazo.dto.GenericResponse;
import com.bnpl.aplazo.exception.BusinessException;
import com.bnpl.aplazo.service.client.IClientService;
import com.bnpl.aplazo.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<ClientRs> registerClient(@RequestBody ClientRq clientRq){

        System.out.println(clientRq.getDateOfBirth());

        if(!ValidateUtil.isValidClientRq(clientRq)){
            throw new BusinessException("Invalid data types or format in the request", "APZ0001111","INVALID_REQUEST");
        }

        return clientService.registerClient(clientRq);

    }


}
