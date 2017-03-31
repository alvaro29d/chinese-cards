package com.ado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ado.domain.Nivel;
import com.ado.domain.NivelEnum;

@Repository
public class DBNivelDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	public List<Nivel> getNiveles() {
		String sql = "select * from niveles";
		List<Nivel> list = jdbcTemplate.query(sql, new NivelRowMapper());
		return list;
	}

	public void saveAvance(String user, NivelEnum nivel, int subNivel) {
		String avance = getAvanceByUserAsString(user, nivel);
		byte[] bitArray = Base64.getDecoder().decode(avance);
		bitArray[subNivel] = 1;
		String nuevoAvance = Base64.getEncoder().encodeToString(bitArray);
		saveAvance(user,nivel, nuevoAvance);
	}

	private void saveAvance(String user, NivelEnum nivel, String nuevoAvance) {
		int userId = getUserId(user);
//		String sql = "insert into avances (user_id,nivel_id,avance) values(?,?,?)";
//		jdbcTemplate.queryForObject(insertSql, String.class);
//		
//		
//		jdbcTemplate.update(sql, args)
//		List<Nivel> list = jdbcTemplate.query(sql, new NivelRowMapper());
//		return list;
		// TODO Auto-generated method stub
		
	}

	private int getUserId(final String user) {
		try{
			String sql = "select user_id from users where user = ?";
			return jdbcTemplate.queryForObject(sql,new Object[]{user}, Integer.class);
		} catch (Exception e) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					String sql = "insert into users (\"user\") values (?)";
					PreparedStatement ps = con.prepareStatement(sql,new String[]{"user_id"});
					ps.setString(1, user);
					return ps;
				}
			}, keyHolder);
			return keyHolder.getKey().intValue();
		}
	}

	public List<Boolean> getAvanceByUser(String user, NivelEnum nivel) {
		String avance = getAvanceByUserAsString(user, nivel);
		byte[] bitArray = Base64.getDecoder().decode(avance);
		List<Boolean> result = new ArrayList<Boolean>();
		for(byte b : bitArray) {
			result.add(b!=0);
		}
		return result;
	}

	
	
	private String getAvanceByUserAsString(String user, NivelEnum nivel) {
		try{
			String sql = "select a.avance from avances a "
				+ " left join users u on a.user_id=u.user_id "
				+ " left join niveles n on a.nivel_id=n.nivel_id "
				+ " where u.user=? and n.nivel=?";
			String avance = jdbcTemplate.queryForObject(sql,new Object[]{user,nivel.name()}, String.class);
			return avance;
		} catch (Exception e) {
			return Base64.getEncoder().encodeToString(new byte[nivel.getNroPalabras()/30]);
		}
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
