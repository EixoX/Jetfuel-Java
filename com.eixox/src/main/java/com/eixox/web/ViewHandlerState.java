package com.eixox.web;

public class ViewHandlerState {

	private String successMessage;
	private String errorMessage;
	private String warningMessage;

	/**
	 * @return the successMessage
	 */
	public final String getSuccessMessage() {
		return successMessage;
	}

	/**
	 * @param successMessage
	 *            the successMessage to set
	 */
	public final void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	/**
	 * @return the errorMessage
	 */
	public final String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public final void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the warningMessage
	 */
	public final String getWarningMessage() {
		return warningMessage;
	}

	/**
	 * @param warningMessage
	 *            the warningMessage to set
	 */
	public final void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

}
