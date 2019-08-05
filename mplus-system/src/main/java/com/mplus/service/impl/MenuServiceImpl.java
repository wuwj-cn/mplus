package com.mplus.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mplus.entity.Menu;
import com.mplus.enums.Status;
import com.mplus.repo.MenuRepository;
import com.mplus.service.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;
	
	@Override
	public Menu saveMenu(Menu menu) {
		if (!StringUtils.isEmpty(menu.getId())) {
			throw new RuntimeException("object id is not null or empty");
		}
		return menuRepository.save(menu);
	}

	@Override
	public Menu updateMenu(Menu menu) {
		if (StringUtils.isEmpty(menu.getId())) {
			throw new RuntimeException("object id is null or empty");
		}
		return menuRepository.save(menu);
	}

	@Override
	public void removeOrg(Menu menu) {
		menu.setStatus(Status.DELETED.getCode());
		menuRepository.save(menu);
	}

	@Override
	@Transactional(readOnly = true)
	public Menu findOneByCode(String menuCode) {
		return menuRepository.findOneByCode(menuCode, Status.NORMAL.getCode());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Menu> findMenusByParent(String parentMenuId) {
		return menuRepository.findMenusByParent(parentMenuId, Status.NORMAL.getCode());
	}

}
