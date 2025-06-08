package com.otc.survey.modules.survey.domain.model.form_element;

import java.time.LocalDateTime;

import com.otc.survey.modules.survey.domain.constant.FormElementTypeConst;

public class DateTimeFormElementProperty extends FormElementProperty<LocalDateTime>
{
	protected String placeholder;
	protected LocalDateTime minValue;
	protected LocalDateTime maxValue;
	
	
	@Override
	public FormElementTypeConst getFormElementType() {
		return FormElementTypeConst.date_time;
	}
}