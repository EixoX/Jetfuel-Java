package com.eixox.xml.atom;

import com.eixox.xml.XmlElement;

public class AtomAuthor {

	@XmlElement
	private String name;

	@XmlElement
	private String uri;

	@XmlElement
	private String email;

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the uri
	 */
	public final String getUri() {
		return uri;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public final void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the email
	 */
	public final String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public final void setEmail(String email) {
		this.email = email;
	}

}
