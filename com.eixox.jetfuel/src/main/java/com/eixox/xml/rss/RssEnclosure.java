package com.eixox.xml.rss;

import com.eixox.xml.XmlAttribute;

public class RssEnclosure {

	@XmlAttribute
	private String url;
	
	@XmlAttribute
	private int length;
	
	@XmlAttribute
	private String type;

	public final String getUrl() {
		return url;
	}

	public final void setUrl(String url) {
		this.url = url;
	}

	public final int getLength() {
		return length;
	}

	public final void setLength(int length) {
		this.length = length;
	}

	public final String getType() {
		return type;
	}

	public final void setType(String type) {
		this.type = type;
	}
	
	
}
