package com.challenge.meli.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResponseStatsDTO {

	@JsonProperty("count_mutant_dna")
    private int countMutan;
	
    @JsonProperty("count_human_dna")
    private int countHuman;
    
    private BigDecimal ratio;
	
	public ResponseStatsDTO(int countMutan, int countHuman, BigDecimal ratio) {
		this.countMutan = countMutan;
		this.countHuman = countHuman;
		this.ratio = ratio;
	}

}
