package com.eixox.ui;

import java.util.LinkedList;

public class UIControlOptionList extends LinkedList<UIControlOption> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1290607055719577538L;

	public final UIControlOption prepend(Object key, String label) {
		UIControlOption opt = new UIControlOption(key, label);
		super.addFirst(opt);
		return opt;
	}

	public final UIControlOption add(Object key, String label) {
		UIControlOption opt = new UIControlOption(key, label);
		super.add(opt);
		return opt;
	}

	public final UIControlOption get(Object key) {
		for (UIControlOption opt : this)
			if (key.equals(opt.key))
				return opt;

		return null;
	}

	public final String getLabel(Object key) {
		UIControlOption opt = get(key);
		return opt == null ? null : opt.label;
	}
	
	public static final UIControlOptionList parse(String[] keynames) {
		UIControlOptionList list = new UIControlOptionList();
		for (int i = 0; i < keynames.length; i++) {
			list.add(new UIControlOption(keynames[i], keynames[i]));
		}
		
		return list;
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
