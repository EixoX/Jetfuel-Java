package com.eixox.xml.atom;

import com.eixox.xml.XmlAttribute;

public class AtomLink {

	@XmlAttribute
	private String rel;

	@XmlAttribute
	private String type;

	@XmlAttribute
	private String href;

	@XmlAttribute
	private String length;

	/**
	 * @return the rel
	 */
	public final String getRel() {
		return rel;
	}

	/**
	 * @param rel the rel to set
	 */
	public final void setRel(String rel) {
		this.rel = rel;
	}

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
	 * @return the href
	 */
	public final String getHref() {
		return href;
	}

	/**
	 * @param href the href to set
	 */
	public final void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the length
	 */
	public final String getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public final void setLength(String length) {
		this.length = length;
	}

	
}
