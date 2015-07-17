package com.eixox.xml;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eixox.Strings;
import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public class XmlAspect extends AbstractAspect<XmlAspectMember> {

	public final String xmlName;
	public static final HashMap<Class<?>, XmlAspect> INSTANCES = new HashMap<Class<?>, XmlAspect>();
	public static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

	private XmlAspect(Class<?> dataType) {
		super(dataType);
		Xml annotation = dataType.getAnnotation(Xml.class);
		this.xmlName = annotation == null ?
				dataType.getName() :
				Strings.isNullOrEmptyAlternate(annotation.name(), dataType.getName());
	}

	@Override
	protected boolean decoratesParent() {
		return false;
	}

	@Override
	protected boolean decoratesProperties() {
		return false;
	}

	@Override
	protected XmlAspectMember decorate(AspectMember member) {
		Xml annotation = member.getAnnotation(Xml.class);
		return annotation == null ?
				null :
				new XmlAspectMember(member, annotation);
	}

	public static synchronized final XmlAspect getInstance(Class<?> claz) {
		XmlAspect aspect = INSTANCES.get(claz);
		if (aspect == null) {
			aspect = new XmlAspect(claz);
			INSTANCES.put(claz, aspect);
		}
		return aspect;
	}

	public final Object readXml(Document document) {
		NodeList nodes = document.getElementsByTagName(this.xmlName);
		if (nodes.getLength() < 1)
			return null;
		else {
			Element element = (Element) nodes.item(0);
			Object instance = newInstance();
			for (XmlAspectMember member : this)
				member.readXml(instance, element);
			return instance;
		}
	}

	public final Object readXml(InputStream input) {
		try {
			DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
			Document document = builder.parse(input);
			return readXml(document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final Object readXml(File file) {
		try {
			DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
			Document document = builder.parse(file);
			return readXml(document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
