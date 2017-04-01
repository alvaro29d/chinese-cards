package com.ado.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ado.dao.AvanceDao;
import com.ado.dao.NivelDao;
import com.ado.dao.PalabraDao;
import com.ado.dao.UserDao;
import com.ado.domain.Nivel;
import com.ado.domain.NivelEnum;

@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,readOnly = true)
public class NivelService {

	@Autowired
	private NivelDao nivelDao;
	@Autowired
	private PalabraDao palabrasDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AvanceDao avanceDao;
	
	public List<Nivel> getNiveles() {
		List<Nivel> niveles = nivelDao.getNiveles();
		for (Nivel nivel : niveles) {
			nivel.getPalabras().addAll(palabrasDao.getPalabrasByNivel(nivel.getNivel().name()));
		}
		return niveles;
	}
	
	@Transactional(readOnly=false)
	public void saveAvance(String user,NivelEnum nivel, int subNivel) {
		int userId = userDao.getUserId(user);
		if(userId == 0) {
			userId = userDao.createUser(user);
		}
		String avance = avanceDao.getAvance(userId, nivel);
		if(avance == null) {
			avanceDao.saveAvance(userId,nivel,subNivel);
		} else {
			avanceDao.updateAvance(userId,nivel,subNivel,avance);
		}
	}
	
	public List<Boolean> getAvanceByUserId(String user, NivelEnum nivel) {
		int userId = userDao.getUserId(user);
		if(userId == 0) {
			return sinAvance(nivel);
		}
		String avance = avanceDao.getAvance(userId,nivel);
		if(avance == null){
			return sinAvance(nivel);
		}
		List<Boolean> result = avanceStringToList(avance);
		return result;
	}

	private List<Boolean> avanceStringToList(String avance) {
		byte[] bitArray = Base64.getDecoder().decode(avance);
		List<Boolean> result = new ArrayList<Boolean>();
		for(byte b : bitArray) {
			result.add(b!=0);
		}
		return result;
	}

	private List<Boolean> sinAvance(NivelEnum nivel) {
		List<Boolean> result = new ArrayList<Boolean>();
		for(int i = 0; i < nivel.getNroPalabras()/30; i++) {
			result.add(false);
		}
		return result;
	}
	
}
