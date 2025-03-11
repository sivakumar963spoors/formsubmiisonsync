package com.effort.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.effort.entity.FormCustomEntityUpdate;
import com.effort.entity.FormSpec;
import com.effort.entity.OfflineCustomEntityUpdateConfiguration;
import com.effort.sqls.Sqls;
import com.effort.util.Api;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
@Repository
public class ConfiguratorDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<OfflineCustomEntityUpdateConfiguration> getFormSpecOfflineCustomEntityUpdateConfiguration(FormSpec formSpec){
		return jdbcTemplate.query(Sqls.SELECT_OFFLINE_CUSTOM_ENTITY_UPDATE_FORM_CONFIGURATIONS, new Object[] {formSpec.getUniqueId()},
				new BeanPropertyRowMapper<OfflineCustomEntityUpdateConfiguration>(OfflineCustomEntityUpdateConfiguration.class));
	}
	
public long insertOrUpdateFormCustomEntityUpdates(final FormCustomEntityUpdate formCustomEntityUpdate) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(Sqls.INSERT_OR_UPDATES_FORM_CUSTOM_ENTITY_UPDATES, 
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, formCustomEntityUpdate.getFormId());
				ps.setInt(2, formCustomEntityUpdate.getCustomEntityUpdateStatus());
				ps.setString(3, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				ps.setString(4, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				ps.setLong(5, formCustomEntityUpdate.getCompanyId());
				ps.setString(6, formCustomEntityUpdate.getRemarks());
				return ps;
			}
		},keyHolder);
		return formCustomEntityUpdate.getFormId();
	}

public List<OfflineCustomEntityUpdateConfiguration> getOfflineCustomEntityUpdateConfigurationForSync(String formSpecIds){
	
	List<OfflineCustomEntityUpdateConfiguration> offlineCustomEntityUpdateConfiguration = new ArrayList<OfflineCustomEntityUpdateConfiguration>();
	
	if(!Api.isEmptyString(formSpecIds)) {
		String sql=Sqls.SELECT_OFFLINE_CUSTOM_ENTITY_UPDATE_FORM_CONFIGURATIONS_FOR_FORMSPEC_IDS.replace(":formSpecIds", formSpecIds);
		return jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<OfflineCustomEntityUpdateConfiguration>(OfflineCustomEntityUpdateConfiguration.class));
	}
	return offlineCustomEntityUpdateConfiguration;
	
}

}
