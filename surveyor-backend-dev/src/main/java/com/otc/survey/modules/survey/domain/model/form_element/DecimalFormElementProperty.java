package com.otc.survey.modules.survey.domain.model.form_element;

import java.math.BigDecimal;

import com.otc.survey.modules.survey.domain.constant.FormElementTypeConst;

public class DecimalFormElementProperty extends FormElementProperty<BigDecimal>
{
	protected String placeholder;
	protected BigDecimal minValue;
	protected BigDecimal maxValue;
	protected int decimalPoint;
	
	
	@Override
	public FormElementTypeConst getFormElementType() {
		return FormElementTypeConst.number;
	}
}