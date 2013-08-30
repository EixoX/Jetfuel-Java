package com.eixox.ui;

import java.util.List;

public class UIControlState {

	private Object value;
	private List<UIControlOption> options;
	private String label;
	private String hint;
	private String errorMessage;
	private String infoMessage;
	private String successMessage;
	private String id;
	private String name;
	private boolean error;
	private boolean success;
	private boolean info;
	private UIControlType memberType;

	public final Object getValue() {
		return value;
	}

	public final void setValue(Object value) {
		this.value = value;
	}

	public final List<UIControlOption> getOptions() {
		return options;
	}

	public final void setOptions(List<UIControlOption> options) {
		this.options = options;
	}

	public final String getLabel() {
		return label;
	}

	public final void setLabel(String label) {
		this.label = label;
	}

	public final String getHint() {
		return hint;
	}

	public final void setHint(String hint) {
		this.hint = hint;
	}

	public final String getErrorMessage() {
		return errorMessage;
	}

	public final void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public final String getInfoMessage() {
		return infoMessage;
	}

	public final void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}

	public final String getSuccessMessage() {
		return successMessage;
	}

	public final void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final boolean isError() {
		return this.error;
	}

	public final void setError(boolean error, String message) {
		this.error = error;
		this.errorMessage = message;
	}

	public final boolean isInfo() {
		return this.info;
	}

	public final void setInfo(boolean info, String message) {
		this.info = info;
		this.infoMessage = message;
	}

	public final boolean isSuccess() {
		return this.success;
	}

	public final void setSuccess(boolean success, String successMessage) {
		this.success = success;
		this.successMessage = successMessage;
	}

	public final UIControlType getMemberType() {
		return memberType;
	}

	public final void setMemberType(UIControlType memberType) {
		this.memberType = memberType;
	}

	
}
