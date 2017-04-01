package com.ado.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ado.domain.Palabra;

@Repository
public class PalabraDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(PalabraDao.class);
	
	private static final String WORD_FILE = "words" + File.separator + "%s.txt";
	
	public List<Palabra> getPalabrasByNivel(String nivel) {
		LOGGER.debug("get palabras " + nivel);
		List<Palabra> palabras = new ArrayList<Palabra>();
		try {
			File file = new File(String.format(WORD_FILE,nivel));
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				palabras.add(parseLinea(sc.nextLine()));
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("No se encuentra el archivo: " + nivel,e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return palabras;
	}

	private Palabra parseLinea(String nextLine) {
		LOGGER.trace("parse line: " + nextLine);
		String[] split = nextLine.split("	");
		Palabra p = new Palabra();
		p.setCaracter(split[0]);
		p.setPingin(split[3]);
		p.setSignificado(split[4]);
		LOGGER.trace(p.toString());
		return p;
	}
	
}
