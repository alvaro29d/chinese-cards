package com.ado.dao;

import java.util.List;

import com.ado.domain.Nivel;
import com.ado.domain.NivelEnum;

public interface NivelDao {

	List<Nivel> getNiveles();

	void saveAvance(String uid, NivelEnum nivel, int subNivel);

	List<Boolean> getAvanceByUserId(String uid, NivelEnum nivel);

	
}
