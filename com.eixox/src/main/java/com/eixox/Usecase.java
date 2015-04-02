package com.eixox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eixox.globalization.Culture;
import com.eixox.interceptors.InterceptorAspect;
import com.eixox.ui.UIControlPresentation;
import com.eixox.ui.UIControlState;

public abstract class Usecase {

	private final UsecaseAspect aspect;
	private final UsecaseProperty[] properties;

	public Usecase() {
		this.aspect = UsecaseAspect.getInstance(getClass());
		this.properties = new UsecaseProperty[aspect.getCount()];
		for (int i = 0; i < this.properties.length; i++) {
			this.properties[i] = new UsecaseProperty();
			aspect.get(i).write(this.properties[i]);
		}
	}

	public String getTitle() {
		return toString();
	}

	public final UsecaseAspect getAspect() {
		return aspect;
	}

	public final UsecaseProperty[] getProperties() {
		return properties;
	}

	public final void refreshProperties(Culture culture) {
		for (int i = 0; i < this.properties.length; i++) {
			UsecaseAspectMember member = aspect.get(i);
			member.write(this.properties[i]);
			this.properties[i].value = member.getValue(this);
		}
	}

	public void parse(NameValueCollection<String> items, Culture culture) {
		for (int i = 0; i < this.properties.length; i++) {
			UsecaseProperty property = this.properties[i];
			property.value = items.get(property.name);
		}
		parse(culture);
	}

	public void parse(HttpServletRequest request, Culture culture) {
		for (int i = 0; i < this.properties.length; i++) {
			UsecaseProperty property = this.properties[i];
			property.value = request.getParameter(property.name);
		}
		parse(culture);
	}

	public void parse(Culture culture) {
		for (int i = 0; i < this.properties.length; i++) {
			UsecaseProperty property = this.properties[i];
			UsecaseAspectMember member = this.aspect.get(i);
			member.parse(property, this, culture);
		}
	}

	public boolean validate() {
		boolean result = true;
		for (int i = 0; i < this.properties.length; i++) {
			UsecaseProperty property = this.properties[i];
			UsecaseAspectMember member = this.aspect.get(i);
			result &= member.validate(this, property);
		}
		return result;
	}

	public final void set(int ordinal, String value) {
		UsecaseProperty property = this.properties[ordinal];
		property.value = value;
		property.state = UIControlState.NORMAL;
		property.message = null;
	}

	public final void set(String name, String value) {
		set(getOrdinalOrException(name), value);
	}

	public final boolean trySet(String name, String value) {
		int ordinal = getOrdinal(name);
		if (ordinal < 0)
			return false;
		else {
			set(ordinal, value);
			return true;
		}
	}

	public final UsecaseProperty get(int ordinal) {
		return this.properties[ordinal];
	}

	public final UsecaseProperty get(String name) {
		return this.properties[getOrdinalOrException(name)];
	}

	public final int getOrdinal(String name) {
		for (int i = 0; i < this.properties.length; i++)
			if (name.equalsIgnoreCase(this.properties[i].name))
				return i;
		return -1;
	}

	public final int getOrdinalOrException(String name) {
		int ordinal = getOrdinal(name);
		if (ordinal >= 0)
			return ordinal;
		else
			throw new RuntimeException(name + " was not found on " + getClass().getName());
	}
	
	public final Map<String, UIControlPresentation> buildUIMap(Culture culture) {
		Map<String, UIControlPresentation> uiMap = new HashMap<String, UIControlPresentation>();
		for (UsecaseProperty prop : this.properties) {
			UsecaseAspectMember member = this.aspect.get(prop.name);
			if (member.isControl())
				uiMap.put(prop.name, member.buildUIPresentation(prop, culture));
		}
		
		return uiMap;
	}

	public final List<UIControlPresentation> buildUIPresentation(Culture culture) {
		ArrayList<UIControlPresentation> presentation = new ArrayList<UIControlPresentation>(this.properties.length);
		for (int i = 0; i < this.properties.length; i++)
		{
			UsecaseAspectMember member = this.aspect.get(i);
			if (member.isControl()) {
				presentation.add(member.buildUIPresentation(this.properties[i], culture));
			}
		}
		return presentation;
	}

	protected abstract void executeFlow(UsecaseResult result);

	public synchronized final UsecaseResult execute() {
		UsecaseResult result = new UsecaseResult();
		result.properties = this.properties;
		try {
			InterceptorAspect.getInstance(getClass()).apply(this);
			if (validate()) {
				executeFlow(result);
			}
			else {
				result.message = "Os campos estão inválidos";
				result.resultType = UsecaseResultType.VALIDATION_FAILED;
			}
		} catch (Exception ex) {
			result.exception = ex;
			result.resultType = UsecaseResultType.EXCEPTION;
			result.message = ex.getMessage();
		}
		return result;
	}
}
