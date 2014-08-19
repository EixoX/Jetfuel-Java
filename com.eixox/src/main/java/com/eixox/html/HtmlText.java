package com.eixox.html;

public class HtmlText implements HtmlNode {

	private String text;

	public final String getTagName() {
		return "#text";
	}

	public final String getText() {
		return this.text;
	}

	public final void setText(String value) {
		this.text = value;
	}

	public final HtmlAttributeList getAttributes() {
		return null;
	}

	public final HtmlNodeList getChildren() {
		return null;
	}

	public final boolean canHaveText() {
		return true;
	}

	public final boolean canHaveAttributes() {
		return false;
	}
	
	public final boolean canHaveChildren(){
		return false;
	}

	public final int getAttributeCount() {
		return -1;
	}

	public final String getAttribute(String name) {
		return null;
	}

	public final HtmlNode setAttribute(String name, String value) {
		return null;
	}

	public final HtmlNode getFirstChild(String tagName) {
		return null;
	}

	public final HtmlNodeList getChildren(String tagName) {
		return null;
	}

	public final HtmlNode addChild(HtmlNode node) {
		return null;
	}

	public final boolean removeChild(HtmlNode node) {
		return false;
	}

}
