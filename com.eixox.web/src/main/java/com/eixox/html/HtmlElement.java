package com.eixox.html;

public class HtmlElement {

	private String tagName;
	private HtmlAttributeList attributes;
	private HtmlNodeList children;

	public HtmlElement() {
		this.attributes = new HtmlAttributeList();
		this.children = new HtmlNodeList();
	}

	/**
	 * @return the tagName
	 */
	public final String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName
	 *            the tagName to set
	 */
	public final void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @return the attributes
	 */
	public final HtmlAttributeList getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public final void setAttributes(HtmlAttributeList attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the children
	 */
	public final HtmlNodeList getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public final void setChildren(HtmlNodeList children) {
		this.children = children;
	}

}
