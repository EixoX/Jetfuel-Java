package com.eixox.xml.rss;

import java.util.ArrayList;

import com.eixox.xml.rss.RssCloud;
import com.eixox.xml.rss.RssImage;
import com.eixox.xml.rss.RssItem;
import com.eixox.xml.rss.RssTextInput;
import com.eixox.xml.XmlElement;

//From http://www.rssboard.org/rss-specification

public class RssChannel {

	// The name of the channel.
	// It's how people refer to your service.
	// If you have an HTML website that contains the same information as your
	// RSS file,
	// the title of your channel should be the same as the title of your
	// website.
	@XmlElement
	private String title;

	// The URL to the HTML website corresponding to the channel.
	@XmlElement
	private String link;

	// Phrase or sentence describing the channel.
	@XmlElement
	private String description;

	// The language the channel is written in.
	// This allows aggregators to group all Italian language sites, for example,
	// on a single page.
	// A list of allowable values for this element, as provided by Netscape.
	// You may also use values defined by the W3C.
	@XmlElement
	private String language;

	// Copyright notice for content in the channel.
	@XmlElement
	private String copyright;

	// Email address for person responsible for editorial content.
	@XmlElement
	private String managingEditor;

	// Email address for person responsible for technical issues relating to
	// channel.
	@XmlElement
	private String webMaster;

	// The publication date for the content in the channel.
	// For example, the New York Times publishes on a daily basis,
	// the publication date flips once every 24 hours.
	// That's when the pubDate of the channel changes.
	// All date-times in RSS conform to the Date and Time Specification of RFC
	// 822,
	// with the exception that the year may be expressed with two characters or
	// four characters (four preferred).
	@XmlElement
	private String pubDate;

	// The last time the content of the channel changed.
	@XmlElement
	private String lastBuildDate;

	// Specify one or more categories that the channel belongs to. Follows the
	// same rules as the <item>-level category element.
	@XmlElement(name = "category")
	private String[] categories;

	// A string indicating the program used to generate the channel.
	@XmlElement
	private String generator;

	// A URL that points to the documentation for the format used in the RSS
	// file.
	// It's probably a pointer to this page.
	// It's for people who might stumble across an RSS file on a Web server 25
	// years from now and wonder what it is.
	@XmlElement
	private String docs;

	// Allows processes to register with a cloud to be notified of updates to
	// the channel,
	// implementing a lightweight publish-subscribe protocol for RSS feeds. More
	// info
	// http://www.rssboard.org/rss-specification#ltcloudgtSubelementOfLtchannelgt.
	@XmlElement
	private RssCloud cloud;

	// ttl stands for time to live.
	// It's a number of minutes that indicates how long a channel can be cached
	// before refreshing from the source.
	// More info
	// http://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt.
	@XmlElement
	private int ttl;

	// Specifies a GIF, JPEG or PNG image that can be displayed with the
	// channel. More info
	// http://www.rssboard.org/rss-specification#ltimagegtSubelementOfLtchannelgt
	@XmlElement
	private RssImage image;

	// The PICS rating for the channel.
	@XmlElement
	private String rating;

	// Specifies a text input box that can be displayed with the channel.
	@XmlElement
	private RssTextInput textInput;

	@XmlElement(name = "item")
	private ArrayList<RssItem> items;

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

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final String getLanguage() {
		return language;
	}

	public final void setLanguage(String language) {
		this.language = language;
	}

	public final String getCopyright() {
		return copyright;
	}

	public final void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public final String getManagingEditor() {
		return managingEditor;
	}

	public final void setManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
	}

	public final String getWebMaster() {
		return webMaster;
	}

	public final void setWebMaster(String webMaster) {
		this.webMaster = webMaster;
	}

	public final String getPubDate() {
		return pubDate;
	}

	public final void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public final String getLastBuildDate() {
		return lastBuildDate;
	}

	public final void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public final String[] getCategories() {
		return categories;
	}

	public final void setCategories(String[] categories) {
		this.categories = categories;
	}

	public final String getGenerator() {
		return generator;
	}

	public final void setGenerator(String generator) {
		this.generator = generator;
	}

	public final String getDocs() {
		return docs;
	}

	public final void setDocs(String docs) {
		this.docs = docs;
	}

	public final RssCloud getCloud() {
		return cloud;
	}

	public final void setCloud(RssCloud cloud) {
		this.cloud = cloud;
	}

	public final int getTtl() {
		return ttl;
	}

	public final void setTtl(int ttl) {
		this.ttl = ttl;
	}

	public final RssImage getImage() {
		return image;
	}

	public final void setImage(RssImage image) {
		this.image = image;
	}

	public final RssTextInput getTextInput() {
		return textInput;
	}

	public final void setTextInput(RssTextInput textInput) {
		this.textInput = textInput;
	}

	public final String getRating() {
		return rating;
	}

	public final void setRating(String rating) {
		this.rating = rating;
	}

	public final ArrayList<RssItem> getItems() {
		return items;
	}

	public final void setItems(ArrayList<RssItem> items) {
		this.items = items;
	}

}
