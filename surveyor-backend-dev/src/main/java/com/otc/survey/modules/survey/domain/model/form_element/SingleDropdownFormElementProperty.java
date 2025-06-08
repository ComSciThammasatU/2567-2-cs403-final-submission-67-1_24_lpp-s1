package com.otc.survey.modules.survey.domain.model.form_element;

public class SingleDropdownFormElementProperty extends DropdownFormElementProperty<String>
{
	protected Option selectedOption;

	@Override
	public Mode getMode() {
		return Mode.Single;
	}
}