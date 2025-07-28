package com.bnpl.aplazo.model;

import jakarta.persistence.*;

@Entity
public class PayScheme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pay_scheme_id")
    private Long id;

    private Integer payments;

    private String frecuency;

    private Float interest;

    public PayScheme() {
    }

    public PayScheme(Integer payments, String frecuency, Float interest) {
        this.payments = payments;
        this.frecuency = frecuency;
        this.interest = interest;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public String getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(String frecuency) {
        this.frecuency = frecuency;
    }

    public Float getInterest() {
        return interest;
    }

    public void setInterest(Float interest) {
        this.interest = interest;
    }
}
