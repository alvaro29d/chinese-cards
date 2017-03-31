package com.ado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ado.dao.DBNivelDao;
import com.ado.dao.FSNivelDao;
import com.ado.domain.Nivel;
import com.ado.domain.NivelEnum;

@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,readOnly = true)
public class NivelService {

	@Autowired
	private DBNivelDao dbDao;
	
	@Autowired
	private FSNivelDao fsDao;
	
	
	public List<Nivel> getNiveles() {
		List<Nivel> niveles = dbDao.getNiveles();
		for (Nivel nivel : niveles) {
			nivel.getPalabras().addAll(fsDao.getPalabrasByNivel(nivel.getNivel().name()));
		}
		return niveles;
	}
	
	@Transactional(readOnly=false)
	public void saveAvance(String uid,NivelEnum nivel, int subNivel) {
		dbDao.saveAvance(uid,nivel,subNivel);
	}
	
	public List<Boolean> getAvanceByUserId(String uid, NivelEnum nivel) {
		return dbDao.getAvanceByUser(uid,nivel);
	}
	
}
