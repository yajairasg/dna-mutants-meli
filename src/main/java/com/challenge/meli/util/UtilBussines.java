package com.challenge.meli.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class UtilBussines {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilBussines.class);
	
    @Value("${minSize}")
    private static int minSize;
    
    public static final Pattern PATTERN_DNA_MUTANT = Pattern.compile("AAAA|TTTT|CCCC|GGGG");
    
    public static final Pattern PATTERN_CHARACTERES = Pattern.compile("[^ATCG]");

	/**
     * Valida que la cantidad de elementos en el listado y la longitud de los textos sean iguales NxN
     * @param body listado de cadenas de texto
     * @return boolean Retorna true si la cantidad de elementos en el listado y la longitud de los 
     * textos son iguales de lo contrario retorna false
     */
    public static boolean validateElements(List<String> dna) {
    	if(!dna.isEmpty() || dna.equals(null)) {
	    	int dnaSize = dna.size();
	    	if(dnaSize == 0) {
	    		// El listado esta vacio
    	    	logger.info("validateElements:{}", UtilConstants.MESSAGE_STRUCTURE_ERROR + UtilConstants.MESSAGE_DATA_EMPTY_ERROR);
	    		return false;
	    	}
	    	for (String text: dna) {
	    		int lineSize = text.getBytes().length;
	    		if(lineSize != dnaSize) {
	        		// La longitud del listado es diferente a la de los caracteres no es NxN
	    	    	logger.info("validateElements:{}", UtilConstants.MESSAGE_STRUCTURE_ERROR + UtilConstants.MESSAGE_SIZE_ERROR);
	    			return false;
	    		}
	    		if(validateCharacters(text)) {
	    			// Se encuentran caracteres diferentes a A,T,C,G en los textos
	    	    	logger.info("validateElements:{}", UtilConstants.MESSAGE_STRUCTURE_ERROR + UtilConstants.MESSAGE_DATA_ERROR + text);
	    			return false;
	    		}
	        }
	    	//Es un listado válido de ADN
	    	return true;
    	}else {
	    	logger.info("validateElements:{}", UtilConstants.MESSAGE_STRUCTURE_ERROR + UtilConstants.MESSAGE_DATA_EMPTY_ERROR);
    		return false;
    	}
	}

	/**
     * Valida que los textos contengan solo los caracteres A,T,C o G
     * @param text cadenas de texto a verificar
     * @return boolean Retorna true si el texto contiene caracteres diferentes a A,T,C,G 
     * de lo contrario retorna false
     */
    public static boolean validateCharacters(String text) {
        Matcher matcher = PATTERN_CHARACTERES.matcher(text);
        while(matcher.find()) {
            return true;
        }  	
    	return false;
    }
	
    /**
     * Calcula la cantidad de coincidencias en el listado de cadenas de texto
     * @param dna listado de cadenas de texto
     * @return int cantidad de coincidencias
     */
    public static int obtenerCoincidencias(List<String> dna) {
    	int count = 0;
    	for (String text: dna) {
            Matcher matcher = PATTERN_DNA_MUTANT.matcher(text);
            while(matcher.find()) {
                count++;
            }
        }    	
    	return count;
    }

}
