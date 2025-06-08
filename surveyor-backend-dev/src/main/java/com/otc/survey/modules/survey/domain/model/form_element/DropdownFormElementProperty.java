package com.otc.survey.modules.survey.domain.model.form_element;

import java.util.List;

import com.otc.survey.modules.survey.domain.constant.FormElementTypeConst;

import lombok.Data;

public abstract class DropdownFormElementProperty<T> extends FormElementProperty<T>
{
	protected Mode mode;
	protected String placeholder;
	protected List<Option> options;
	
	@Override
	public FormElementTypeConst getFormElementType() {
		return FormElementTypeConst.dropdown;
	}
	
	public abstract Mode getMode();
	
	@Data
	public static class Option
	{
		protected String id; // html element id
		protected String value;
		protected String text;
	}
	
	public static enum Mode
	{
		Single, Multiple
	}
}