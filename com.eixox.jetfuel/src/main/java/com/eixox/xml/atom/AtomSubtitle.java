package com.eixox.xml.atom;

import com.eixox.xml.XmlAttribute;
import com.eixox.xml.XmlText;

public class AtomSubtitle {

	@XmlAttribute
	private String type;
	
	@XmlText
	private String content;

	/**
	 * @return the type
	 */
	public final String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the content
	 */
	public final String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public final void setContent(String content) {
		this.content = content;
	}
	
	
}
