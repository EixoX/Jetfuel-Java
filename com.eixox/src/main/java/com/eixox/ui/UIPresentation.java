package com.eixox.ui;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class UIPresentation extends ArrayList<UIPresentationMember> {

	private static final long serialVersionUID = 6539046574902926538L;

	public UIPresentation() {
	}

	public UIPresentation(int size) {
		super(size);
	}

	public void parse(HttpServletRequest request) {
		for (UIPresentationMember member : this)
		{
			String itemValue =request.getParameter(member.name); 
			member.value = itemValue;
		}
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

}
