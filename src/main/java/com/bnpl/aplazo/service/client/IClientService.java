package com.bnpl.aplazo.service.client;

import com.bnpl.aplazo.dto.client.ClientRq;
import com.bnpl.aplazo.dto.client.ClientRs;
import com.bnpl.aplazo.dto.GenericResponse;

public interface IClientService {

    GenericResponse<ClientRs> registerClient(ClientRq clientRq);
}
