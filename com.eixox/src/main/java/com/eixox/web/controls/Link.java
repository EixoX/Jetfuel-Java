package com.eixox.web.controls;

public class Link extends ComponentList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1448696197316288595L;

	public Link() {
	}

	public Link(String text) {
		super.add(new Text(text));
	}
}
