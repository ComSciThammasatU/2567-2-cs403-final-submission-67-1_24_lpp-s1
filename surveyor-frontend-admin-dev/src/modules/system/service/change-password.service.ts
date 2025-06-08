import { AxiosResponse } from 'axios';
import httpClient from "@/modules/common/service/http/http-client.service";
import { HttpResponseMessage } from '@/modules/common/model/http.response-message.model';
import { HttpLoadScreenBodyResponseMessage } from "@/modules/common/model/http.load-screen-body-message.model";
import { LabelI18n } from '@/modules/common/model/label.model';

class ChangePasswordService
{
    public async loadScreen(): Promise<AxiosResponse<LoadScreenResponseMessage>>
    {
        console.info("### ChangePasswordService.loadScreen ###");
        const apiEndpoint = '/api/system/auth/change-password/load-screen';
        return httpClient.post<LoadScreenResponseMessage>(apiEndpoint);
    }


    public async submit(requestMessage: SubmitRequestMessage): Promise<AxiosResponse<SubmitResponseMessage>>
    {
        console.info("### ChangePasswordService.submit ###");
        const apiEndpoint = '/api/system/auth/change-password/submit';
        return httpClient.post<SubmitResponseMessage>(apiEndpoint, requestMessage);
    }
}


interface LoadScreenResponseMessage extends HttpResponseMessage<HttpLoadScreenBodyResponseMessage<{
    passwordPolicyContent?: string;
    passwordPolicyContentI18ns?: LabelI18n[];
}>> {};


interface SubmitRequestMessage {
    oldPassword: string;
    newPassword: string;
};


interface SubmitResponseMessage extends HttpResponseMessage<{

}> {};


export default new ChangePasswordService();