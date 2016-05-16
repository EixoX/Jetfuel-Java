package com.eixox.ui;

import com.eixox.reflection.AbstractAspectMember;
import com.eixox.reflection.AspectMember;
import com.eixox.restrictions.Length;
import com.eixox.restrictions.MaxLength;
import com.eixox.restrictions.Required;

public class UIAspectMember extends AbstractAspectMember {

	public final ControlType controlType;
	public final String label;
	public final String hint;
	public final String placeholder;
	public final boolean required;
	public final int maxlength;
	public final OptionList options;

	public static final ControlType getControlType(AspectMember member) {
		Control ctrl = member.getAnnotation(Control.class);
		return ctrl == null ? ControlType.NONE : ctrl.value();
	}

	public static final String getLabel(AspectMember member) {
		Label lbl = member.getAnnotation(Label.class);
		return lbl == null ? null : lbl.value();
	}

	public static final String getHint(AspectMember member) {
		Hint hnt = member.getAnnotation(Hint.class);
		return hnt == null ? null : hnt.value();
	}

	public static final String getPlaceHolder(AspectMember member) {
		Placeholder plh = member.getAnnotation(Placeholder.class);
		return plh == null ? null : plh.value();
	}

	public static final boolean getRequired(AspectMember member) {
		Required req = member.getAnnotation(Required.class);
		return req != null;
	}

	public static final int getMaxlength(AspectMember member) {
		MaxLength ml = member.getAnnotation(MaxLength.class);
		if (ml != null)
			return ml.value();
		else {
			Length lh = member.getAnnotation(Length.class);
			return lh == null ? 0 : lh.max();
		}
	}

	public static final OptionList getOptionList(AspectMember member) {
		Options opsAnn = member.getAnnotation(Options.class);
		if (opsAnn == null)
			return null;
		else {
			Class<?> opsCls = opsAnn.value();
			if (!OptionSource.class.isAssignableFrom(opsCls))
				throw new RuntimeException("Options must implement com.eixox.ui.OptionSource");
			else {
				try {
					OptionList list = ((OptionSource) opsCls.newInstance()).getOptions();
					DefaultOption df = member.getAnnotation(DefaultOption.class);
					if (df != null)
						list.add(0, new Option(df.key(), df.value()));
					return list;

				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
		}

	}

	public UIAspectMember(AspectMember member) {
		super(member);
		this.controlType = getControlType(member);
		if (this.controlType != ControlType.NONE) {
			this.label = getLabel(member);
			this.hint = getHint(member);
			this.placeholder = getPlaceHolder(member);
			this.required = getRequired(member);
			this.maxlength = getMaxlength(member);
			this.options = getOptionList(member);
		} else {
			this.label = null;
			this.hint = null;
			this.placeholder = null;
			this.required = false;
			this.maxlength = 0;
			this.options = null;
		}
	}

	public final UIPresentationMember buildPresentation() {
		UIPresentationMember member = new UIPresentationMember();
		member.controlType = this.controlType;
		member.hint = this.hint;
		member.id = this.getName();
		member.name = this.getName();
		member.label = this.label;
		member.maxlength = this.maxlength;
		member.options = this.options;
		member.placeholder = this.placeholder;
		member.required = this.required;
		member.value = "";
		return member;
	}

}
