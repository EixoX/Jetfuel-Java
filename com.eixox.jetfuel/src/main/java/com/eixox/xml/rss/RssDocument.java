package com.eixox.xml.rss;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.eixox.xml.rss.RssChannel;
import com.eixox.xml.rss.RssDocument;
import com.eixox.xml.XmlAspect;
import com.eixox.xml.XmlAttribute;
import com.eixox.xml.XmlElement;

public class RssDocument {

	// At the top level, a RSS document is a <rss> element,
	// with a mandatory attribute called version,
	// that specifies the version of RSS that the document conforms to.
	// If it conforms to this specification, the version attribute must be 2.0.
	@XmlAttribute
	private String version;

	// Subordinate to the <rss> element is a single <channel> element,
	// which contains information about the channel (metadata) and its contents.
	@XmlElement
	private RssChannel channel;

	public final String getVersion() {
		return version;
	}

	public final void setVersion(String version) {
		this.version = version;
	}

	public final RssChannel getChannel() {
		return channel;
	}

	public final void setChannel(RssChannel channel) {
		this.channel = channel;
	}

	private static XmlAspect aspect;

	public boolean read(Document document) {
		NodeList elementsByTagName = document.getElementsByTagName("rss");
		if (elementsByTagName.getLength() != 1)
			return false;
		else {
			if (aspect == null)
				aspect = XmlAspect.getInstance(RssDocument.class);
			aspect.read(this, (Element) elementsByTagName.item(0));
			return true;
		}
	}

	//Reads a document from a given url.
	public boolean read(String url) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder newDocumentBuilder = builderFactory.newDocumentBuilder();
		Document parse = newDocumentBuilder.parse(url);
		return read(parse);
	}

}
