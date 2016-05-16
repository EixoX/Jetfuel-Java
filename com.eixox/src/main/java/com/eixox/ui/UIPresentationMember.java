package com.eixox.ui;

import com.eixox.web.Bootstrap3Presenter;

public class UIPresentationMember {

	public ControlType controlType;
	public ControlState controlState;
	public String label;
	public String hint;
	public String placeholder;
	public boolean required;
	public int maxlength;
	public OptionList options;
	public String id;
	public String name;
	public String value;
	public String message;

	public UIPresentationMember() {
	}

	public UIPresentationMember(UIAspectMember aspect) {
		this.controlState = ControlState.NORMAL;
		this.controlType = aspect.controlType;
		this.label = aspect.label;
		this.hint = aspect.hint;
		this.placeholder = aspect.placeholder;
		this.required = aspect.required;
		this.maxlength = aspect.maxlength;
		this.options = aspect.options;
		this.id = aspect.getName();
		this.name = aspect.getName();
	}

	@Override
	public String toString() {
		return Bootstrap3Presenter.present(this);
	}
}
