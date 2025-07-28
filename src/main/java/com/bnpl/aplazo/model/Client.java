package com.bnpl.aplazo.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_client")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "scond_last_name")
    private String secondLastName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "credit_line")
    private float creditLine;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "pay_scheme_id", referencedColumnName = "pay_scheme_id")
    private PayScheme payScheme;

    public Client() {
    }

    public Client(String firstName, String lastName, String secondLastName, float creditLine, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.createdAt = LocalDateTime.now();
        this.creditLine = creditLine;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public float getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(float creditLine) {
        this.creditLine = creditLine;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PayScheme getPayScheme() {
        return payScheme;
    }

    public void setPayScheme(PayScheme payScheme) {
        this.payScheme = payScheme;
    }
}
