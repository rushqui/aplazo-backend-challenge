package com.bnpl.aplazo.dto.client;

public class ClientRs {

    private Long idClient;

    private Float assignedCredit;

    public ClientRs(Long idClient, Float assignedCredit) {
        this.idClient = idClient;
        this.assignedCredit = assignedCredit;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Float getAssignedCredit() {
        return assignedCredit;
    }

    public void setAssignedCredit(Float assignedCredit) {
        this.assignedCredit = assignedCredit;
    }
}
