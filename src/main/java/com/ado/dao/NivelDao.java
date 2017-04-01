package com.ado.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ado.domain.Nivel;
import com.ado.domain.NivelEnum;

@Repository
public class NivelDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	public List<Nivel> getNiveles() {
		String sql = "select * from niveles";
		List<Nivel> list = jdbcTemplate.query(sql, new NivelRowMapper());
		return list;
	}

	public class NivelRowMapper implements RowMapper<Nivel> {
		@Override
		public Nivel mapRow(ResultSet rs, int arg1) throws SQLException {
			Nivel n = new Nivel();
			n.setNivel(NivelEnum.valueOf(rs.getString("nivel")));
			return n;
		}
	}
	
}
