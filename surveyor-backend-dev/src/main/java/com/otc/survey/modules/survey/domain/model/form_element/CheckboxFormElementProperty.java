package com.otc.survey.modules.survey.domain.model.form_element;

import java.util.List;

import com.otc.survey.modules.survey.domain.constant.FormElementTypeConst;

import lombok.Data;

public class CheckboxFormElementProperty extends FormElementProperty<List<CheckboxFormElementProperty.Option>>
{
	protected String name;
	protected List<Option> options;
	protected List<Option> selectedOptions;
	
	
	@Override
	public FormElementTypeConst getFormElementType() {
		return FormElementTypeConst.checkbox;
	}
	
	
	@Data
	public static class Option
	{
		protected String id;
		protected String label;
		protected String value;
		protected boolean selected;
	}
}