package com.eixox.web.controls;

public class Panel implements Component {

	private String id;
	private static final long serialVersionUID = -6887231134681411658L;

	public Panel() {

	}

	public Panel(String id) {
		this.id = id;
	}

	public final String getId() {
		return this.id;
	}

	public final void setId(String id) {
		this.id = id;

	}

	public ComponentList heading = new ComponentList();
	public ComponentList body = new ComponentList();

}
