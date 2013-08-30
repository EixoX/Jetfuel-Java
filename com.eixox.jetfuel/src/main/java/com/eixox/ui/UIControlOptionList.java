package com.eixox.ui;

import java.util.LinkedList;

public class UIControlOptionList extends LinkedList<UIControlOption> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1290607055719577538L;

	public final UIControlOption add(Object key, String value) {
		UIControlOption opt = new UIControlOption(key, value);
		super.add(opt);
		return opt;
	}

	public final UIControlOption get(Object key) {
		for (UIControlOption opt : this)
			if (key.equals(opt.getKey()))
				return opt;

		return null;
	}

	public final String getValue(Object key) {
		UIControlOption opt = get(key);
		return opt == null ? null : opt.getValue();
	}
}
