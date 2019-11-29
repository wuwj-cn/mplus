package com.mplus.system.service;

import java.util.List;

import com.mplus.system.entity.Menu;

public interface MenuService {

	Menu saveMenu(Menu menu);
	
	Menu updateMenu(Menu menu);
	
	void removeOrg(Menu menu);
	
	Menu findOneByCode(String menuCode);
	
	List<Menu> findMenusByParent(String parentMenuId);
	
}
