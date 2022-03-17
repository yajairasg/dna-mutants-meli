package com.challenge.meli.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "dna_stats")
public class DnaStats {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String dna;

    @Column
    private String isMutant;
}