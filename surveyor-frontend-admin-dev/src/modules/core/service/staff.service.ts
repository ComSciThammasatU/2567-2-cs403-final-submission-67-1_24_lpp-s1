import { HttpResponseMessage } from "@/modules/common/model/http.response-message.model";
import { StaffInfo } from "../model/staff-info.model";
import httpClient from "@/modules/common/service/http/http-client.service";
import { UserGroup } from "../model/user-group.model";
import StatusCodeConst from "@/modules/common/const/status-code.const";
import { Role } from "../model/role.model";

class StaffService
{
    public async loadScreen(requestMessage: StaffLoadScreenRequestMessage): Promise<StaffLoadScreenResponseMessage> 
    {
        console.info("### StaffService.loadScreen ###");
        const apiEndpoint = '/api/core/staff/setup/load-screen';
        const response = await httpClient.post<StaffLoadScreenResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async search(requestMessage: SearchStaffRequestMessage): Promise<SearchStaffResponseMessage> 
    {
        console.info("### StaffService.search ###");
        const apiEndpoint = '/api/core/staff/setup/search';
        const response = await httpClient.post<SearchStaffResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async create(requestMessage: CreateStaffRequestMessage): Promise<CreateStaffResponseMessage> 
    {
        console.info("### StaffService.create ###");
        const apiEndpoint = '/api/core/staff/setup/create';
        const response = await httpClient.post<CreateStaffResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async update(requestMessage: UpdateStaffRequestMessage): Promise<UpdateStaffResponseMessage> 
    {
        console.info("### StaffService.update ###");
        const apiEndpoint = '/api/core/staff/setup/update';
        const response = await httpClient.post<UpdateStaffResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }
}

// ****************************** load-screen ****************************** //
export interface StaffLoadScreenRequestMessage {
    
};

export interface StaffLoadScreenResponseMessage extends HttpResponseMessage<{
    roles: Role[];
    userGroups: UserGroup[];
}> {};
// ****************************** load-screen ****************************** //

// ****************************** search ****************************** //
export interface SearchStaffRequestMessage {
    searchStaffRequest?: {
        staffName?: string;
    };
};

export interface SearchStaffResponseMessage extends HttpResponseMessage<{
    searchStaffResponse?: {
        staffInfos: StaffInfo[];
    }
}> {};
// ****************************** search ****************************** //

// ****************************** create ****************************** //
export interface CreateStaffRequestMessage {
    createStaffRequest: {
        username: string;
        email: string;
        accountName: string;
        roleIds: string[];
        userGroupIds: string[];
    };
};

export interface CreateStaffResponseMessage extends HttpResponseMessage<{
    createStaffResponse?: {
        staffInfo: StaffInfo;
    }
}> {};
// ****************************** create ****************************** //

// ****************************** update ****************************** //
export interface UpdateStaffRequestMessage {
    updateStaffRequest: {
        userId: string;
        username: string;
        email: string;
        accountName: string;
        roleIds: string[];
        userGroupIds: string[];
        status: StatusCodeConst;
    };
};

export interface UpdateStaffResponseMessage extends HttpResponseMessage<{
    updateStaffResponse?: {
        staffInfo: StaffInfo;
    }
}> {};
// ****************************** update ****************************** //

export default new StaffService();