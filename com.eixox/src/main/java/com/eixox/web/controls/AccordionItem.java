package com.eixox.web.controls;

public class AccordionItem {

	public ComponentList heading = new ComponentList();
	public ComponentList body = new ComponentList();

	public final AccordionItem addHeading(Component component) {
		this.heading.add(component);
		return this;
	}

	public final AccordionItem addBody(Component component) {
		this.body.add(component);
		return this;
	}
}
