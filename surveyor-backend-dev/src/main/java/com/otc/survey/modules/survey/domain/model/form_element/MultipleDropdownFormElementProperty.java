package com.otc.survey.modules.survey.domain.model.form_element;

import java.util.List;

public class MultipleDropdownFormElementProperty extends DropdownFormElementProperty<List<String>>
{
	protected List<Option> selectedOptions;
	
	@Override
	public Mode getMode() {
		return Mode.Single;
	}
}