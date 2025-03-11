package com.effort.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.effort.dao.AuditDao;
import com.effort.dao.ConfiguratorDao;
import com.effort.entity.FormCustomEntityUpdate;
@Service
public class ConfiguratorManager {
	
	@Autowired
	private AuditDao auditDao;
	@Autowired
	private ConfiguratorDao configuratorDao;
	public long insertOrUpdateFormCustomEntityUpdates(FormCustomEntityUpdate formCustomEntityUpdate) {
		return configuratorDao.insertOrUpdateFormCustomEntityUpdates(formCustomEntityUpdate);
	}
			
	public void insertFormCustomEntityUpdateAuditLogs(long formId) {
		auditDao.insertFormCustomEntityUpdateAuditLogs(formId);
	}

}
