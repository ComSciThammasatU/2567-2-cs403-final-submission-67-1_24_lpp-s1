import { HttpResponseMessage } from "@/modules/common/model/http.response-message.model";
import { ProfessorInfo } from "../model/professor-info.model";
import httpClient from "@/modules/common/service/http/http-client.service";
import { UserGroup } from "../model/user-group.model";
import StatusCodeConst from "@/modules/common/const/status-code.const";
import { Role } from "../model/role.model";

class ProfessorService
{
    public async loadScreen(requestMessage: ProfessorLoadScreenRequestMessage): Promise<ProfessorLoadScreenResponseMessage> 
    {
        console.info("### ProfessorService.loadScreen ###");
        const apiEndpoint = '/api/core/professor/setup/load-screen'; 
        const response = await httpClient.post<ProfessorLoadScreenResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async search(requestMessage: SearchProfessorRequestMessage): Promise<SearchProfessorResponseMessage> 
    {
        console.info("### ProfessorService.search ###");
        const apiEndpoint = '/api/core/professor/setup/search';
        const response = await httpClient.post<SearchProfessorResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async create(requestMessage: CreateProfessorRequestMessage): Promise<CreateProfessorResponseMessage> 
    {
        console.info("### ProfessorService.create ###");
        const apiEndpoint = '/api/core/professor/setup/create';
        const response = await httpClient.post<CreateProfessorResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async update(requestMessage: UpdateProfessorRequestMessage): Promise<UpdateProfessorResponseMessage> 
    {
        console.info("### ProfessorService.update ###");
        const apiEndpoint = '/api/core/professor/setup/update';
        const response = await httpClient.post<UpdateProfessorResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }
}

// ****************************** load-screen ****************************** //
export interface ProfessorLoadScreenRequestMessage {
    
};

export interface ProfessorLoadScreenResponseMessage extends HttpResponseMessage<{
    roles: Role[];
    userGroups: UserGroup[];
}> {};
// ****************************** load-screen ****************************** //

// ****************************** search ****************************** //
export interface SearchProfessorRequestMessage {
    searchProfessorRequest?: {
        professorName?: string;
    };
};

export interface SearchProfessorResponseMessage extends HttpResponseMessage<{
    searchProfessorResponse?: {
        professorInfos: ProfessorInfo[];
    }
}> {};
// ****************************** search ****************************** //

// ****************************** create ****************************** //
export interface CreateProfessorRequestMessage {
    createProfessorRequest: {
        username: string;
        email: string;
        accountName: string;
        roleIds: string[];
        userGroupIds: string[];
    };
};

export interface CreateProfessorResponseMessage extends HttpResponseMessage<{
    createProfessorResponse?: {
        professorInfo: ProfessorInfo;
    }
}> {};
// ****************************** create ****************************** //

// ****************************** update ****************************** //
export interface UpdateProfessorRequestMessage {
    updateProfessorRequest: {
        userId: string;
        username: string;
        email: string;
        accountName: string;
        roleIds: string[];
        userGroupIds: string[];
        status: StatusCodeConst;
    };
};

export interface UpdateProfessorResponseMessage extends HttpResponseMessage<{
    updateProfessorResponse?: {
        professorInfo: ProfessorInfo;
    }
}> {};
// ****************************** update ****************************** //

export default new ProfessorService();