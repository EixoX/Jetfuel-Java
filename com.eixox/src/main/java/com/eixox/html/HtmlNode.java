package com.eixox.html;

public interface HtmlNode {

	public String getTagName();
	public String getText();
	public void setText(String text);
	public HtmlAttributeList getAttributes();
	public HtmlNodeList getChildren();
	
	public boolean canHaveText();
	public boolean canHaveAttributes();
	public boolean canHaveChildren();
	
	public int getAttributeCount();
	public String getAttribute(String name);
	public HtmlNode setAttribute(String name, String value);
	public HtmlNode getFirstChild(String tagName);
	public HtmlNodeList getChildren(String tagName);
	public HtmlNode addChild(HtmlNode node);
	public boolean removeChild(HtmlNode node);
	
}
