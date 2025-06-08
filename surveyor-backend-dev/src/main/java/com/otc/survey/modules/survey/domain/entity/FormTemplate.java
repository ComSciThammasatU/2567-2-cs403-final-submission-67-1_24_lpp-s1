package com.otc.survey.modules.survey.domain.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.otc.survey.modules.common.domain.constant.ApplicationConfigConst;
import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.entity.BaseEntity;
import com.otc.survey.modules.common.domain.util.JsonUtil;
import com.otc.survey.modules.survey.domain.constant.FormTemplateStatus;
import com.otc.survey.modules.survey.domain.model.form_element.FormElementAttribute;
import com.otc.survey.modules.survey.domain.model.form_element.TextFormElementAttribute;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sv_m_form_template")
@Data
public class FormTemplate extends BaseEntity 
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "code")
	protected String code;
	
	@Column(name = "revision")
	protected int revision;
	
	@Column(name = "template_group_id")
	protected String templateGroupId;
	
	@Column(name = "hierachy_level")
	protected int hierachyLevel;
	
	@Convert(converter = LineageTemplateConverter.class)
	@Column(name = "lineage_templates", columnDefinition = "JSON")
	protected List<LineageTemplate> lineageTemplates;
	
	@Column(name = "root_template_id")
	protected String rootTemplateId;
	
	@Column(name = "parent_template_id")
	protected String parentTemplateId;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "remark")
	protected String remark;
	
	@Convert(converter = FormElementConverter.class)
	@Column(name = "elements", columnDefinition = "JSON")
	protected List<FormElementAttribute> elements;
	
	@Column(name = "is_notify")
	protected boolean notify;
	
	@Column(name = "template_status")
	@Enumerated(EnumType.STRING)
	protected FormTemplateStatus templateStatus;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected StatusCode status;
	
	@Column(name = "created_by")
	protected String createdBy;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = ApplicationConfigConst.DEFAULT_DATETIME_FORMAT, timezone = ApplicationConfigConst.DEFAULT_TIMEZONE)
	protected Date createdAt;
	
	@Column(name = "last_modified_by")
	protected String lastModifiedBy;
	
	@Column(name = "last_modified_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = ApplicationConfigConst.DEFAULT_DATETIME_FORMAT, timezone = ApplicationConfigConst.DEFAULT_TIMEZONE)
	protected Date lastModifiedAt;
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class LineageTemplate {
		protected String templateId;
		protected int hierachyLevel;
	}
	
	
	@Converter
	public static class LineageTemplateConverter implements AttributeConverter<List<LineageTemplate>, String> {

		@Override
		public String convertToDatabaseColumn(List<LineageTemplate> attributes) {
			if(attributes == null) {
				return null;
			}
			try {
				return JsonUtil.stringify(attributes);
			} catch (JsonProcessingException ex) {
				throw new RuntimeException(ex);
			}
		}

		@Override
		public List<LineageTemplate> convertToEntityAttribute(String jsonData) {
			if(jsonData == null || jsonData.isBlank()) {
				return null;
			}
			try {
				return new ArrayList<>(List.of(JsonUtil.parse(jsonData, LineageTemplate[].class)));
			} catch (JsonProcessingException ex) {
				throw new RuntimeException(ex);
			}
		}
		
	}
	
	
	@Converter
	public static class FormElementConverter implements AttributeConverter<List<FormElementAttribute>, String> {

		@Override
		public String convertToDatabaseColumn(List<FormElementAttribute> attributes) {
			if(attributes == null) {
				return null;
			}
			try {
				return JsonUtil.stringify(attributes);
			} catch (JsonProcessingException ex) {
				throw new RuntimeException(ex);
			}
		}

		@Override
		public List<FormElementAttribute> convertToEntityAttribute(String jsonData) {
			if(jsonData == null || jsonData.isBlank()) {
				return null;
			}
			
			try {
				List<Map<String, Object>> elementAttributes = JsonUtil.parse(jsonData, new TypeReference<List<Map<String, Object>>>() {});
				
				if(elementAttributes == null || elementAttributes.isEmpty()) {
					return null;
				}
				
				List<FormElementAttribute> elements = new ArrayList<>();
				
				for (Map<String, Object> attributes : elementAttributes) {
		            String type = (String) attributes.get("type");
		            try {
		                Class<?> clazz = switch (type) {
							case "text" -> TextFormElementAttribute.class;
							default -> null;
		                };
		                
		                FormElementAttribute formElementAttribute = (FormElementAttribute) clazz.getDeclaredConstructor().newInstance();

		                for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
		                    Field field = getField(clazz, attribute.getKey());
		                    if(field != null) {
		                    	field.setAccessible(true);
		                        field.set(formElementAttribute, attribute.getValue());
		                    }
		                }
		                
		                elements.add(formElementAttribute);
		            } catch (Exception ex) {
		            	throw new RuntimeException(ex);
		            }
		        }
				
				return elements;
			} catch (JsonProcessingException ex) {
				throw new RuntimeException(ex);
			}
		}
		
		protected Field getField(Class<?> clazz, String fieldName) {
			Field field = null;
            
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ex) {
                //ex.printStackTrace();
            }
            
            if(field == null) {
            	try {
                    field = clazz.getSuperclass().getDeclaredField(fieldName);
                } catch (NoSuchFieldException ex) {
                    //ex.printStackTrace();
                }
            }
            
            return field;
		}
		
	}
}