package com.eixox.xml.atom;

import com.eixox.xml.XmlAttribute;
import com.eixox.xml.XmlText;

public class AtomContent {

	@XmlAttribute
	private String type;

	@XmlAttribute(name = "xml:lang")
	private String lang;

	@XmlAttribute(name = "xml:base")
	private String base;

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
	 * @return the lang
	 */
	public final String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public final void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the base
	 */
	public final String getBase() {
		return base;
	}

	/**
	 * @param base the base to set
	 */
	public final void setBase(String base) {
		this.base = base;
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
