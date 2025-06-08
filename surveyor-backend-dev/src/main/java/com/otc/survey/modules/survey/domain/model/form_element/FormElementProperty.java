package com.otc.survey.modules.survey.domain.model.form_element;

import com.otc.survey.modules.survey.domain.constant.FormElementTypeConst;

public abstract class FormElementProperty <T>
{
	protected String id;  // html element id
	protected String title;
	protected String description;
	protected boolean required;
	protected T defaultValue;
	protected T value;
	
	public abstract FormElementTypeConst getFormElementType();
	
	public void toJSON() {}
	
	public void fromTemplateElement() {};
	public void toTemplateElement() {};
	public void fromDocumentElement() {};
	public void toDocumentElement() {};
}