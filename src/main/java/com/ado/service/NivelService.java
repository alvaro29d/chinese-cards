package com.ado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ado.dao.NivelDao;
import com.ado.domain.Nivel;
import com.ado.domain.NivelEnum;

@Service
public class NivelService {

	@Autowired
	private NivelDao dao;
	
	public List<Nivel> getNiveles() {
		return dao.getNiveles();
	}
	
	public void saveAvance(String uid,NivelEnum nivel, int subNivel) {
		dao.saveAvance(uid,nivel,subNivel);
	}
	
	public List<Boolean> getAvanceByUserId(String uid, NivelEnum nivel) {
		return dao.getAvanceByUserId(uid,nivel);
	}
	
}
