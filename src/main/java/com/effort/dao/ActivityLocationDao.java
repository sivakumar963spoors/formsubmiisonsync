package com.effort.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.effort.beans.http.request.location.Location;
import com.effort.sqls.Sqls;
import com.effort.util.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;

@Repository
public class ActivityLocationDao implements Serializable {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void saveActivityLocation(final Location location){

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						Sqls.INSERT_INTO_ACTIVITY_LOCATIONS,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, location.getLocationId());
				ps.setLong(2, location.getEmpId());
				ps.setInt(3, location.getPurpose());
				ps.setString(4, location.getClientTime());
				ps.setString(5, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				return ps;
			}
		}, keyHolder);

	}
	

}
