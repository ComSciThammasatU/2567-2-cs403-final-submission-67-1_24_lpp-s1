package com.otc.survey.modules.survey.domain.model.form_element;

import com.otc.survey.modules.survey.domain.constant.FormElementTypeConst;

public class NumberFormElementProperty extends FormElementProperty<Long>
{
	protected String placeholder;
	protected Long minValue;
	protected Long maxValue;
	
	
	@Override
	public FormElementTypeConst getFormElementType() {
		return FormElementTypeConst.number;
	}
}