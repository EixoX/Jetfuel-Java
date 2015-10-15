package com.eixox.ui;

import java.util.ArrayList;

public class OptionList extends ArrayList<Option> {

	private static final long serialVersionUID = 1205665022645276455L;

	public final Option add(Object key, Object label) {
		Option o = new Option(key, label);
		super.add(o);
		return o;

	}
}
