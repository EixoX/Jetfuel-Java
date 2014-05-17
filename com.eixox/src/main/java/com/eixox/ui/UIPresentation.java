package com.eixox.ui;

import java.util.Map;

import com.eixox.globalization.Culture;
import com.eixox.restrictions.RestrictionProblems;

public class UIPresentation<T> {

	private final UIAspect aspect;
	private final UIPresentationMember[] controls;
	private final RestrictionProblems problems;

	public UIPresentation(UIAspect aspect) {
		this.aspect = aspect;
		this.controls = new UIPresentationMember[aspect.getCount()];
		this.problems = new RestrictionProblems();
		for (int i = 0; i < this.controls.length; i++) {
			UIAspectMember member = aspect.get(i);
			this.controls[i] = new UIPresentationMember();
			this.controls[i].controlType = member.getMemberType();
			this.controls[i].hint = member.getHint();
			this.controls[i].id = member.getName();
			this.controls[i].label = member.getLabel();
			this.controls[i].name = member.getName();
			this.controls[i].options = member.getOptions();
			this.controls[i].placeholder = member.getPlaceholder();
			this.controls[i].state = UIControlState.Normal;
		}
	}

	public final UIPresentationMember[] getControls() {
		return this.controls;
	}

	public final UIPresentationMember get(int ordinal) {
		return this.controls[ordinal];
	}

	public final UIPresentationMember get(String name) {
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

	public final synchronized boolean parseAndValidate(Map<String, String> map, Culture culture, Object destination) {
		this.problems.clear();
		for (int i = 0; i < this.controls.length; i++) {
			this.controls[i].value = map.get(this.controls[i].name);
			UIAspectMember member = this.aspect.get(i);
			member.parse(this.controls[i].value, destination, culture);
			String msg = member.getRestrictionMessageFor(destination);
			if (msg != null && !msg.isEmpty()) {
				this.problems.put(member.getName(), msg);
				this.controls[i].message = msg;
				this.controls[i].state = UIControlState.Error;
			} else {
				this.controls[i].state = UIControlState.Success;
			}
		}
		return this.problems.size() == 0;
	}

	public final RestrictionProblems getProblems() {
		return this.problems;
	}

	public final void addProblem(String name, String message) {
		this.problems.put(name, message);
		int ordinal = getOrdinal(name);
		if (ordinal >= 0) {
			this.controls[ordinal].message = message;
			this.controls[ordinal].state = UIControlState.Error;
		}
	}

}
