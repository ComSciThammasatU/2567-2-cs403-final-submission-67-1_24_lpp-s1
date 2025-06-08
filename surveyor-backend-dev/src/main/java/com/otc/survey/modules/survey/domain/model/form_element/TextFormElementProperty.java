package com.otc.survey.modules.survey.domain.model.form_element;

import com.otc.survey.modules.survey.domain.constant.FormElementTypeConst;

public class TextFormElementProperty extends FormElementProperty<String>
{
	protected String placeholder;
	protected String minLength;
	protected String maxLength;
	
	
	@Override
	public FormElementTypeConst getFormElementType() {
		return FormElementTypeConst.text;
	}
}