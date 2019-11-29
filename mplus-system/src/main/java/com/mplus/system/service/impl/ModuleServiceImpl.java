package com.mplus.system.service.impl;

import com.mplus.common.repo.BaseRepository;
import com.mplus.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mplus.system.entity.Module;
import com.mplus.system.repo.ModuleRepsitory;
import com.mplus.system.service.ModuleService;

@Service
@Transactional
public class ModuleServiceImpl extends BaseServiceImpl<Module, String> implements ModuleService {

	@Autowired
	private ModuleRepsitory moduleRepository;
	
	@Override
	public BaseRepository<Module, String> getRepository() {
		return moduleRepository;
	}

	

}
