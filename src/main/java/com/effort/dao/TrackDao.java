package com.effort.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.effort.beans.http.request.location.Location;
import com.effort.sqls.Sqls;
import com.effort.util.Api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class TrackDao {

	@Autowired
	private JdbcTemplate  jdbcTemplate;
	
	public long saveLocation(final Location location) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {

				PreparedStatement ps = connection.prepareStatement(
						Sqls.INSERT_LOCATION, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, location.getEmpId());

				if (location.getLatitude() == null) {
					ps.setNull(2, Types.DOUBLE);
				} else {
					ps.setDouble(2, location.getLatitude());
				}

				if (location.getLongitude() == null) {
					ps.setNull(3, Types.DOUBLE);
				} else {
					ps.setDouble(3, location.getLongitude());
				}

				if (location.getAltitude() == null) {
					ps.setNull(4, Types.DOUBLE);
				} else {
					ps.setDouble(4, location.getAltitude());
				}

				if (location.getAccuracy() == null) {
					ps.setNull(5, Types.DOUBLE);
				} else {
					ps.setDouble(5, location.getAccuracy());
				}

				if (location.getMedium() == null) {
					ps.setNull(6, Types.BIGINT);
				} else {
					ps.setInt(6, location.getMedium());
				}

				ps.setBoolean(7, location.isOutlier());

				ps.setString(8, location.getFixTime());

				ps.setInt(9, location.getResolvedBy());

				if (location.getCarrierSignalStrength() == null) {
					ps.setNull(10, Types.DOUBLE);
				} else {
					ps.setDouble(10, location.getCarrierSignalStrength());
				}

				if (location.getBatteryLevel() == null) {
					ps.setNull(11, Types.DOUBLE);
				} else {
					ps.setDouble(11, location.getBatteryLevel());
				}

				if (location.getPurpose() == null) {
					ps.setNull(12, Types.INTEGER);
				} else {
					ps.setInt(12, location.getPurpose());
				}

				ps.setBoolean(13, location.isFreshFix());

				if (location.getGpsLat() == null) {
					ps.setNull(14, Types.DOUBLE);
				} else {
					ps.setDouble(14, location.getGpsLat());
				}

				if (location.getGpsLng() == null) {
					ps.setNull(15, Types.DOUBLE);
				} else {
					ps.setDouble(15, location.getGpsLng());
				}

				if (location.getGpsAlt() == null) {
					ps.setNull(16, Types.DOUBLE);
				} else {
					ps.setDouble(16, location.getGpsAlt());
				}

				if (location.getGpsAccuracy() == null) {
					ps.setNull(17, Types.DOUBLE);
				} else {
					ps.setDouble(17, location.getGpsAccuracy());
				}

				if (location.getSpeed() == null) {
					ps.setNull(18, Types.DOUBLE);
				} else {
					ps.setDouble(18, location.getSpeed());
				}

				if (location.getBearingAngle() == null) {
					ps.setNull(19, Types.DOUBLE);
				} else {
					ps.setDouble(19, location.getBearingAngle());
				}

				ps.setString(20, location.getGpsFixTime());

				ps.setBoolean(21, location.isGpsProvider());

				ps.setBoolean(22, location.isGpsOutlier());

				if (location.getCellLat() == null) {
					ps.setNull(23, Types.DOUBLE);
				} else {
					ps.setDouble(23, location.getCellLat());
				}

				if (location.getCellLng() == null) {
					ps.setNull(24, Types.DOUBLE);
				} else {
					ps.setDouble(24, location.getCellLng());
				}

				if (location.getCellAccuracy() == null) {
					ps.setNull(25, Types.DOUBLE);
				} else {
					ps.setDouble(25, location.getCellAccuracy());
				}

				ps.setString(26, location.getCellId());

				ps.setString(27, location.getLac());

				ps.setString(28, location.getMcc());

				ps.setString(29, location.getMnc());

				ps.setString(30, location.getCellFixTime());

				ps.setBoolean(31, location.isCellProvider());

				ps.setBoolean(32, location.isCellOutlier());

				if (location.getUnknownLat() == null) {
					ps.setNull(33, Types.DOUBLE);
				} else {
					ps.setDouble(33, location.getUnknownLat());
				}

				if (location.getUnknownLng() == null) {
					ps.setNull(34, Types.DOUBLE);
				} else {
					ps.setDouble(34, location.getUnknownLng());
				}

				if (location.getUnknownAlt() == null) {
					ps.setNull(35, Types.DOUBLE);
				} else {
					ps.setDouble(35, location.getUnknownAlt());
				}

				if (location.getUnknownAccuracy() == null) {
					ps.setNull(36, Types.DOUBLE);
				} else {
					ps.setDouble(36, location.getUnknownAccuracy());
				}

				ps.setString(37, location.getUnknownFixTime());

				ps.setBoolean(38, location.isOtherProvider());

				ps.setInt(39, location.getConnectivityStatus());

				ps.setInt(40, location.getDeliveryMedium());

				ps.setInt(41, location.getReason());

				ps.setString(42, location.getClientTime());

				ps.setString(43, Api.getDateTimeInUTC(new Date(System
						.currentTimeMillis())));

				ps.setBoolean(44, location.isUnknownOutlier());
				ps.setInt(45, location.getStatus());
				ps.setBoolean(46, location.getEnhanced());
				if (location.getCustomerId() == null) {
					ps.setNull(47, Types.NULL);
				} else {
					ps.setLong(47, location.getCustomerId());
				}
				if (location.getClientCode() == null) {
					ps.setNull(48, Types.NULL);
				} else {
					ps.setString(48, location.getClientCode());
				}

				if (location.getClientId() == null) {
					ps.setNull(49, Types.NULL);
				} else {
					ps.setString(49, location.getClientId());
				}
				
				if(location.getLocationSource() == null) {
					ps.setInt(50,0);
				}else {
					ps.setInt(50, location.getLocationSource());
				}
				
				if(Api.isEmptyString(location.getLocation())) {
					ps.setNull(51, Types.VARCHAR);
				}else {
					ps.setString(51, location.getLocation());
				}
				
				if(Api.isEmptyString(location.getPostalCode())) {
					ps.setNull(52, Types.VARCHAR);
				}else {
					ps.setString(52, location.getPostalCode());
				}
				
				if(Api.isEmptyString(location.getCountry())) {
					ps.setNull(53, Types.VARCHAR);
				}else {
					ps.setString(53, location.getCountry());
				}
				
				if(Api.isEmptyString(location.getAdminArea())) {
					ps.setNull(54, Types.VARCHAR);
				}else {
					ps.setString(54, location.getAdminArea());
				}
				
				if(Api.isEmptyString(location.getLocality())) {
					ps.setNull(55, Types.VARCHAR);
				}else {
					ps.setString(55, location.getLocality());
				}
				
				if(Api.isEmptyString(location.getSublocality())) {
					ps.setNull(56, Types.VARCHAR);
				}else {
					ps.setString(56, location.getSublocality());
				}
				
			/*	if (location.getStartWorkReason() == null) {
					ps.setNull(50, Types.NULL);
				} else {
					ps.setLong(50, location.getStartWorkReason());
				}*/

				return ps;
			}
		}, keyHolder);

		//long id = keyHolder.getKey().longValue();
		
		long  id = 0;
		if(keyHolder.getKey() != null){
			id = keyHolder.getKey().longValue();
		}
		location.setLocationId(id);

		if(location.getStartWorkReason()!=null && id!=0){
			try {
				jdbcTemplate.update(
						Sqls.INSERT_INTO_LOCATION_REASONS,
						new Object[] {
								id,
								location.getStartWorkReason()
								});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}
	
}
