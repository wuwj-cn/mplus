package com.mplus.utils.tree.service;

import java.util.List;

import com.mplus.utils.tree.entity.TreeNode;

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
