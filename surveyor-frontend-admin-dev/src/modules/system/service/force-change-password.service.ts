import httpClient from "@/modules/common/service/http/http-client.service";
import { HttpResponseMessage } from '@/modules/common/model/http.response-message.model';
import { HttpLoadScreenBodyResponseMessage } from "@/modules/common/model/http.load-screen-body-message.model";

class ForceChangePasswordService
{
    public async loadScreen(): Promise<LoadScreenResponseMessage>
    {
        console.info("### ForceChangePasswordService.loadScreen ###");
        const apiEndpoint = '/api/system/auth/force-change-password/load-screen';
        const response = await httpClient.post<LoadScreenResponseMessage>(apiEndpoint);
        return response.data;
    }


    public async submit(requestMessage: SubmitRequestMessage): Promise<SubmitResponseMessage>
    {
        console.info("### ForceChangePasswordService.submit ###");
        const apiEndpoint = '/api/system/auth/force-change-password/submit';
        const response = await httpClient.post<SubmitResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }
}


interface LoadScreenResponseMessage extends HttpResponseMessage<HttpLoadScreenBodyResponseMessage<{
    passwordPolicyContent?: string;
}>> {};


interface SubmitRequestMessage {
    newPassword: string;
};


interface SubmitResponseMessage extends HttpResponseMessage<{

}> {};


export default new ForceChangePasswordService();