package com.otc.survey.modules.survey.domain.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.survey.domain.constant.FormTemplateStatus;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;

public class FormTemplateRowMapper implements RowMapper<FormTemplate>
{

	@Override
	public FormTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		FormTemplate formTemplate = new FormTemplate();
		formTemplate.setId(rs.getString("id"));
		formTemplate.setCode(rs.getString("code"));
		formTemplate.setRevision(rs.getInt("revision"));
		formTemplate.setTemplateGroupId(rs.getString("template_group_id"));
		formTemplate.setHierachyLevel(rs.getInt("hierachy_level"));
		formTemplate.setLineageTemplates(new FormTemplate.LineageTemplateConverter().convertToEntityAttribute(rs.getString("lineage_templates")));
		formTemplate.setRootTemplateId(rs.getString("root_template_id"));
		formTemplate.setParentTemplateId(rs.getString("parent_template_id"));
		formTemplate.setName(rs.getString("name"));
		formTemplate.setDescription(rs.getString("description"));
		formTemplate.setRemark(rs.getString("remark"));
		formTemplate.setNotify(rs.getBoolean("is_notify"));
		formTemplate.setElements(new FormTemplate.FormElementConverter().convertToEntityAttribute(rs.getString("elements")));
		formTemplate.setTemplateStatus(FormTemplateStatus.valueOf(rs.getString("template_status")));
		formTemplate.setStatus(StatusCode.valueOf(rs.getString("status")));
		formTemplate.setCreatedBy(rs.getString("created_by"));
		formTemplate.setCreatedAt(rs.getTimestamp("created_at"));
		formTemplate.setLastModifiedBy(rs.getString("last_modified_by"));
		formTemplate.setLastModifiedAt(rs.getTimestamp("last_modified_at"));
		
		return formTemplate;
	}

}