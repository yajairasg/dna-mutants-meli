package com.challenge.meli.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.meli.dto.RequestMutantDTO;
import com.challenge.meli.service.IMutantDnaService;
import com.challenge.meli.util.UtilBussines;
import com.challenge.meli.util.UtilConstants;

/**
 * Api Humano Mutante desarrollada para el challenge de Mercado Libre
 * @author Yajaira Sanabria
 * 
 * */

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class MutantDnaController {
	
	private static final Logger logger = LoggerFactory.getLogger(MutantDnaController.class);
	
	@Autowired
    private IMutantDnaService mutantDnaService;
	
    /**
     * Este servicio que detecta si un humano es un mutante de acuerdo a su ADN
     * @param request contiene un arreglo de textos que representan cada fila de 
     * la tabla de NxN con la secuencia del ADN
     * @return ResponseEntity contiene la respuesta del consumo del servicio
     */
    @PostMapping(value="/mutant", produces = "application/json")
    public ResponseEntity<Object> isMutant(@RequestBody RequestMutantDTO request) {
    	logger.info("isMutant():{}","isMutant");
    	Map<String, Object> response = new HashMap<>();
    	try {
	    	HttpStatus httpStatusIsMutant = HttpStatus.FORBIDDEN;
	    	List<String> dna = request.getDna();
	    	boolean validStructure = UtilBussines.validateElements(dna);
	    	logger.info("validStructure():{}",validStructure);
    		if (validStructure) {
            	if (this.mutantDnaService.isMutant(dna)) {
            		response.put("message", UtilConstants.MESSAGE_IS_MUTANT);
                	logger.info(UtilConstants.MESSAGE_IS_MUTANT,response.toString());
                	return new ResponseEntity<>(response, HttpStatus.OK);
            	} else {
            		response.put("message", UtilConstants.MESSAGE_IS_NOT_MUTANT);
                	logger.info(UtilConstants.MESSAGE_IS_NOT_MUTANT,response.toString());
                	return new ResponseEntity<>(response, httpStatusIsMutant);    		
            	}
    		} else {
        		response.put("message", UtilConstants.MESSAGE_STRUCTURE_ERROR);
            	logger.info(UtilConstants.MESSAGE_STRUCTURE_ERROR,response.toString());
    			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); 
    		}
    		
    	} catch (Exception e) {
    		response.put("message", e.getMessage());
        	logger.error("Exception():{}",response.toString());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); 
    	}
    	
    }

	/**
     * Este servicio consulta las estadisticas de las verificaciones de ADN
     * @return ResponseEntity contiene la respuesta del consumo del servicio
     */
    @GetMapping(value="/stats", produces = "application/json")
    public ResponseEntity<Object> getDnaVerificationsStats(){

        return ResponseEntity.ok(mutantDnaService.getDnaStats());
    }

}
