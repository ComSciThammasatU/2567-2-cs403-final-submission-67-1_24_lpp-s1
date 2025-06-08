import httpClient from "@/modules/common/service/http/http-client.service";
import { HttpResponseMessage } from '@/modules/common/model/http.response-message.model';
import { Company } from '@/modules/core/model/company.model';
import { UserType } from '@/modules/core/model/user-type.model';
import { Role } from '@/modules/core/model/role.model';
import { HttpLoadScreenBodyResponseMessage } from '@/modules/common/model/http.load-screen-body-message.model';
import StatusCodeConst from "@/modules/common/const/status-code.const";
import { SearchCondition } from "@/modules/common/model/search-condition.model";


class UserManageService
{
    public async loadScreen(): Promise<LoadScreenResponseMessage>
    {
        const apiEndpoint = '/api/system/user/management/load-screen';
        const response = await httpClient.post(apiEndpoint, {});
        return response.data;
    }

    public async search(searchCondition: UserManageSearchCondition = {}): Promise<SearchResponseMessage>
    {
        const apiEndpoint = '/api/system/user/management/search';
        const response = await httpClient.post(apiEndpoint, { searchCondition });
        return response.data;
    }

    public async create(requestMessage: CreateRequestMessage): Promise<CreateResponseMessage>
    {
        console.info("### UserManageService.create ###");
        console.info("requestMessage => ", requestMessage);

        const apiEndpoint = '/api/system/user/management/create';
        const response = await httpClient.post(apiEndpoint, requestMessage);
        return response.data;
    }

    public async update(requestMessage: UpdateRequestMessage): Promise<UpdateResponseMessage>
    {
        const apiEndpoint = '/api/system/user/management/update';
        const response = await httpClient.post(apiEndpoint, requestMessage);
        return response.data;
    }

    public async remove(requestMessage: RemoveRequestMessage): Promise<RemoveResponseMessage>
    {
        const apiEndpoint = '/api/system/user/management/remove';
        const response = await httpClient.post(apiEndpoint, requestMessage);
        return response.data;
    }
}


export interface RoleInfo {
    id?: string;
    activatedDate?: string;
    expiredDate?: string;
    status?: StatusCodeConst;
};


export interface UserInfoModel {
    userAccount: {
        id?: string;
        site?: string;
        companyRefId?: string;
        code?: string;
        username?: string;
        password?: string;
        email?: string;
        accountName?: string;
        userTypeId?: string;
        activatedDate?: string;
        expiredDate?: string;
        profileImageId?: string;
        profileImageBase64?: string;
        profileImageURL?: string;
        status?: StatusCodeConst;
    },

    userPersonalInfo: {
        id?: string;
        firstName?: string;
        lastName?: string;
        birthDate?: string;
    },

    roles: RoleInfo[]
};


export interface LoadScreenResponseMessage extends HttpResponseMessage<HttpLoadScreenBodyResponseMessage<{
    companies: Company[];
    userTypes: UserType[];
    roles: Role[];
    defaultActivatedDate: string;
    defaultExpiredDate: string;
}>> {};


export interface UserManageSearchCondition extends SearchCondition<{
    site?: string;
    companyRefId?: string;
    userCode?: string;
    username?: string;
    userFullName?: string;
}> {};


export interface SearchResponseMessage extends HttpResponseMessage<{
    userInfos: UserInfoModel[];
}> {};


export interface CreateRequestMessage {
    userInfo: UserInfoModel;
    notifyInitialUserAccount: boolean;
};


export interface CreateResponseMessage extends HttpResponseMessage<{
    createdUserInfo: UserInfoModel;
}> {};


export interface UpdateRequestMessage {
    userInfo: UserInfoModel;
};


export interface UpdateResponseMessage extends HttpResponseMessage<{
    updatedUserInfo: UserInfoModel;
}> {};


export interface RemoveRequestMessage {
    userId: string;
};


export interface RemoveResponseMessage extends HttpResponseMessage<{
    removedUserInfo: UserInfoModel;
}> {};


export default new UserManageService();