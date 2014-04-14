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

	public static final UIControlOptionList parse(String[] keyValues, char splitter) {
		UIControlOptionList list = new UIControlOptionList();
		String key;
		String value;
		for (int i = 0; i < keyValues.length; i++) {
			int ipos = keyValues[i].indexOf(splitter);
			if (ipos > 0) {
				key = keyValues[i].substring(0, ipos);
				value = keyValues[i].substring(ipos + 1);
			} else {
				key = keyValues[i];
				value = keyValues[i];
			}
			list.add(new UIControlOption(key, value));
		}
		return list;
	}
}
