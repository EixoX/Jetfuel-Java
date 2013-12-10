package com.eixox.html;

public interface HtmlNode {

	public String getTagName();

	public HtmlAttributeList getAttributes();

	public HtmlNodeList getChildren();

	public boolean isEmpty();

	public String getText();
}
