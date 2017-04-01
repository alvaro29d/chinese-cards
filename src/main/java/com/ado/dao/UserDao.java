package com.ado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int getUserId(final String user) {
		try {
			String sql = "select user_id from users where \"user\"=?";
			return jdbcTemplate.queryForObject(sql,new Object[]{user}, Integer.class);
		} catch(EmptyResultDataAccessException e){
			return 0;
		}
	}
	
	public int createUser(final String user) {
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
