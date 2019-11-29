package com.mplus.common.utils.tree.entity;

public class CheckboxTreeNode extends TreeNode {
	protected boolean checked;
	protected boolean disabled;

	public CheckboxTreeNode() {
	}

	public CheckboxTreeNode(String id, String code, String text, boolean expanded, boolean leaf, boolean checked) {
		this.id = id;
		this.code = code;
		this.text = text;
		this.expanded = expanded;
		this.leaf = leaf;
		this.checked = checked;
	}

	public CheckboxTreeNode(String id, String code, String text, boolean expanded, boolean leaf, boolean checked,
			boolean disabled) {
		this.id = id;
		this.code = code;
		this.text = text;
		this.expanded = expanded;
		this.leaf = leaf;
		this.checked = checked;
		this.disabled = disabled;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
