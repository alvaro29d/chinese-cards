package com.ado.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ado.domain.Nivel;
import com.ado.domain.NivelEnum;
import com.ado.domain.Palabra;

@Repository
public class FSNivelDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(FSNivelDao.class);
	
	private static final String WORD_FILE = "words" + File.separator + "%s.txt";
	private static final String USER_DATA_FILE = "user_data" + File.separator + "%s-%s.txt";
	
	public List<Nivel> getNiveles() {
		List<Nivel> niveles = new ArrayList<Nivel>();
		for(NivelEnum nivel : NivelEnum.values()) {
			Nivel nivelHsk = new Nivel();
			nivelHsk.setNivel(nivel);
			nivelHsk.getPalabras().addAll(getPalabrasByNivel(nivel.name()));
			niveles.add(nivelHsk);
			
		}
		return niveles;
	}

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
			// TODO Auto-generated catch block
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
	

	public void saveAvance(String uid, NivelEnum nivel, int subNivel) {
		try {
			List<Boolean> avances = getAvanceByUserId(uid, nivel);
			String path = String.format(USER_DATA_FILE,nivel,uid);
			PrintWriter pw = new PrintWriter(new FileOutputStream(path,false));
			for(int i = 0; i < nivel.getNroPalabras()/30 ; i++){
				if(i == subNivel){
					pw.println(true);
				} else {
					pw.println(avances.get(i));
				}
			}
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			LOGGER.error("Error al cargar los avances", e);
		}
	}

	public List<Boolean> getAvanceByUserId(String uid, NivelEnum nivel) {
		List<Boolean> avance = new ArrayList<Boolean>();
		if(new File(String.format(USER_DATA_FILE,nivel,uid)).exists()){
			try {
				File file = new File(String.format(USER_DATA_FILE,nivel,uid));
				Scanner sc = new Scanner(file);
				while(sc.hasNext()) {
					avance.add(sc.nextBoolean());
				}
			} catch (FileNotFoundException e) {
				LOGGER.error("Error al guardar los avances",e);
			}
		} else {
			for(int i = 0; i< nivel.getNroPalabras(); i++) {
				avance.add(false);
			}
		}
		return avance;
	}

	
}
