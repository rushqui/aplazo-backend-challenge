package com.bnpl.aplazo.service.client.impl;

import com.bnpl.aplazo.dto.GenericResponse;
import com.bnpl.aplazo.dto.client.ClientRq;
import com.bnpl.aplazo.dto.client.ClientRs;
import com.bnpl.aplazo.exception.BusinessException;
import com.bnpl.aplazo.model.Client;
import com.bnpl.aplazo.model.PayScheme;
import com.bnpl.aplazo.repository.ClientRepository;
import com.bnpl.aplazo.repository.PaySchemeRespository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PaySchemeRespository paySchemeRespository;

    @InjectMocks
    private ClientService clientService;

    private ClientRq clientRq;
    private Client client;
    private PayScheme payScheme;

    @BeforeEach
    void setUp() {
        clientRq = new ClientRq("John", "Doe", "Smith", LocalDate.of(1990, 1, 1));
        client = new Client("John", "Doe", "Smith", 5000.0f, LocalDate.of(1990, 1, 1));
        client.setId(1L);
        payScheme = new PayScheme(5, "monthly", 0.05f);
    }

    @Test
    void registerClient_Success_Age26To30() {
        when(clientRepository.findByFirstNameAndLastNameAndSecondLastName(anyString(), anyString(), anyString()))
                .thenReturn(null);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(paySchemeRespository.getReferenceById(2L)).thenReturn(payScheme);

        GenericResponse<ClientRs> response = clientService.registerClient(clientRq);

        assertNotNull(response);
        assertEquals("200", response.getCode());
        assertEquals("Client created successfully", response.getMessage());
        assertNotNull(response.getResponse());
        assertEquals(1L, response.getResponse().getIdClient());
        assertEquals(5000.0f, response.getResponse().getAssignedCredit());
        
        verify(clientRepository, times(2)).save(any(Client.class));
    }

    @Test
    void registerClient_Success_Age18To25() {
        ClientRq youngClientRq = new ClientRq("Jane", "Doe", "Smith", LocalDate.of(2001, 1, 1));
        Client youngClient = new Client("Jane", "Doe", "Smith", 3000.0f, LocalDate.of(2001, 1, 1));
        youngClient.setId(2L);
        
        when(clientRepository.findByFirstNameAndLastNameAndSecondLastName(anyString(), anyString(), anyString()))
                .thenReturn(null);
        when(clientRepository.save(any(Client.class))).thenReturn(youngClient);
        when(paySchemeRespository.getReferenceById(2L)).thenReturn(payScheme);

        GenericResponse<ClientRs> response = clientService.registerClient(youngClientRq);

        assertEquals(3000.0f, response.getResponse().getAssignedCredit());
    }

    @Test
    void registerClient_Success_AgeOver30() {
        ClientRq olderClientRq = new ClientRq("Bob", "Doe", "Smith", LocalDate.of(1980, 1, 1));
        Client olderClient = new Client("Bob", "Doe", "Smith", 8000.0f, LocalDate.of(1980, 1, 1));
        olderClient.setId(3L);
        
        when(clientRepository.findByFirstNameAndLastNameAndSecondLastName(anyString(), anyString(), anyString()))
                .thenReturn(null);
        when(clientRepository.save(any(Client.class))).thenReturn(olderClient);
        when(paySchemeRespository.getReferenceById(2L)).thenReturn(payScheme);

        GenericResponse<ClientRs> response = clientService.registerClient(olderClientRq);

        assertEquals(8000.0f, response.getResponse().getAssignedCredit());
    }

    @Test
    void registerClient_ThrowsException_ClientAlreadyExists() {
        when(clientRepository.findByFirstNameAndLastNameAndSecondLastName(anyString(), anyString(), anyString()))
                .thenReturn(client);

        BusinessException exception = assertThrows(BusinessException.class, 
                () -> clientService.registerClient(clientRq));
        
        assertEquals("Name client already exists", exception.getMessage());
        assertEquals("APZ0000333", exception.getCode());
        assertEquals("CLIENT_EXISTS", exception.getError());
        
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void registerClient_ThrowsException_ClientTooYoung() {
        ClientRq youngClientRq = new ClientRq("Minor", "Client", "Test", LocalDate.of(2010, 1, 1));
        when(clientRepository.findByFirstNameAndLastNameAndSecondLastName(anyString(), anyString(), anyString()))
                .thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, 
                () -> clientService.registerClient(youngClientRq));
        
        assertEquals("Invalid client age", exception.getMessage());
        assertEquals("APZ0000444", exception.getCode());
        assertEquals("INVALID_AGE", exception.getError());
    }

    @Test
    void registerClient_ThrowsException_ClientTooOld() {
        ClientRq oldClientRq = new ClientRq("Senior", "Client", "Test", LocalDate.of(1950, 1, 1));
        when(clientRepository.findByFirstNameAndLastNameAndSecondLastName(anyString(), anyString(), anyString()))
                .thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, 
                () -> clientService.registerClient(oldClientRq));
        
        assertEquals("Invalid client age", exception.getMessage());
        assertEquals("APZ0000444", exception.getCode());
        assertEquals("INVALID_AGE", exception.getError());
    }

    @Test
    void registerClient_PaySchemeAssignment_NameStartsWithC() {
        ClientRq clientWithC = new ClientRq("Carlos", "Doe", "Smith", LocalDate.of(1990, 1, 1));
        Client savedClient = new Client("Carlos", "Doe", "Smith", 5000.0f, LocalDate.of(1990, 1, 1));
        savedClient.setId(1L);
        
        when(clientRepository.findByFirstNameAndLastNameAndSecondLastName(anyString(), anyString(), anyString()))
                .thenReturn(null);
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);
        when(paySchemeRespository.getReferenceById(1L)).thenReturn(payScheme);

        clientService.registerClient(clientWithC);

        verify(paySchemeRespository).getReferenceById(1L);
    }

    @Test
    void registerClient_PaySchemeAssignment_NameStartsWithL() {
        ClientRq clientWithL = new ClientRq("Luis", "Doe", "Smith", LocalDate.of(1990, 1, 1));
        Client savedClient = new Client("Luis", "Doe", "Smith", 5000.0f, LocalDate.of(1990, 1, 1));
        savedClient.setId(1L);
        
        when(clientRepository.findByFirstNameAndLastNameAndSecondLastName(anyString(), anyString(), anyString()))
                .thenReturn(null);
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);
        when(paySchemeRespository.getReferenceById(1L)).thenReturn(payScheme);

        clientService.registerClient(clientWithL);

        verify(paySchemeRespository).getReferenceById(1L);
    }

    @Test
    void registerClient_PaySchemeAssignment_NameStartsWithH() {
        ClientRq clientWithH = new ClientRq("Hugo", "Doe", "Smith", LocalDate.of(1990, 1, 1));
        Client savedClient = new Client("Hugo", "Doe", "Smith", 5000.0f, LocalDate.of(1990, 1, 1));
        savedClient.setId(1L);
        
        when(clientRepository.findByFirstNameAndLastNameAndSecondLastName(anyString(), anyString(), anyString()))
                .thenReturn(null);
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);
        when(paySchemeRespository.getReferenceById(1L)).thenReturn(payScheme);

        clientService.registerClient(clientWithH);

        verify(paySchemeRespository).getReferenceById(1L);
    }

    @Test
    void registerClient_PaySchemeAssignment_ClientIdGreaterThan25() {
        Client savedClient = new Client("John", "Doe", "Smith", 5000.0f, LocalDate.of(1990, 1, 1));
        savedClient.setId(30L);
        
        when(clientRepository.findByFirstNameAndLastNameAndSecondLastName(anyString(), anyString(), anyString()))
                .thenReturn(null);
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);
        when(paySchemeRespository.getReferenceById(2L)).thenReturn(payScheme);

        clientService.registerClient(clientRq);

        verify(paySchemeRespository).getReferenceById(2L);
    }

    @Test
    void registerClient_PaySchemeAssignment_DefaultScheme() {
        Client savedClient = new Client("John", "Doe", "Smith", 5000.0f, LocalDate.of(1990, 1, 1));
        savedClient.setId(10L);
        
        when(clientRepository.findByFirstNameAndLastNameAndSecondLastName(anyString(), anyString(), anyString()))
                .thenReturn(null);
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);
        when(paySchemeRespository.getReferenceById(2L)).thenReturn(payScheme);

        clientService.registerClient(clientRq);

        verify(paySchemeRespository).getReferenceById(2L);
    }

    @Test
    void registerClient_PaySchemeAssignment_ExceptionHandling() {
        when(clientRepository.findByFirstNameAndLastNameAndSecondLastName(anyString(), anyString(), anyString()))
                .thenReturn(null);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(paySchemeRespository.getReferenceById(anyLong())).thenThrow(new RuntimeException("Database error"));

        GenericResponse<ClientRs> response = clientService.registerClient(clientRq);

        assertNotNull(response);
        assertEquals("200", response.getCode());
    }
}