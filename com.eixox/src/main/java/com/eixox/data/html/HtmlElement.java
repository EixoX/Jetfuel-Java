package com.eixox.data.html;

public class HtmlElement implements HtmlNode {

	private final HtmlAttributeList attributes = new HtmlAttributeList();
	private final HtmlNodeList children = new HtmlNodeList();
	private String tagName;

	public HtmlElement() {
	}

	public HtmlElement(String tagName) {
		this.tagName = tagName;
	}

	public final String getTagName() {
		return this.tagName;
	}

	public final HtmlElement setTagName(String name) {
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
		return this.children;
	}

	public final boolean canHaveText() {
		return false;
	}

	public final boolean canHaveAttributes() {
		return true;
	}

	public final boolean canHaveChildren() {
		return true;
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
		return this.children.getFirst(tagName);
	}

	public final HtmlNodeList getChildren(String tagName) {
		return this.children.getChildren(tagName);
	}

	public final HtmlNode addChild(HtmlNode node) {
		this.children.add(node);
		return this;
	}

	public final boolean removeChild(HtmlNode node) {
		return this.children.remove(node);
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

	public final void addCssClass(String claz) {
		final String cssClass = this.attributes.get(claz);
		if (cssClass == null || cssClass.isEmpty())
			this.attributes.set("class", claz);
		else if (!cssClass.equals(claz)) {
			if (!cssClass.contains(claz + " ") && !cssClass.contains(" " + claz))
				this.attributes.set("class", cssClass + " " + claz);
		}
	}

	public final void removeCssClass(String claz) {
		final String cssClass = this.attributes.get(claz);
		if (cssClass != null && !cssClass.isEmpty()) {
			if (cssClass.equals(claz))
				this.attributes.set("class", null);
			else if (cssClass.contains(claz + " "))
				attributes.set("class", cssClass.replace(claz + " ", ""));
			else if (cssClass.contains(" " + claz))
				attributes.set("class", cssClass.replace(" " + claz, ""));
		}
	}

}
