package com.effort.dao.rollbackhandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;


public class ClientIdCallBackHandler implements RowCallbackHandler{
	
	private Map<String, Long> clientIdMap = null;
	
	public ClientIdCallBackHandler(){
		clientIdMap = new HashMap<String, Long>();
	}

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		Long id = rs.getLong("id");
		String clientSideId = rs.getString("clientSideId");
		
		clientIdMap.put(clientSideId, id);
	}

	public Map<String, Long> getClientIdMap() {
		return clientIdMap;
	}


}
