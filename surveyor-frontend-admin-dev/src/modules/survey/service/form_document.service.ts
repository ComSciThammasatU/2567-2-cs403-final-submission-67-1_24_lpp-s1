import { HttpResponseMessage } from "@/modules/common/model/http.response-message.model";
import httpClient from "@/modules/common/service/http/http-client.service";
import { FormDocumentInfo } from "../model/form-document-info.model";
import { DocumentStatus } from "../model/form-document.model";
import { FormTemplateGroup } from "../model/form-template-group.model";
import { FormTemplate } from "../model/form-template.model";


class FormDocumentService
{
    public async loadScreen(requestMessage: FormDocumentLoadScreenRequestMessage): Promise<FormDocumentLoadScreenResponseMessage> 
    {
        console.info("### FormDocumentService.loadScreen ###");
        const apiEndpoint = '/api/survey/form/admin/load-screen';
        const response = await httpClient.post<FormDocumentLoadScreenResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async searchDocument(requestMessage: FormDocumentSearchRequestMessage): Promise<FormDocumentSearchResponseMessage> 
    {
        console.info("### FormDocumentService.searchDocument ###");
        const apiEndpoint = '/api/survey/form/admin/search';
        const response = await httpClient.post<FormDocumentSearchResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }
}


export interface FormDocumentLoadScreenRequestMessage {
    
};

export interface FormDocumentLoadScreenResponseMessage extends HttpResponseMessage<{
    formTemplateGroups: FormTemplateGroup[];
    formTemplates: FormTemplate[];
}> {};


export interface FormDocumentSearchRequestMessage {
    templateId: string;
    documentStatus: DocumentStatus;
};

export interface FormDocumentSearchResponseMessage extends HttpResponseMessage<{
    formDocumentInfos: FormDocumentInfo[];
}> {};


export default new FormDocumentService();