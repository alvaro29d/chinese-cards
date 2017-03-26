package com.ado.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ado.domain.Nivel;
import com.ado.domain.Palabra;

@Repository
public class FileSystemNivelDao implements NivelDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemNivelDao.class);
	
	@Override
	public List<Nivel> getNiveles() {
		List<Nivel> niveles = new ArrayList<Nivel>();
		Nivel nivelHsk1 = new Nivel();
		nivelHsk1.setNombre("Hsk1");
		nivelHsk1.getPalabras().addAll(getPalabrasByNivel("HSK1"));
		Nivel nivelHsk2 = new Nivel();
		nivelHsk2.setNombre("Hsk2");
		nivelHsk2.getPalabras().addAll(getPalabrasByNivel("HSK2"));
		Nivel nivelHsk3 = new Nivel();
		nivelHsk3.setNombre("Hsk3");
		nivelHsk3.getPalabras().addAll(getPalabrasByNivel("HSK3"));
		Nivel nivelHsk4 = new Nivel();
		nivelHsk4.setNombre("HSK4");
		nivelHsk4.getPalabras().addAll(getPalabrasByNivel("HSK4"));
		Nivel nivelHsk5 = new Nivel();
		nivelHsk5.setNombre("HSK5");
		nivelHsk5.getPalabras().addAll(getPalabrasByNivel("HSK5"));
		Nivel nivelHsk6 = new Nivel();
		nivelHsk6.setNombre("HSK6");
		nivelHsk6.getPalabras().addAll(getPalabrasByNivel("HSK6"));
		
		niveles.add(nivelHsk1);
		niveles.add(nivelHsk2);
		niveles.add(nivelHsk3);
		niveles.add(nivelHsk4);
		niveles.add(nivelHsk5);
		niveles.add(nivelHsk6);
		
		
		return niveles;
	}

	private Collection<? extends Palabra> getPalabras() {
		List<Palabra> p = new ArrayList<Palabra>();
		for(int i = 0 ; i < 600 ; i ++){
			Palabra palabra = new Palabra();
			palabra.setPalabra(RandomStringUtils.randomAlphabetic(1));
			palabra.setPingin(RandomStringUtils.randomAlphabetic(2,10) + " " + palabra.getPalabra());
			palabra.setSignificado(RandomStringUtils.randomAlphabetic(6,40));
			p.add(palabra);
		}
		return p;
	}
	
	private List<Palabra> getPalabrasByNivel(String nivel) {
		LOGGER.debug("get palabras " + nivel);
		List<Palabra> palabras = new ArrayList<Palabra>();
		File file = new File(nivel + ".txt");
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				palabras.add(parseLinea(sc.nextLine()));
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("No se encuentra el archivo: " + nivel,e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return palabras;
	}

	private Palabra parseLinea(String nextLine) {
		LOGGER.trace("parse line: " + nextLine);
		String[] split = nextLine.split("	");
		
		Palabra p = new Palabra();
		p.setPalabra(split[0]);
		p.setPingin(split[3]);
		p.setSignificado(split[4]);
		LOGGER.trace(p.toString());
		return p;
	}
	
	
}
