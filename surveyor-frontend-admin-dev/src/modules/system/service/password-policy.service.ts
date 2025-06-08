import { HttpLoadScreenBodyResponseMessage } from "@/modules/common/model/http.load-screen-body-message.model";
import { HttpResponseMessage } from "@/modules/common/model/http.response-message.model";
import httpClient from "@/modules/common/service/http/http-client.service";
import { PasswordPolicy } from "../model/password-policy.model";
import { MasterStatus } from "../model/master-status.model";
import { Company } from "@/modules/core/model/company.model";

class PasswordPolicyService
{
    public async loadScreen(): Promise<LoadScreenResponseMessage> 
    {
        const apiEndpoint = '/api/system/password-policy/load-screen';
        const response = await httpClient.post<LoadScreenResponseMessage>(apiEndpoint);
        return response.data;
    }

    public async loadLoadPasswordPolicy(requestMessage: LoadPasswordPolicyRequestMessage): Promise<LoadPasswordPolicyResponseMessage> 
    {
        const apiEndpoint = '/api/system/password-policy/load-policy';
        const response = await httpClient.post<LoadPasswordPolicyResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async update(requestMessage: UpdatePasswordPolicyRequestMessage): Promise<UpdatePasswordPolicyResponseMessage> 
    {
        const apiEndpoint = '/api/system/password-policy/update-policy';
        const response = await httpClient.post<UpdatePasswordPolicyResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async create(requestMessage: CreatePasswordPolicyRequestMessage): Promise<CreatPasswordPolicyResponseMessage> 
    {
        const apiEndpoint = '/api/system/password-policy/create-policy';
        const response = await httpClient.post<CreatPasswordPolicyResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }
}

// ****************************** loadScreen ****************************** //
export interface LoadScreenRequestMessage {

};

export interface LoadScreenResponseMessage extends HttpResponseMessage<HttpLoadScreenBodyResponseMessage<{
    masterStatuses:MasterStatus[],
    companies: Company[]
}>> {};
// ****************************** loadScreen ****************************** //

export interface LoadPasswordPolicyRequestMessage {
    site?: string,
    companyRefId?: string,
    filterActiveStatus?: Boolean
}

export interface LoadPasswordPolicyResponseMessage  extends HttpResponseMessage<{
    passwordPolicy: PasswordPolicy
}> {};

export interface UpdatePasswordPolicyRequestMessage {
    updatePasswordPolicyRequest: {
        passwordPolicy: PasswordPolicy
    }
}

export interface UpdatePasswordPolicyResponseMessage  extends HttpResponseMessage<{
    passwordPolicy: PasswordPolicy
}> {};

export interface CreatePasswordPolicyRequestMessage {
    createPasswordPolicyRequest: {
        passwordPolicy: PasswordPolicy
    }
}

export interface CreatPasswordPolicyResponseMessage  extends HttpResponseMessage<{
    passwordPolicy: PasswordPolicy
}> {};

export default new PasswordPolicyService();