package com.mplus.system.service.impl;

import com.mplus.common.repo.BaseRepository;
import com.mplus.common.service.impl.BaseServiceImpl;
import com.mplus.system.entity.Module;
import com.mplus.system.repo.ModuleRepository;
import com.mplus.system.service.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ModuleServiceImpl extends BaseServiceImpl<Module, String> implements ModuleService {

	private ModuleRepository moduleRepository;
	
	@Override
	public BaseRepository<Module, String> getRepository() {
		return moduleRepository;
	}

	

}
