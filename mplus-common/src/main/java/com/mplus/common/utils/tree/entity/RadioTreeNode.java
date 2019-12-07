/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mplus.common.utils.tree.entity;

public class RadioTreeNode extends TreeNode {

	protected boolean checked;
	protected boolean disabled;
	protected boolean radio;

	public RadioTreeNode() {
	}

	public RadioTreeNode(String id, String code, String text, boolean expanded, boolean leaf, boolean radio) {
		this.id = id;
		this.code = code;
		this.text = text;
		this.expanded = expanded;
		this.leaf = leaf;
		this.radio = radio;
		this.checked = false;
	}

	public RadioTreeNode(String id, String code, String text, boolean expanded, boolean leaf, boolean radio,
			boolean disabled) {
		this.id = id;
		this.code = code;
		this.text = text;
		this.expanded = expanded;
		this.leaf = leaf;
		this.radio = radio;
		this.checked = false;
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

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}
}
