package com.eixox.web.controls;

import java.util.LinkedList;

public class ComponentList extends LinkedList<Component> implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8782867477544597905L;
	private String id;

	public final Component get(String id) {
		for (Component c : this)
			if (id.equalsIgnoreCase(c.getId()))
				return c;

		return null;
	}

	public final String getId() {
		return this.id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final ComponentList append(Component component){
		super.add(component);
		return this;
	}
}
