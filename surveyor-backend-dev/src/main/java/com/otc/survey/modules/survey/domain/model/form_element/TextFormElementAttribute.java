package com.otc.survey.modules.survey.domain.model.form_element;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class TextFormElementAttribute extends FormElementAttribute 
{
	protected String placeholder;
	protected String className;
	protected String value;
	protected String subtype;
	protected int maxlength;
	
	@Override
	public String getType() {
		return "text";
	}
}