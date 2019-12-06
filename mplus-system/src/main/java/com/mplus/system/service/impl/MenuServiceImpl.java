package com.mplus.system.service.impl;

import com.mplus.common.enums.DataStatus;
import com.mplus.system.entity.Menu;
import com.mplus.system.repo.MenuRepository;
import com.mplus.system.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

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
		menu.setDataState(DataStatus.DELETED.getCode());
		menuRepository.save(menu);
	}

	@Override
	@Transactional(readOnly = true)
	public Menu findOneByCode(String menuCode) {
		return menuRepository.findOneByCode(menuCode, DataStatus.NORMAL.getCode());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Menu> findMenusByParent(String parentMenuId) {
		return menuRepository.findMenusByParent(parentMenuId, DataStatus.NORMAL.getCode());
	}

}
