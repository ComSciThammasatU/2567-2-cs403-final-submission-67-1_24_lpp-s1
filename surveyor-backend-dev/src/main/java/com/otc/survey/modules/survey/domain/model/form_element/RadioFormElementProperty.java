package com.otc.survey.modules.survey.domain.model.form_element;

import java.util.List;

import com.otc.survey.modules.survey.domain.constant.FormElementTypeConst;

import lombok.Data;

public class RadioFormElementProperty extends FormElementProperty<RadioFormElementProperty.Option>
{
	protected String name;
	protected List<Option> options;
	protected Option selectedOption;
	
	
	@Override
	public FormElementTypeConst getFormElementType() {
		return FormElementTypeConst.radio;
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