package com.eixox.xml.atom;

import com.eixox.xml.XmlAttribute;

public class AtomCategory {

	@XmlAttribute
	private String term;

	@XmlAttribute
	private String schema;

	@XmlAttribute
	private String label;

	/**
	 * @return the term
	 */
	public final String getTerm() {
		return term;
	}

	/**
	 * @param term
	 *            the term to set
	 */
	public final void setTerm(String term) {
		this.term = term;
	}

	/**
	 * @return the schema
	 */
	public final String getSchema() {
		return schema;
	}

	/**
	 * @param schema
	 *            the schema to set
	 */
	public final void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * @return the label
	 */
	public final String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public final void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return this.term == null || this.term.isEmpty() ? this.label : this.term;
	}

}
