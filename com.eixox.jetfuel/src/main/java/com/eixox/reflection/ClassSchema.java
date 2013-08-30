package com.eixox.reflection;

import java.util.HashMap;

public class ClassSchema extends AbstractAspect<DecoratedMember> {

	private ClassSchema(Class<?> claz) {
		super(claz);
	}

	@Override
	protected DecoratedMember decorate(AspectMember member) {
		return new DecoratedMember(member);
	}

	private static HashMap<Class<?>, ClassSchema> _Instances = new HashMap<Class<?>, ClassSchema>();

	public static ClassSchema getInstance(Class<?> claz) {
		ClassSchema schema = _Instances.get(claz);
		if (schema == null) {
			schema = new ClassSchema(claz);
			_Instances.put(claz, schema);
		}
		return schema;
	}

}
