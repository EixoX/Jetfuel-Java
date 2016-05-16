package com.eixox.data.html;

import java.util.LinkedList;

public class HtmlNodeList extends LinkedList<HtmlNode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8685600860505283635L;

	public final HtmlNode getFirst(String tagName) {
		for (HtmlNode child : this)
			if (tagName.equalsIgnoreCase(child.getTagName()))
				return child;
		return null;
	}
	
	public final HtmlNodeList getChildren(String tagName){
		HtmlNodeList list = new HtmlNodeList();
		for(HtmlNode child : this)
			if(tagName.equalsIgnoreCase(child.getTagName()))
				list.add(child);
		return list;
	}

}
