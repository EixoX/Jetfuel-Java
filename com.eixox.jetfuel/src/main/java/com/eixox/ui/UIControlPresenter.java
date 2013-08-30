package com.eixox.ui;

public interface UIControlPresenter {

	public void present(UIControlState state);

	public void presentHidden(UIControlState state);

	public void presentSingleLine(UIControlState state);

	public void presentPassword(UIControlState state);

	public void presentTextarea(UIControlState state);

	public void presentHtml(UIControlState state);

	public void presentDatePicker(UIControlState state);

	public void presentDropDown(UIControlState state);

	public void presentRadioButton(UIControlState state);

	public void presentRadioGroup(UIControlState state);

	public void presentCheckbox(UIControlState state);

	public void presentCheckboxGroup(UIControlState state);

}
