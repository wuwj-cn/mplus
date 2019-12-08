/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mplus.system.service.impl;

import com.mplus.common.enums.DataState;
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
		menu.setDataState(DataState.DELETED.getCode());
		menuRepository.save(menu);
	}

	@Override
	@Transactional(readOnly = true)
	public Menu findOneByCode(String menuCode) {
		return menuRepository.findOneByCode(menuCode, DataState.NORMAL.getCode());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Menu> findMenusByParent(String parentMenuId) {
		return menuRepository.findMenusByParent(parentMenuId, DataState.NORMAL.getCode());
	}

}
