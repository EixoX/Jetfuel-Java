package com.eixox.xml.atom;

import com.eixox.xml.XmlElement;
import com.eixox.xml.XmlText;

public class AtomGenerator {

	@XmlElement
	private String uri;

	@XmlElement
	private String version;

	@XmlText
	private String content;

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
	 * @return the version
	 */
	public final String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public final void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the content
	 */
	public final String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public final void setContent(String content) {
		this.content = content;
	}

}
