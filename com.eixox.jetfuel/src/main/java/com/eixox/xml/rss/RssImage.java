package com.eixox.xml.rss;

import com.eixox.xml.XmlElement;

//From http://www.rssboard.org/rss-specification
//_________________________________________________________________
public class RssImage {

	// Is the URL of a GIF, JPEG or PNG image that represents the channel.
	@XmlElement
	private String url;
	// Describes the image, it's used in the ALT attribute of the HTML <img> tag
	// when the channel is rendered in HTML.
	@XmlElement
	private String title;

	// Is the URL of the site, when the channel is rendered, the image is a link
	// to the site.
	// (Note, in practice the image <title> and <link> should have the same
	// value as the channel's <title> and <link>.
	@XmlElement
	private String link;

	// The width of the image in pixels.
	@XmlElement
	private int width = 88;

	// The height of the image in pixels.
	@XmlElement
	private int height = 31;

	// Contains text that is included in the TITLE attribute of the link formed
	// around the image in the HTML rendering.
	@XmlElement
	private String description;

	public final String getUrl() {
		return url;
	}

	public final void setUrl(String url) {
		this.url = url;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final String getLink() {
		return link;
	}

	public final void setLink(String link) {
		this.link = link;
	}

	public final int getWidth() {
		return width;
	}

	public final void setWidth(int width) {
		this.width = width;
	}

	public final int getHeight() {
		return height;
	}

	public final void setHeight(int height) {
		this.height = height;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

}
