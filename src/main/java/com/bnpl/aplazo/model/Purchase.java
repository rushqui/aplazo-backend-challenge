package com.bnpl.aplazo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "purchase_id")
    private Long id;

    @Column(name = "purchase_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime purchaseDate;

    @Column(name = "total_amount")
    private Float totalAmount;

    @Column(name = "parcial_amount")
    private Float parcialAmount;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id_client")
    private Client client;

    public Purchase(LocalDateTime purchaseDate, Float totalAmount, Float parcialAmount, Client client) {
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
        this.parcialAmount = parcialAmount;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Float getParcialAmount() {
        return parcialAmount;
    }

    public void setParcialAmount(Float parcialAmount) {
        this.parcialAmount = parcialAmount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
