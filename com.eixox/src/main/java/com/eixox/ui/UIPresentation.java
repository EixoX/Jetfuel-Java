package com.eixox.ui;

import java.util.ArrayList;

public class UIPresentation extends ArrayList<UIPresentationMember> {

	private static final long serialVersionUID = 6539046574902926538L;

	public UIPresentation() {
		
	}

	public UIPresentation(int size) {
		super(size);
	}

	public final int getOrdinal(String name) {
		int l = super.size();
		for (int i = 0; i < l; i++)
			if (name.equalsIgnoreCase(get(i).name))
				return i;

		return -1;
	}

	public final UIPresentationMember get(String name) {
		int ordinal = getOrdinal(name);
		return ordinal < 0 ? null : super.get(ordinal);
	}

	public final boolean invalidate(String name, String message) {
		UIPresentationMember member = get(name);
		if (member == null)
			return false;
		else {
			member.controlState = ControlState.ERROR;
			member.message = message;
			return true;
		}
	}

	public final void invalidateVisibleControls() {
		for (UIPresentationMember member : this)
			if (member.controlType != ControlType.NONE && member.controlType != ControlType.HIDDEN)
				member.controlState = ControlState.ERROR;
	}

	public final void invalidateVisibleControls(String message) {
		for (UIPresentationMember member : this)
			if (member.controlType != ControlType.NONE && member.controlType != ControlType.HIDDEN) {
				member.controlState = ControlState.ERROR;
				member.message = message;
			}
	}

	public final boolean setValue(String name, String value, ControlState state) {
		UIPresentationMember member = get(name);
		if (member == null)
			return false;
		else {
			member.controlState = state;
			member.message = "";
			member.value = value;
			return true;
		}
	}

	public final boolean setValue(String name, String value) {
		return setValue(name, value, ControlState.NORMAL);
	}

}
