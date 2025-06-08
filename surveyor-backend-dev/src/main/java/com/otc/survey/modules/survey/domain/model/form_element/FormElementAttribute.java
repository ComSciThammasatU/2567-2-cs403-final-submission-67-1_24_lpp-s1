package com.otc.survey.modules.survey.domain.model.form_element;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@Data
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type" // ชื่อ field ที่จะระบุประเภทของ child class ใน JSON
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TextFormElementAttribute.class, name = "text")
})
public abstract class FormElementAttribute
{
	protected String name;
	protected boolean required;
	protected String label;
	protected String description;
	
	public abstract String getType();
}