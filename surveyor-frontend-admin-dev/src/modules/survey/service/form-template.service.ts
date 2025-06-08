import { HttpResponseMessage } from "@/modules/common/model/http.response-message.model";
import httpClient from "@/modules/common/service/http/http-client.service";
import { FormTemplateGroup } from "../model/form-template-group.model";
import { FormTemplateInfo } from "../model/form-template-info.model";
import { TemplateStatus } from "../model/form-template.model";
import StatusCodeConst from "@/modules/common/const/status-code.const";
import { UserGroup } from "@/modules/core/model/user-group.model";
import { PrincipalType } from "@/modules/core/constant/principal-type.const";

class FormTemplateService
{
    public async loadScreen(requestMessage: FormTemplateLoadScreenRequestMessage): Promise<FormTemplateLoadScreenResponseMessage> 
    {
        console.info("### FormTemplateService.loadScreen ###");
        const apiEndpoint = '/api/survey/form-template/setup/load-screen';
        const response = await httpClient.post<FormTemplateLoadScreenResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async search(requestMessage: FormTemplateSearchRequestMessage): Promise<FormTemplateSearchResponseMessage> 
    {
        console.info("### FormTemplateService.search ###");
        const apiEndpoint = '/api/survey/form-template/setup/search';
        const response = await httpClient.post<FormTemplateSearchResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async create(requestMessage: FormTemplateCreateRequestMessage): Promise<FormTemplateCreateResponseMessage> 
    {
        console.info("### FormTemplateService.create ###");
        const apiEndpoint = '/api/survey/form-template/setup/create';
        const response = await httpClient.post<FormTemplateCreateResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async update(requestMessage: FormTemplateUpdateRequestMessage): Promise<FormTemplateUpdateResponseMessage> 
    {
        console.info("### FormTemplateService.update ###");
        const apiEndpoint = '/api/survey/form-template/setup/update';
        const response = await httpClient.post<FormTemplateUpdateResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }
}

// ****************************** load-screen ****************************** //
export interface FormTemplateLoadScreenRequestMessage {
    
};

export interface FormTemplateLoadScreenResponseMessage extends HttpResponseMessage<{
    formTemplateGroups: FormTemplateGroup[];
    userGroups: UserGroup[];
}> {};
// ****************************** load-screen ****************************** //

// ****************************** search ****************************** //
export interface FormTemplateSearchRequestMessage {
    requestPayload?: {
        criteria?: {
            templateGroupId?: string;
            templateCode?: string;
            templateName?: string;
            templateStatus?: TemplateStatus | string;
            status?: StatusCodeConst | string;
        }
    };
};

export interface FormTemplateSearchResponseMessage extends HttpResponseMessage<{
    responsePayload?: {
        formTemplateInfos: FormTemplateInfo[];
    }
}> {};
// ****************************** search ****************************** //

// ****************************** create ****************************** //
export interface FormTemplateCreateRequestMessage {
    requestPayload: {
        templateGroupId: string;
        templateCode: string;
        templateName: string;
        templateDescription: string;
        remark: string;
        templateStatus: TemplateStatus | string;
        status: StatusCodeConst | string;

        authorities: {
            principalType: PrincipalType;
            principalRefId: string;
        }[]
    }
};

export interface FormTemplateCreateResponseMessage extends HttpResponseMessage<{
    responsePayload: {
        formTemplateInfo: FormTemplateInfo;
    }
}> {};
// ****************************** create ****************************** //

// ****************************** update ****************************** //
export interface FormTemplateUpdateRequestMessage {
    requestPayload: {
        parentTemplateId: string;
        templateGroupId: string;
        templateCode: string;
        templateName: string;
        templateDescription: string;
        remark: string;
        templateStatus: TemplateStatus | string;
        status: StatusCodeConst | string;

        authorities: {
            principalType: PrincipalType;
            principalRefId: string;
        }[]
    }
};

export interface FormTemplateUpdateResponseMessage extends HttpResponseMessage<{
    responsePayload: {
        formTemplateInfo: FormTemplateInfo;
    }
}> {};
// ****************************** update ****************************** //

export default new FormTemplateService();