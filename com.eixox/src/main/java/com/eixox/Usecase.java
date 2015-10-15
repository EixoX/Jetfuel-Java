package com.eixox;

import com.eixox.globalization.Culture;
import com.eixox.ui.ControlState;
import com.eixox.ui.UIPresentation;
import com.eixox.ui.UIPresentationMember;

public abstract class Usecase {

	public final UsecaseAspect aspect;
	public final UIPresentation presentation;

	public Usecase() {
		this.aspect = UsecaseAspect.getInstance(getClass());
		this.presentation = new UIPresentation(this.aspect.getCount());
		for (UsecaseAspectMember member : this.aspect) {
			this.presentation.add(new UIPresentationMember(member.ui));
		}
	}

	public final void refreshPresentation() {
		int l = this.aspect.getCount();
		for (int i = 0; i < l; i++) {
			String newValue = this.aspect.get(i).getValueToString(this);
			this.presentation.get(i).value = newValue;
		}
	}

	public String getTitle() {
		return toString();
	}

	public final void parse(Culture culture) {

		int l = this.presentation.size();
		for (int i = 0; i < l; i++) {
			this.aspect.get(i).parse(
					this,
					culture,
					this.presentation
							.get(i).value);
		}
	}

	protected void postExecute(UsecaseResult result) {

	}

	public synchronized boolean validate() {
		int l = this.presentation.size();
		boolean valid = true;
		for (int i = 0; i < l; i++) {
			UsecaseAspectMember uam = this.aspect.get(i);
			Object value = uam.getValue(this);
			UIPresentationMember uipm = this.presentation.get(i);
			uipm.message = uam.restrictions.getRestrictionMessageFor(value);
			if (uipm.message != null && uipm.message.length() > 0) {
				uipm.controlState = ControlState.ERROR;
				valid = false;
			} else
				uipm.controlState = ControlState.SUCCESS;
		}
		return valid;
	}

	protected abstract void executeFlow(UsecaseResult result) throws Exception;

	public synchronized final UsecaseResult execute() {
		UsecaseResult result = new UsecaseResult();
		result.presentation = this.presentation;
		try {
			if (validate()) {
				executeFlow(result);
			} else {
				result.message = "Os campos estão inválidos";
				result.resultType = UsecaseResultType.VALIDATION_FAILED;
			}
		} catch (Exception ex) {
			result.exception = ex;
			result.resultType = UsecaseResultType.EXCEPTION;
			result.message = ex.getMessage();
		}
		postExecute(result);
		return result;
	}

	public static final boolean isValidName(String name) {
		if (name == null || name.isEmpty())
			return false;

		int l = name.length();

		if (l > 255)
			return false;

		for (int i = 0; i < l; i++) {
			char c = name.charAt(i);
			if (!Character.isLetterOrDigit(c))
				if (c != '-')
					return false;

		}

		return true;
	}

}
