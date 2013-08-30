package com.eixox.globalization;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public class GlobalizationAspect extends AbstractAspect<GlobalizationAspectMember> {

	private final Locale locale;

	private GlobalizationAspect(Class<?> claz, Element element) {
		super(claz);
		String language = element.getAttribute("locale");

		this.locale = new Locale(language);
		NodeList nodes = element.getChildNodes();

		int count = nodes.getLength();
		for (int i = 0; i < count; i++) {
			Node node = nodes.item(i);
			int ordinal = super.getOrdinal(node.getLocalName());
			if (ordinal >= 0)
				super.get(ordinal).setElement((Element) node);
		}

	}

	@Override
	protected GlobalizationAspectMember decorate(AspectMember member) {
		return new GlobalizationAspectMember(member);
	}

	public final Locale getLocale() {
		return this.locale;
	}

	private static final HashMap<Class<?>, HashMap<String, GlobalizationAspect>> _LocaleInstances = new HashMap<Class<?>, HashMap<String, GlobalizationAspect>>();

	public static final GlobalizationAspect getInstance(Class<?> claz, String language) throws IOException,
			ParserConfigurationException, SAXException {
		HashMap<String, GlobalizationAspect> map = _LocaleInstances.get(claz);
		if (map == null) {
			map = new HashMap<String, GlobalizationAspect>();
			URL resource = claz.getResource(claz.getName() + ".xml");
			if (resource != null) {
				InputStream openStream = resource.openStream();
				try {
					DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
					Document document = documentBuilder.parse(openStream);
					NodeList globalizationElements = document.getElementsByTagName("Globalization");
					int nodeCount = globalizationElements.getLength();
					for (int i = 0; i < nodeCount; i++) {
						GlobalizationAspect aspect = new GlobalizationAspect(claz,
								(Element) globalizationElements.item(i));

						String localeKey = aspect.getLocale().getLanguage();
						map.put(localeKey, aspect);
					}
				} finally {
					openStream.close();
				}
			}
		}
		return map.get(language);
	}
}
