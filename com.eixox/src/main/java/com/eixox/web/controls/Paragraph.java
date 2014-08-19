package com.eixox.web.controls;

public class Paragraph extends ComponentList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -827185773971471842L;

	public Paragraph() {
	}

	public Paragraph(String content) {
		super.add(new Text(content));
	}
}
