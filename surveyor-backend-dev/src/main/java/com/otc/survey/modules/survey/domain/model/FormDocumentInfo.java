package com.otc.survey.modules.survey.domain.model;

import java.util.List;

import com.otc.survey.modules.core.domain.entity.User;
import com.otc.survey.modules.core.domain.model.UserProfile;
import com.otc.survey.modules.survey.domain.entity.FormDocument;
import com.otc.survey.modules.survey.domain.entity.FormDocumentElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormDocumentInfo 
{
	protected FormDocument formDocument;
	protected List<FormDocumentElement> formDocumentElements;
	protected FormTemplateInfo formTemplateInfo;
	
	//protected UserProfile submittedUserProfile;
	protected User submittedUser;
}