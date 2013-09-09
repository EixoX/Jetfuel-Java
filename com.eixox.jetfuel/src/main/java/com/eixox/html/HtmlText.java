package com.eixox.html;

public class HtmlText implements HtmlNode {

	private String _text;

	public HtmlText(String text) {
		this._text = text;
	}

	
	public final String getTagName() {
		return "#text";
	}

	
	public final HtmlAttributeList getAttributes() {
		return null;
	}

	
	public final HtmlNodeList getChildren() {
		return null;
	}

	
	public final boolean isEmpty() {
		return true;
	}

	
	public final String getText() {
		return this._text;
	}

	public final void setText(String text) {
		this._text = text;
	}

}
