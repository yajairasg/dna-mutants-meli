package com.challenge.meli.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.challenge.meli.dto.ResponseStatsDTO;
import com.challenge.meli.model.DnaStats;
import com.challenge.meli.repository.DnaRepository;
import com.challenge.meli.service.IMutantDnaService;
import com.challenge.meli.util.UtilBussines;

@Service
public class MutantDnaImpl implements IMutantDnaService{
	
	private static final Logger logger = LoggerFactory.getLogger(MutantDnaImpl.class);

    @Value("${minSecuences}")
    private int minSecuences;
    
    @Autowired
    private DnaRepository dnaRepository;
    
    public static final String MUTANTE = "Mutante";
    public static final String HUMANO = "Humano";
    
    /**
     * Se encarga de hacer el llamado a los metodos que verificacion del ADN
     * @param body es el listado con la secuencia de ADN verificada
     * @return boolean valor que indica si es mutante = true o si es humano = false
     * */
	@Override
	public boolean isMutant(List<String> body) {
		boolean isMutantDna = false;
		isMutantDna = this.findSequences(body);
        this.saveDnaVerified(isMutantDna ? MUTANTE : HUMANO, body);
    	logger.info("isMutantDna:{}",isMutantDna);
		return isMutantDna;
	}
	
    /**
     * Se encarga de hacer el calculo del promedio de las verificaciones del ADN 
     * @return ResponseStatsDTO objeto que contiene las estadisticas de verificaciones del ADN
     * */
	@Override
	public ResponseStatsDTO getDnaStats() {
		
        int mutantsStats = dnaRepository.getDnaStats(MUTANTE);
        int humansStats = dnaRepository.getDnaStats(HUMANO);
        BigDecimal ratio;
        try {
    		BigDecimal mutants = new BigDecimal(mutantsStats);
    		BigDecimal total = new BigDecimal(mutantsStats + humansStats);
            ratio = mutants.divide(total,1, RoundingMode.HALF_EVEN);
        }catch (ArithmeticException ex){
        	logger.error("ArithmeticException:{}",ex);
            ratio = BigDecimal.valueOf(mutantsStats);
        }
        ResponseStatsDTO response = new ResponseStatsDTO(mutantsStats,mutantsStats + humansStats,ratio);
    	logger.info("response:{}",response);
        return response;
	}
	


    /**
     * Se encarga de orquestar la validacion de coincidencias de secuencias en el ADN, de manera 
     * horizontal, vertical y oblicua.
     * @param body es el listado con la secuencia de ADN a verificar
     * @return boolean indica si se encuentra mas de una secuencia de cuatro letras iguales finaliza 
     * el proceso y retorna true, de lo contrario retorna false.
     * */
	private boolean findSequences(List<String> body) {

    	logger.info("findSequences:{}",body.toString());
		
		// Calcular secuencias horizontales
		int totalHorizontal = UtilBussines.obtenerCoincidencias(body);
    	logger.info("totalHorizontal:{}",totalHorizontal);
		if(totalHorizontal >= minSecuences) {
			return true;
		}
		
		// Calcular secuencias Verticales
		int totalVertical = UtilBussines.obtenerCoincidencias(this.createListsVertical(body));
    	logger.info("totalVertical:{}",totalVertical);
		if((totalHorizontal + totalVertical) >= minSecuences) {
			return true;
		}

		// Calcular secuencias Oblicuas
		int totalObliquo = UtilBussines.obtenerCoincidencias(this.createListsOblicuous(body));
    	logger.info("totalObliquo:{}",totalObliquo);
		if((totalHorizontal + totalVertical + totalObliquo) >= minSecuences) {
			return true;
		}
		
		return false;
	}
	
    /**
     * Se encarga crear listado de secuencias en el ADN de manera oblicua.
     * @param body es el listado con la secuencia de ADN .
     * @return listado con la secuencia de ADN de manera oblicua.
     * */
	private List<String> createListsOblicuous(List<String> body) {
		int size = body.size();
		List<String> oblicuousList = new ArrayList<String>();
        StringBuilder oblicuouslLine = new StringBuilder();
        StringBuilder oblicuouslLineReverse = new StringBuilder();
        int reverse = size;
	        for(int j=0; j < size; j++) {
			        oblicuouslLine.append(body.get(j).charAt(j));
	        }
			oblicuousList.add(oblicuouslLine.toString());
	        for(int j=0; j < size; j++) {
		        reverse --;
		        oblicuouslLineReverse.append(body.get(j).charAt(reverse));
	        }
		oblicuousList.add(oblicuouslLineReverse.toString());

    	logger.info("createListsOblicuous:{}",oblicuousList.toString());
		
		return oblicuousList;
	}
	
    /**
     * Se encarga crear listado de secuencias en el ADN de manera vertical.
     * @param body es el listado con la secuencia de ADN .
     * @return listado con la secuencia de ADN de manera vertical.
     * */
	private List<String> createListsVertical(List<String> body) {
		int size = body.size();
		List<String> verticalList = new ArrayList<String>();

        for(int i=0; i < size; i++) {
            StringBuilder verticalLine = new StringBuilder();
	        for(int j=0; j < size; j++) {
			        verticalLine.append(body.get(j).charAt(i));
	        }
			verticalList.add(verticalLine.toString());
        }

    	logger.info("createListsVertical:{}",verticalList.toString());
		
		return verticalList;
	}

    /**
     * Se encarga del guardado de la verificacion del ADN
     * @param isMutantDna parametro que indica si es mutante o si es humano
     * @param body es el listado con la secuencia de ADN verificada
     * */
	private void saveDnaVerified(String isMutantDna, List<String> body) {
        DnaStats DnaStats = new DnaStats();
        DnaStats.setDna(body.toString());
        DnaStats.setIsMutant(isMutantDna);
    	logger.info("saveDnaVerified:{}",DnaStats);
        this.dnaRepository.save(DnaStats);
		
	}

}
