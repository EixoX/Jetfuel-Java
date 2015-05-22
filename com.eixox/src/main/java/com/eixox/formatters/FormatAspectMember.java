package com.eixox.formatters;

import com.eixox.globalization.Cultures;
import com.eixox.reflection.AbstractAspectMember;
import com.eixox.reflection.AspectMember;

public class FormatAspectMember extends AbstractAspectMember {

	private final ValueFormatter<?> formatter;
	public final String title;

	@SuppressWarnings("rawtypes")
	public FormatAspectMember(AspectMember member) {
		super(member);

		Title title = member.getAnnotation(Title.class);
		this.title = title == null ? member.getName() : title.value();

		TextFormat tf = member.getAnnotation(TextFormat.class);
		if (tf == null) {
			this.formatter = new StringFormatter();
		}
		else {
			try {
				String fmat = tf.format();
				if (fmat == null || fmat.isEmpty()) {
					this.formatter = tf.type().newInstance();
				}
				else {
					this.formatter = tf.type().getConstructor(String.class).newInstance(fmat);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public String getFormattedValue(Object instance) {
		Object value = super.getValue(instance);
		return this.formatter.formatObject(value, Cultures.EN_US);
	}

}
