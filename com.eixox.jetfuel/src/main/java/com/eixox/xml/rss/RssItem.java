package com.eixox.xml.rss;

import com.eixox.xml.rss.RssEnclosure;
import com.eixox.xml.rss.RssImage;
import com.eixox.xml.XmlElement;

// http://www.rssboard.org/rss-specification
public class RssItem {

	// The title of the item.
	@XmlElement
	private String title;

	// The URL of the item.
	@XmlElement
	private String url;

	// The Link of the item.
	@XmlElement
	private String link;

	// The item synopsis.
	@XmlElement
	private String description;

	// Email address of the author of the item."
	@XmlElement
	private String author;

	// Includes the item in one or more categories.
	@XmlElement(name = "category")
	private String[] categories;

	// URL of a page for comments relating to the item.
	@XmlElement
	private String comments;

	@XmlElement
	private RssEnclosure enclosure;

	@XmlElement
	private String pubDate;

	@XmlElement
	private String guid;

	@XmlElement(name = "content:encoded")
	private String contentEncoded;

	// Specifies a GIF, JPEG or PNG image that can be displayed with the
	// channel. More info
	// http://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt
	@XmlElement
	private RssImage image;

	@XmlElement(name = "feedburner:origLink")
	private String feedburnerorigLink;

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final String getUrl() {
		return url;
	}

	public final void setUrl(String url) {
		this.url = url;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final String getAuthor() {
		return author;
	}

	public final void setAuthor(String author) {
		this.author = author;
	}

	public final String[] getCategories() {
		return categories;
	}

	public final void setCategories(String[] categories) {
		this.categories = categories;
	}

	public final String getComments() {
		return comments;
	}

	public final void setComments(String comments) {
		this.comments = comments;
	}

	public final RssEnclosure getEnclosure() {
		return enclosure;
	}

	public final void setEnclosure(RssEnclosure enclosure) {
		this.enclosure = enclosure;
	}

	public final String getPubDate() {
		return pubDate;
	}

	public final void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public final String getGuid() {
		return guid;
	}

	public final void setGuid(String guid) {
		this.guid = guid;
	}

	public final String getContentEncoded() {
		return contentEncoded;
	}

	public final void setContentEncoded(String contentEncoded) {
		this.contentEncoded = contentEncoded;
	}

	public final RssImage getImage() {
		return image;
	}

	public final void setImage(RssImage image) {
		this.image = image;
	}

	public final String getLink() {
		return link;
	}

	public final void setLink(String link) {
		this.link = link;
	}

	public final String getFeedburnerorigLink() {
		return feedburnerorigLink;
	}

	public final void setFeedburnerorigLink(String feedburnerorigLink) {
		this.feedburnerorigLink = feedburnerorigLink;
	}

}
