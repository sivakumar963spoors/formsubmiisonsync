package com.effort.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.effort.entity.Media;
import com.effort.sqls.Sqls;

@Repository
public class MediaDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Media getMedia(String id){
		Media media = jdbcTemplate.queryForObject(Sqls.SELECT_MEDIA, new Object[]{id}, new BeanPropertyRowMapper<Media>(Media.class));
		return media;
	}
}
