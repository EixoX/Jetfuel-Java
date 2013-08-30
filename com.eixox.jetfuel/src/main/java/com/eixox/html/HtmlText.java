package com.eixox.html;

public class HtmlText implements HtmlNode {

	private String _text;

	public HtmlText(String text) {
		this._text = text;
	}

	@Override
	public final String getTagName() {
		return "#text";
	}

	@Override
	public final HtmlAttributeList getAttributes() {
		return null;
	}

	@Override
	public final HtmlNodeList getChildren() {
		return null;
	}

	@Override
	public final boolean isEmpty() {
		return true;
	}

	@Override
	public final String getText() {
		return this._text;
	}

	public final void setText(String text) {
		this._text = text;
	}

}
