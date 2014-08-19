package com.eixox.web.controls;

public class Text implements Component {

	private static final long serialVersionUID = -177137459459300023L;
	public String content;

	public Text(String content) {
		this.content = content;
	}

	public final String getId() {
		return null;
	}

	public final void setId(String id) {
		// Can't set id on a text.
	}
}
