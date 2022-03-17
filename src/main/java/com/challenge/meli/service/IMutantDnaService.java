package com.challenge.meli.service;

import java.util.List;

import com.challenge.meli.dto.ResponseStatsDTO;

public interface IMutantDnaService {
	
	public boolean isMutant(List<String> body);
	
	ResponseStatsDTO getDnaStats();

}
