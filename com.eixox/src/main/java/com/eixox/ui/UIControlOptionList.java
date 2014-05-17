package com.eixox.ui;

import java.util.LinkedList;

public class UIControlOptionList extends LinkedList<UIControlOption> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1290607055719577538L;

	public final UIControlOption add(Object key, String name) {
		UIControlOption opt = new UIControlOption(key, name);
		super.add(opt);
		return opt;
	}

	public final UIControlOption get(Object key) {
		for (UIControlOption opt : this)
			if (key.equals(opt.getKey()))
				return opt;

		return null;
	}

	public final String getName(Object key) {
		UIControlOption opt = get(key);
		return opt == null ? null : opt.getName();
	}

	public static final UIControlOptionList parse(String[] keynames, char splitter) {
		UIControlOptionList list = new UIControlOptionList();
		String key;
		String name;
		for (int i = 0; i < keynames.length; i++) {
			int ipos = keynames[i].indexOf(splitter);
			if (ipos > 0) {
				key = keynames[i].substring(0, ipos);
				name = keynames[i].substring(ipos + 1);
			} else {
				key = keynames[i];
				name = keynames[i];
			}
			list.add(new UIControlOption(key, name));
		}
		return list;
	}
}
