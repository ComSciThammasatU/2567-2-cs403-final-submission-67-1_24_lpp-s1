package com.otc.survey.modules.survey.domain.model.form_element;

import com.otc.survey.modules.survey.domain.constant.FormElementTypeConst;

public class TextAreaFormElementProperty extends FormElementProperty<String>
{
	protected String placeholder;
	protected String minLength;
	protected String maxLength;
	protected int rows;
	protected int columns;
	
	
	@Override
	public FormElementTypeConst getFormElementType() {
		return FormElementTypeConst.text_area;
	}
}