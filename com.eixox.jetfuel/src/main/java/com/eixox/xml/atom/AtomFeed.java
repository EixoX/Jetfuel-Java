package com.eixox.xml.atom;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.eixox.StringHelper;
import com.eixox.xml.atom.AtomCategory;
import com.eixox.xml.atom.AtomEntry;
import com.eixox.xml.atom.AtomFeed;
import com.eixox.xml.atom.AtomGenerator;
import com.eixox.xml.atom.AtomLink;
import com.eixox.xml.atom.AtomSubtitle;
import com.eixox.xml.atom.AtomTitle;
import com.eixox.xml.XmlAspect;
import com.eixox.xml.XmlElement;

public class AtomFeed {

	@XmlElement
	private AtomTitle title;

	@XmlElement
	private AtomSubtitle subtitle;

	@XmlElement
	private String id;

	@XmlElement
	private String updated;

	@XmlElement(name = "link")
	private ArrayList<AtomLink> links;

	@XmlElement
	private String rights;

	@XmlElement
	private AtomGenerator generator;

	@XmlElement(name = "entry")
	private ArrayList<AtomEntry> entries;

	@XmlElement(name = "category")
	private ArrayList<AtomCategory> categories;

	/**
	 * @return the title
	 */
	public final AtomTitle getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public final void setTitle(AtomTitle title) {
		this.title = title;
	}

	/**
	 * @return the subtitle
	 */
	public final AtomSubtitle getSubtitle() {
		return subtitle;
	}

	/**
	 * @param subtitle
	 *            the subtitle to set
	 */
	public final void setSubtitle(AtomSubtitle subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the updated
	 */
	public final String getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            the updated to set
	 */
	public final void setUpdated(String updated) {
		this.updated = updated;
	}

	/**
	 * @return the links
	 */
	public final ArrayList<AtomLink> getLinks() {
		return links;
	}

	/**
	 * @param links
	 *            the links to set
	 */
	public final void setLinks(ArrayList<AtomLink> links) {
		this.links = links;
	}

	/**
	 * @return the rights
	 */
	public final String getRights() {
		return rights;
	}

	/**
	 * @param rights
	 *            the rights to set
	 */
	public final void setRights(String rights) {
		this.rights = rights;
	}

	/**
	 * @return the generator
	 */
	public final AtomGenerator getGenerator() {
		return generator;
	}

	/**
	 * @param generator
	 *            the generator to set
	 */
	public final void setGenerator(AtomGenerator generator) {
		this.generator = generator;
	}

	/**
	 * @return the entries
	 */
	public final ArrayList<AtomEntry> getEntries() {
		return entries;
	}

	/**
	 * @param entries
	 *            the entries to set
	 */
	public final void setEntries(ArrayList<AtomEntry> entries) {
		this.entries = entries;
	}

	/**
	 * @return the categories
	 */
	public final ArrayList<AtomCategory> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public final void setCategories(ArrayList<AtomCategory> categories) {
		this.categories = categories;
	}

	private static XmlAspect aspect;

	public boolean read(Document document) {
		NodeList elementsByTagName = document.getElementsByTagName("feed");
		if (elementsByTagName.getLength() != 1)
			return false;
		else {
			if (aspect == null)
				aspect = XmlAspect.getInstance(AtomFeed.class);
			aspect.read(this, (Element) elementsByTagName.item(0));
			return true;
		}
	}

	// Reads a document from a given url.
	public boolean read(String url) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder newDocumentBuilder = builderFactory.newDocumentBuilder();
		Document parse = newDocumentBuilder.parse(url);
		return read(parse);
	}
	
	public final String getLinkHref(String... rels) {

		if (this.links == null || this.links.size() == 0)
			return null;

		if (rels != null && rels.length > 0) {
			for (AtomLink link : this.links)
				for (int i = 0; i < rels.length; i++)
					if (StringHelper.equalsIgnoreCase(rels[i], link.getRel()))
						return link.getHref();
		} else {
			for (AtomLink link : this.links)
				if (link.getRel() == null || link.getRel().isEmpty())
					return link.getHref();
		}

		return null;

	}

}
