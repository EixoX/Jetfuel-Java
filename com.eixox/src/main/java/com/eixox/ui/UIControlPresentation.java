package com.eixox.ui;

import com.eixox.web.Bootstrap3Presenter;

public class UIControlPresentation {

	public String label;
	public String placeholder;
	public String hint;
	public String message;
	public String value;
	public UIControlOptionList options;
	public UIControlType controlType;
	public UIControlState state;
	public String id;
	public String name;
	
	@Override
	public String toString() {
		return Bootstrap3Presenter.present(this);
	}

}
