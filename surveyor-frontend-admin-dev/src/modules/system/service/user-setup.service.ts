import { RoleInfo } from './../model/role-info';
import httpClient from "@/modules/common/service/http/http-client.service";
import { HttpResponseMessage } from '@/modules/common/model/http.response-message.model';
import { Company } from '@/modules/core/model/company.model';
import { Role } from '@/modules/core/model/role.model';
import { HttpLoadScreenBodyResponseMessage } from '@/modules/common/model/http.load-screen-body-message.model';
import StatusCodeConst from "@/modules/common/const/status-code.const";
import { SearchCondition } from "@/modules/common/model/search-condition.model";
import { MasterStatus } from "../model/master-status.model";
import { Page } from "@/modules/common/model/page.model";
import { NameTitle } from "@/modules/fms/core/model/name-title.model";
import { personalInfo, userAccount } from "../model/user-account-info.model";


class UserSetupService
{
    public async loadScreen(): Promise<LoadScreenResponseMessage>
    {
        const apiEndpoint = '/api/system/user/setup/load-screen';
        const response = await httpClient.post(apiEndpoint, {});
        return response.data;
    }

    public async search(searchCondition: UserSetupSearchCondition): Promise<SearchResponseMessage>
    {
        const apiEndpoint = '/api/system/user/setup/search';
        const response = await httpClient.post<SearchResponseMessage>(apiEndpoint, searchCondition);;
        return response.data;
    }

    public async create(requestMessage: CreateUserRequest): Promise<CreateResponseMessage>
    {
        console.info("### UserManageService.create ###");
        console.info("requestMessage => ", requestMessage);

        const apiEndpoint = '/api/system/user/setup/create';
        const response = await httpClient.post(apiEndpoint, requestMessage);
        return response.data;
    }

    public async update(requestMessage: UpdateRequestMessage): Promise<UpdateResponseMessage>
    {
        const apiEndpoint = '/api/system/user/setup/update';
        const response = await httpClient.post(apiEndpoint, requestMessage);
        return response.data;
    }

    public async loadAccountInfo(requestMessage: LoadAccountInfoRequestMessage): Promise<LoadAccountInfoResponseMessage>
    {
        const apiEndpoint = '/api/system/user/setup/load-account-info';
        const response = await httpClient.post(apiEndpoint, requestMessage);
        return response.data;
    }

    public async notifyRegistration(requestMessage: NotifyRegisterRequestMessage): Promise<NotifyRegisterResponseMessage>
    {
        const apiEndpoint = '/api/system/user/setup/notify-registration';
        const response = await httpClient.post(apiEndpoint, requestMessage);
        return response.data;
    }
}

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
        activatedDate?: Date;
        expiredDate?: Date;
        profileImageId?: string;
        profileImageBase64?: string;
        profileImageURL?: string;
        removeProfileImage?: boolean;
        status?: StatusCodeConst;
        statusId?: string;
        statusCode?: string;
    },

    personalInfo: {
        id?: string;
        nameTitleId?: string;
        firstName?: string;
        lastName?: string;
    },

    roleInfos?: RoleInfo[],
    notifyUserRegistration?: boolean;
};


export interface LoadScreenResponseMessage extends HttpResponseMessage<HttpLoadScreenBodyResponseMessage<{
    companies: Company[];
    roles: Role[];
    masterStatuses: MasterStatus[];
    nameTitles: NameTitle[];
    defaultActivatedDate: Date;
    defaultExpiredDate: Date;
}>> {};


export interface UserSetupSearchCondition{
    searchCondition?: SearchCondition<{
        site?: string;
        companyRefIds?: string[];
        username?: string;
        email?: string;
        statuses?: string[];
    }>;
    loadI18n?: boolean
};


export interface SearchResponseMessage extends HttpResponseMessage<{
    users: Page<UserInfoModel[]>;
}> {};


export interface CreateUserRequest{
    createUserRequest: {
        userAccount: userAccount;
        personalInfo: personalInfo;
        roleInfos: RoleInfo[];
    }
};


export interface CreateResponseMessage extends HttpResponseMessage<{
    createdUserInfo: UserInfoModel;
}> {};


export interface UpdateRequestMessage {
    updateUserRequest: {
        userAccount: userAccount;
        personalInfo: personalInfo;
        roleInfos: RoleInfo[];
    }
};


export interface UpdateResponseMessage extends HttpResponseMessage<{
    updatedUserInfo: UserInfoModel;
}> {};


export interface LoadAccountInfoRequestMessage {
    userId: string;
};


export interface LoadAccountInfoResponseMessage extends HttpResponseMessage<{
    userAccountInfo: {
        userAccount: userAccount,
        personalInfo: personalInfo,
        roleInfos: RoleInfo[],
    }
}> {};

export interface NotifyRegisterRequestMessage {
    userId: string;
};

export interface NotifyRegisterResponseMessage extends HttpResponseMessage<{
    
}> {};

export default new UserSetupService();