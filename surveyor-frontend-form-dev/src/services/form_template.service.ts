import type { FormTemplateInfo } from "../models/form-template-info.model";
import { type HttpResponseMessage } from "../models/http.response-message.model";
import httpClient from "./http/http-client.service";

class FormTemplateService
{
    public async loadToDoList(requestMessage: FormTemplateLoadToDoListRequestMessage): Promise<FormTemplateLoadToDoListResponseMessage> 
    {
        console.info("### FormTemplateService.loadToDoList ###");
        const apiEndpoint = '/api/survey/form-template/to-do-list';
        const response = await httpClient.post<FormTemplateLoadToDoListResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }
}

export interface FormTemplateLoadToDoListRequestMessage {
    
};

export interface FormTemplateLoadToDoListResponseMessage extends HttpResponseMessage<{
    formTemplateInfos: FormTemplateInfo[];
}> {};

export default new FormTemplateService();