package com.otc.survey.modules.survey.domain.model.form_element;

import java.time.LocalTime;

import com.otc.survey.modules.survey.domain.constant.FormElementTypeConst;

public class TimeFormElementProperty extends FormElementProperty<LocalTime>
{
	protected String placeholder;
	protected LocalTime minValue;
	protected LocalTime maxValue;
	
	
	@Override
	public FormElementTypeConst getFormElementType() {
		return FormElementTypeConst.time;
	}
}