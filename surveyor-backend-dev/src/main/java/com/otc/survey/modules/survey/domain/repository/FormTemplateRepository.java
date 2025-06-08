package com.otc.survey.modules.survey.domain.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.repository.rowmapper.FormTemplateRowMapper;
import com.otc.survey.modules.survey.domain.service.form_template.dto.FormTemplateSearchDTO;

@Repository
public interface FormTemplateRepository extends SurveyJpaRepository<FormTemplate, String>
{
	public List<FormTemplate> findByCodeAndRevision(String code, int revision);
	
	public List<FormTemplate> findByTemplateGroupId(String templateGroupId);
	
	public List<FormTemplate> findByTemplateGroupIdAndStatus(String templateGroupId, StatusCode status);
	
	public default List<FormTemplate> findAllActiveByTemplateGroupId(String templateGroupId)
	{
		return findByTemplateGroupIdAndStatus(templateGroupId, StatusCode.Active);
	}
	
	@Query("FROM FormTemplate e WHERE e.id IN (:ids) AND e.templateStatus = 'Open' AND e.status = 'Active'")
	public List<FormTemplate> findAllActiveOpenTemplateByIds(@Param("ids") Collection<String> ids);
	
	
	public default List<FormTemplate> search(NamedParameterJdbcTemplate jdbcTemplate, FormTemplateSearchDTO.RequestPayload.Criteria criteria)
	{
		String sql = "SELECT * FROM sv_m_form_template t WHERE 1=1";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		if(criteria != null) {
			if(criteria.getTemplateGroupId() != null && !criteria.getTemplateGroupId().isBlank()) {
				params.addValue("templateGroupId", criteria.getTemplateGroupId().trim());
				sql += " AND t.template_group_id = :templateGroupId";
			}
			
			if(criteria.getTemplateCode() != null && !criteria.getTemplateCode().isBlank()) {
				params.addValue("templateCode", "%"+criteria.getTemplateCode().trim()+"%");
				sql += " AND t.code LIKE :templateCode";
			}
			
			if(criteria.getTemplateName() != null && !criteria.getTemplateName().isBlank()) {
				params.addValue("templateName", "%"+criteria.getTemplateName().trim()+"%");
				sql += " AND t.name LIKE :templateName";
			}
			
			if(criteria.getTemplateStatus() != null && !criteria.getTemplateStatus().isBlank()) {
				params.addValue("templateStatus", criteria.getTemplateStatus());
				sql += " AND t.template_status = :templateStatus";
			}
			
			if(criteria.getStatus() != null && !criteria.getStatus().isBlank()) {
				params.addValue("status", criteria.getStatus());
				sql += " AND t.status = :status";
			}
		}
		
		sql += " ORDER BY t.created_at DESC";
		
		return jdbcTemplate.query(sql, params, new FormTemplateRowMapper());
	}
}