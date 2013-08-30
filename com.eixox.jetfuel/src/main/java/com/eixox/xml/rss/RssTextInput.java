package com.eixox.xml.rss;

// From http://www.rssboard.org/rss-specification
// The purpose of the <textInput> element is something of a mystery. 
// You can use it to specify a search engine box. 
// Or to allow a reader to provide feedback. 
// Most aggregators ignore it.
//_________________________________________________________________
public class RssTextInput {

	//The label of the Submit button in the text input area.
	private String title;
	//Explains the text input area.
	private String description;
	//The name of the text object in the text input area.
	private String name;
	//The URL of the CGI script that processes text input requests.
	private String link;
	
	public final String getTitle() {
		return title;
	}
	public final void setTitle(String title) {
		this.title = title;
	}
	public final String getDescription() {
		return description;
	}
	public final void setDescription(String description) {
		this.description = description;
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final String getLink() {
		return link;
	}
	public final void setLink(String link) {
		this.link = link;
	}
	
	
}
