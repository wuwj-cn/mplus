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

package com.mplus.common.utils.tree.service;

import java.util.List;

import com.mplus.common.utils.tree.entity.TreeNode;

public interface TreeService {

	/**
	 * Return children of the given node id.
	 * 
	 * @param id parent node id
	 * @return  list of TreeNode
	 */
	public List<TreeNode> getNodes(String id);

	/**
	 * Return children with checkbox of the given node id.
	 * 
	 * @param id parent node id
	 * @return  list of CheckboxTreeNode
	 */
	public List<TreeNode> getCheckboxNodes(String id);
}
