package com.ado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ado.dao.NivelDao;
import com.ado.domain.Nivel;

@Service
public class NivelService {

	@Autowired
	private NivelDao dao;
	
	public List<Nivel> getNiveles() {
		return dao.getNiveles();
	}
	
}
