package com.eixox.data.html;

public class HtmlEmptyElement implements HtmlNode {

	private final HtmlAttributeList attributes = new HtmlAttributeList();
	private String tagName;

	public HtmlEmptyElement() {
	}

	public HtmlEmptyElement(String tagName) {
		this.tagName = tagName;
	}

	public final String getTagName() {
		return this.tagName;
	}

	public final HtmlEmptyElement setTagName(String name) {
		this.tagName = name;
		return this;
	}

	public final String getText() {
		return null;
	}

	public final void setText(String text) {
		throw new RuntimeException("Can't set text of an html element. Add it as a child text node.");
	}

	public final HtmlAttributeList getAttributes() {
		return this.attributes;
	}

	public final HtmlNodeList getChildren() {
		return null;
	}

	public final boolean canHaveText() {
		return false;
	}

	public final boolean canHaveAttributes() {
		return true;
	}

	public final boolean canHaveChildren() {
		return false;
	}

	public final int getAttributeCount() {
		return this.attributes.size();
	}

	public final String getAttribute(String name) {
		return this.attributes.get(name);
	}

	public final HtmlNode setAttribute(String name, String value) {
		this.attributes.set(name, value);
		return this;
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

	public final String getId() {
		return this.attributes.get("id");
	}

	public final void setId(String id) {
		this.attributes.set("id", id);
	}

	public final String getSytle() {
		return this.attributes.get("style");
	}

	public final void setStyle(String style) {
		this.attributes.set("style", style);
	}

	public final String getCssClass() {
		return this.attributes.get("class");
	}

	public final void setCssClass(String claz) {
		this.attributes.set("class", claz);
	}

	public final HtmlEmptyElement addCssClass(String claz) {
		final String cssClass = this.attributes.get(claz);
		if (cssClass == null || cssClass.isEmpty())
			this.attributes.set("class", claz);
		else if (!cssClass.equals(claz)) {
			if (!cssClass.contains(claz + " ") && !cssClass.contains(" " + claz))
				this.attributes.set("class", cssClass + " " + claz);
		}
		return this;
	}

	public final HtmlEmptyElement removeCssClass(String claz) {
		final String cssClass = this.attributes.get(claz);
		if (cssClass != null && !cssClass.isEmpty()) {
			if (cssClass.equals(claz))
				this.attributes.set("class", null);
			else if (cssClass.contains(claz + " "))
				attributes.set("class", cssClass.replace(claz + " ", ""));
			else if (cssClass.contains(" " + claz))
				attributes.set("class", cssClass.replace(" " + claz, ""));
		}
		return this;
	}

}
