package com.eixox.xml;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.w3c.dom.Element;

import com.eixox.Strings;
import com.eixox.adapters.ValueAdapter;
import com.eixox.adapters.ValueAdapters;
import com.eixox.reflection.AbstractAspectMember;
import com.eixox.reflection.AspectMember;
import com.eixox.xml.adapters.XmlAdapter;
import com.eixox.xml.adapters.XmlObjectAdapter;
import com.eixox.xml.adapters.XmlObjectArrayAdapter;
import com.eixox.xml.adapters.XmlObjectListAdapter;
import com.eixox.xml.adapters.XmlValueAdapter;
import com.eixox.xml.adapters.XmlValueArrayAdapter;
import com.eixox.xml.adapters.XmlValueListAdapter;

public class XmlAspectMember extends AbstractAspectMember implements XmlAdapter {

	public final XmlAdapter xmlAdapter;

	public XmlAspectMember(AspectMember member, Xml annotation) {
		super(member);
		
		String xmlName = Strings.isNullOrEmptyAlternate(annotation.name() , member.getName());
		Class<?> dataType = member.getDataType();
		Class<?> componentType = null;
		ValueAdapter<?> valueAdapter = null; 
		if(dataType.isArray()){
			componentType = dataType.getComponentType();
			valueAdapter = ValueAdapters.getAdapter(componentType);
			this.xmlAdapter = valueAdapter == null ? 
					new XmlObjectArrayAdapter(xmlName, componentType):
					new XmlValueArrayAdapter(xmlName, annotation.type(), valueAdapter);
		}
		else if(List.class.isAssignableFrom(dataType)){
			ParameterizedType genericType = (ParameterizedType)member.getGenericType();
			componentType = (Class<?>) genericType.getActualTypeArguments()[0];
			valueAdapter = ValueAdapters.getAdapter(componentType);
			this.xmlAdapter = valueAdapter == null ?
					new XmlObjectListAdapter(xmlName, componentType):
					new XmlValueListAdapter(xmlName, annotation.type(), valueAdapter);
		}
		else {
			valueAdapter = ValueAdapters.getAdapter(dataType);
			this.xmlAdapter = valueAdapter == null ? 
					new XmlObjectAdapter(xmlName, dataType):
					new XmlValueAdapter(xmlName, annotation.type(), valueAdapter);
		}
	}

	public Object readXml(Element parent) {
		return this.xmlAdapter.readXml(parent);
	}

	public void readXml(Object instance, Element parent) {
		Object value = this.xmlAdapter.readXml(parent);
		if (value != null)
			super.setValue(instance, value);
	}

	public void writeXml(Element parent, Object value) {
		Object content = super.getValue(value);
		this.xmlAdapter.writeXml(parent, content);
	}

}
