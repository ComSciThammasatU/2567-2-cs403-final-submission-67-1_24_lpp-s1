package com.otc.survey.modules.survey.domain.model.form_element;

import java.time.LocalDate;

import com.otc.survey.modules.survey.domain.constant.FormElementTypeConst;

public class DateFormElementProperty extends FormElementProperty<LocalDate>
{
	protected String placeholder;
	protected LocalDate minValue;
	protected LocalDate maxValue;
	
	
	@Override
	public FormElementTypeConst getFormElementType() {
		return FormElementTypeConst.date;
	}
}