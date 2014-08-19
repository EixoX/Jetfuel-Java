package com.eixox.ui;

import com.eixox.NameValueCollection;
import com.eixox.globalization.Culture;
import com.eixox.restrictions.RestrictionProblems;

public class UIPresentation {

	private final UIAspect aspect;
	private final UIControlPresentation[] controls;
	private final RestrictionProblems problems;

	public UIPresentation(Object entity, Culture culture) {
		this(UIAspect.getDefaultInstance(entity.getClass()));
		read(entity, culture);
	}
	
	public UIPresentation(Class<?> claz) {
		this(UIAspect.getDefaultInstance(claz));
	}

	public UIPresentation(UIAspect aspect) {
		this.aspect = aspect;
		this.controls = new UIControlPresentation[aspect.getCount()];
		this.problems = new RestrictionProblems();
		for (int i = 0; i < this.controls.length; i++) {
			UIAspectMember member = aspect.get(i);
			this.controls[i] = new UIControlPresentation();
			this.controls[i].controlType = member.getMemberType();
			this.controls[i].hint = member.getHint();
			this.controls[i].id = member.getName();
			this.controls[i].label = member.getLabel();
			this.controls[i].name = member.getName();
			this.controls[i].options = member.getOptions();
			this.controls[i].placeholder = member.getPlaceholder();
			this.controls[i].state = UIControlState.NORMAL;
		}
	}
	
	public final void read(Object entity, Culture culture){
		for(int i=0; i < this.controls.length; i++)
		{
			UIAspectMember member = this.aspect.get(i);
			this.controls[i].value = member.read(entity, culture);
		}
	}

	public final UIControlPresentation[] getControls() {
		return this.controls;
	}

	public final UIControlPresentation get(int ordinal) {
		return this.controls[ordinal];
	}

	public final UIControlPresentation get(String name) {
		return this.controls[getOrdinalOrException(name)];
	}

	public final int getControlCount() {
		return this.controls.length;
	}

	public final int getOrdinal(String name) {
		for (int i = 0; i < this.controls.length; i++)
			if (name.equalsIgnoreCase(this.controls[i].name))
				return i;
		return -1;
	}

	public final int getOrdinalOrException(String name) {
		int ordinal = getOrdinal(name);
		if (ordinal < 0)
			throw new RuntimeException(name + " is not on presentation for " + aspect.getDataType());
		else
			return ordinal;
	}

	public final synchronized boolean parseAndValidate(NameValueCollection<Object> map, Culture culture, Object destination) {
		this.problems.clear();
		for (int i = 0; i < this.controls.length; i++) {
			this.controls[i].value = (String) map.get(this.controls[i].name);
			UIAspectMember member = this.aspect.get(i);
			member.parse(this.controls[i].value, destination, culture);
			String msg = member.getRestrictionMessageFor(destination);
			if (msg != null && !msg.isEmpty()) {
				this.problems.put(member.getName(), msg);
				this.controls[i].message = msg;
				this.controls[i].state = UIControlState.ERROR;
			} else {
				this.controls[i].state = UIControlState.SUCCESS;
			}
		}
		return this.problems.size() == 0;
	}

	public final synchronized void parse(NameValueCollection<Object> map, Culture culture, Object destination) {
		for (int i = 0; i < this.controls.length; i++) {
			this.controls[i].value = (String) map.get(this.controls[i].name);
			UIAspectMember member = this.aspect.get(i);
			member.parse(this.controls[i].value, destination, culture);
		}
	}

	public final RestrictionProblems getProblems() {
		return this.problems;
	}

	public final void addProblem(String name, String message) {
		this.problems.put(name, message);
		int ordinal = getOrdinal(name);
		if (ordinal >= 0) {
			this.controls[ordinal].message = message;
			this.controls[ordinal].state = UIControlState.ERROR;
		}
	}

}
