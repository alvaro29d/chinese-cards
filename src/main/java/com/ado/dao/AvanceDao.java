package com.ado.dao;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ado.domain.NivelEnum;

@Repository
public class AvanceDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String getAvance(int userId, NivelEnum nivel){
		try {
			String sql = "select a.avance from avances a "
					+ " left join niveles n on a.nivel_id=n.nivel_id "
					+ " where a.user_id=? and n.nivel=?";
			String avance = jdbcTemplate.queryForObject(sql,new Object[]{userId,nivel.name()}, String.class);
			return avance;
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public void saveAvance(int userId, NivelEnum nivel, int subNivel) {
		byte[] bAvance = new byte[nivel.getNroPalabras()/30];
		bAvance[subNivel] = 1;
		String sAvance = Base64.getEncoder().encodeToString(bAvance);
		String sql = "insert into avances (user_id,nivel_id,avance) values(?,?,?)";
		jdbcTemplate.update(sql,new Object[]{userId,nivel.ordinal()+1,sAvance});
	}

	public void updateAvance(int userId, NivelEnum nivel, int subNivel, String avance) {
		byte[] bAvance = Base64.getDecoder().decode(avance);
		bAvance[subNivel] = 1;
		String sAvance = Base64.getEncoder().encodeToString(bAvance);
		String sql = "update avances set \"avance\"=? where \"user_id\"=? and \"nivel_id\"=?";
		jdbcTemplate.update(sql,new Object[]{sAvance, userId,nivel.ordinal()+1});
	}
	
}
