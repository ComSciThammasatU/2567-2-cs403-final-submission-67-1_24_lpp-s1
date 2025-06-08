import type { FormDocumentInfo } from "../models/form-document-info.model";
import type { HttpResponseMessage } from "../models/http.response-message.model";
import httpClient from "./http/http-client.service";


class FormDocumentService
{
    public async loadUserHistoryDocuments(requestMessage: FormDocumentLoadUserHistoryRequestMessage): Promise<FormDocumentLoadUserHistoryResponseMessage> 
    {
        console.info("### FormDocumentService.loadUserHistoryDocuments ###");
        const apiEndpoint = '/api/survey/form-document/user/history';
        const response = await httpClient.post<FormDocumentLoadUserHistoryResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async submitForm(requestMessage: FormDocumentSubmitRequestMessage): Promise<FormDocumentSubmitResponseMessage> 
    {
        console.info("### FormDocumentService.submitForm ###");
        const apiEndpoint = '/api/survey/form-document/user/submit';
        const response = await httpClient.post<FormDocumentSubmitResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }
}


export interface FormDocumentLoadUserHistoryRequestMessage {
    
};

export interface FormDocumentLoadUserHistoryResponseMessage extends HttpResponseMessage<{
    formDocumentInfos: FormDocumentInfo[];
}> {};


export interface FormDocumentSubmitRequestMessage {
    requestPayload: {
        templateId: string;

        elements: {
            templateElementId: string;
            elementTypeId: string;
            elementValue: string;
            orderNo: number;
        }[];
    }
};

export interface FormDocumentSubmitResponseMessage extends HttpResponseMessage<{
    responsePayload: {
        formDocumentInfo: FormDocumentInfo;
    };
}> {};


export default new FormDocumentService();