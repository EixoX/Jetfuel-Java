package com.eixox.xml;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;

import com.eixox.adapters.ValueAdapter;
import com.eixox.adapters.ValueAdapters;
import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public class XmlAspect extends AbstractAspect<XmlAspectMember> {

	private XmlAspect(Class<?> claz) {
		super(claz);
	}

	@Override
	protected XmlAspectMember decorate(AspectMember member) {
		XmlAttribute xa = member.getAnnotation(XmlAttribute.class);
		if (xa != null)
			return new XmlAspectMemberAttribute(member, xa.name(), ValueAdapters.getAdapter(member.getType()));

		XmlText xt = member.getAnnotation(XmlText.class);
		if (xt != null)
			return new XmlAspectMemberText(member, null, ValueAdapters.getAdapter(member.getType()));

		XmlElement xe = member.getAnnotation(XmlElement.class);
		if (xe == null)
			return null;

		Class<?> claz = member.getType();
		// Is it an array?
		if (claz.isArray()) {
			Class<?> childArrayType = claz.getComponentType();
			ValueAdapter<?> childArrayAdapter = ValueAdapters.getAdapter(childArrayType);
			if (childArrayAdapter == null)
				return new XmlAspectMemberCompositeArray(member, xe.name(), childArrayType, XmlAspect.getInstance(childArrayType));
			else
				return new XmlAspectMemberSimpleArray(member, xe.name(), childArrayType, childArrayAdapter);
		}
		// Is it a list?
		else if (List.class.isAssignableFrom(claz)) {
			Class<?> childListType = (Class<?>) ((ParameterizedType) member.getGenericType()).getActualTypeArguments()[0];
			ValueAdapter<?> childListAdapter = ValueAdapters.getAdapter(childListType);
			if (childListAdapter == null)
				return new XmlAspectMemberCompositeList(member, xe.name(), childListType, XmlAspect.getInstance(childListType));
			else
				return new XmlAspectMemberSimpleList(member, xe.name(), childListAdapter);
		}
		// It can only be a simple or composite element
		else {
			Class<?> childType = member.getType();
			ValueAdapter<?> childAdapter = ValueAdapters.getAdapter(childType);
			if (childAdapter == null)
				return new XmlAspectMemberComposite(member, xe.name(), childType, XmlAspect.getInstance(childType));
			else
				return new XmlAspectMemberSimple(member, xe.name(), childAdapter);
		}

	}

	public final void read(Object entity, Element element) {
		for (XmlAspectMember member : this) {
			member.read(entity, element);
		}
	}

	private static final HashMap<Class<?>, XmlAspect> _instances = new HashMap<Class<?>, XmlAspect>();

	public static synchronized final XmlAspect getInstance(Class<?> claz) {
		XmlAspect aspect = _instances.get(claz);
		if (aspect == null) {
			aspect = new XmlAspect(claz);
			_instances.put(claz, aspect);
		}
		return aspect;
	}

}
