package com.mplus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mplus.entity.Module;
import com.mplus.repo.BaseRepository;
import com.mplus.repo.ModuleRepsitory;
import com.mplus.service.ModuleService;

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
