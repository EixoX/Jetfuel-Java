package com.eixox.xml.atom;

import java.util.ArrayList;

import com.eixox.StringHelper;
import com.eixox.xml.atom.AtomAuthor;
import com.eixox.xml.atom.AtomCategory;
import com.eixox.xml.atom.AtomContent;
import com.eixox.xml.atom.AtomContributor;
import com.eixox.xml.atom.AtomLink;
import com.eixox.xml.atom.AtomTitle;
import com.eixox.xml.XmlElement;

public class AtomEntry {

	@XmlElement
	private AtomTitle title;

	@XmlElement(name = "link")
	private ArrayList<AtomLink> links;

	@XmlElement
	private String id;

	@XmlElement
	private String updated;

	@XmlElement
	private String published;

	@XmlElement
	private AtomAuthor author;

	@XmlElement(name = "contributor")
	private ArrayList<AtomContributor> contributors;

	@XmlElement
	private AtomContent content;

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
	 * @return the published
	 */
	public final String getPublished() {
		return published;
	}

	/**
	 * @param published
	 *            the published to set
	 */
	public final void setPublished(String published) {
		this.published = published;
	}

	/**
	 * @return the author
	 */
	public final AtomAuthor getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public final void setAuthor(AtomAuthor author) {
		this.author = author;
	}

	/**
	 * @return the contributors
	 */
	public final ArrayList<AtomContributor> getContributors() {
		return contributors;
	}

	/**
	 * @param contributors
	 *            the contributors to set
	 */
	public final void setContributors(ArrayList<AtomContributor> contributors) {
		this.contributors = contributors;
	}

	/**
	 * @return the content
	 */
	public final AtomContent getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public final void setContent(AtomContent content) {
		this.content = content;
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
