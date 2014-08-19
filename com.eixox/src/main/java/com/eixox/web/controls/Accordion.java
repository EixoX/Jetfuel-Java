package com.eixox.web.controls;

import java.util.LinkedList;

public class Accordion extends LinkedList<AccordionItem> implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3829287765467082135L;
	private String id;

	public final String getId() {
		return this.id;
	}

	public final void setId(String id) {
		this.id = id;

	}

}
