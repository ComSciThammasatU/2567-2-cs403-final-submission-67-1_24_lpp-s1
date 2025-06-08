import { HttpLoadScreenBodyResponseMessage } from "@/modules/common/model/http.load-screen-body-message.model";
import { HttpResponseMessage } from "@/modules/common/model/http.response-message.model";
import httpClient from "@/modules/common/service/http/http-client.service";
import { MasterStatus } from "../model/master-status.model";
import { SearchCondition, Sort } from "@/modules/common/model/search-condition.model";
import { I18n } from "@/modules/common/model/i18n.model";
import { Page } from "@/modules/common/model/page.model";
import { Role, RoleOriginType } from "../../core/model/role.model";
import { Company } from "@/modules/core/model/company.model";
import { AssetClass } from "@/modules/fms/core/model/asset-class.model";
import { AssetCategory } from "@/modules/fms/core/model/asset-category.model";
import { AssetType } from "@/modules/fms/core/model/asset-type.mode";
import { CostCenter } from "@/modules/core/model/cost-center.model";
import { FmsLocation, FmsLocationGroup } from "@/modules/fms/core/model/fms.location.model";
import { AuthDataAccessDataSourceType } from "../const/AuthDataAccessDataSourceType";
import { AssetSubCategory } from "@/modules/fms/core/model/asset-subcategory.model";
import { CompanyBranch } from "@/modules/core/model/company-branch.model";
import { AuthDataAccessPrincipalType } from "../const/AuthDataAccessPrincipalType";
import { RoleGroup } from "../model/role-group.model";

class RoleSetupService 
{
    public async loadScreen(): Promise<LoadScreenResponseMessage> 
    {
        const apiEndpoint = '/api/system/role/setup/load-screen';
        const response = await httpClient.post<LoadScreenResponseMessage>(apiEndpoint);
        return response.data;
    }

    public async search(requestMessage: SearchRoleRequestMessage): Promise<SearchRoleResponseMessage>
    {
        const apiEndpoint = '/api/system/role/setup/search';
        const response = await httpClient.post<SearchRoleResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async update(requestMessage: UpdateRoleRequestMessage): Promise<UpdateRoleResponseMessage> 
    {
        const apiEndpoint = '/api/system/role/setup/update';
        const response = await httpClient.post<UpdateRoleResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async create(requestMessage: CreateRoleRequestMessage): Promise<CreatRoleResponseMessage> 
    {
        const apiEndpoint = '/api/system/role/setup/create';
        const response = await httpClient.post<CreatRoleResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async loadById(requestMessage: LoadRoleSetupByIdRequestMessage): Promise<LoadRoleSetupByIdResponseMessage> 
    {
        const apiEndpoint = '/api/system/role/setup/load-by-id';
        const response = await httpClient.post<LoadRoleSetupByIdResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }
}

// ****************************** loadScreen ****************************** //
export interface LoadScreenRequestMessage {

};

export interface LoadScreenResponseMessage extends HttpResponseMessage<HttpLoadScreenBodyResponseMessage<{
    masterStatuses:MasterStatus[],
    companies: Company[],
    assetClasses: AssetClass[],
    assetCategories: AssetCategory[],
    assetTypes: AssetType[],
    costCenters: CostCenter[],
    assetSubCategories: AssetSubCategory[],
    companyBranchs: CompanyBranch[],
    roleGroups: RoleGroup[],
    locationGroup1: FmsLocationGroup,
    locationGroup2: FmsLocationGroup,
    locationGroup3: FmsLocationGroup,
    locationGroup4: FmsLocationGroup,
    locationGroup5: FmsLocationGroup,
}>> {};
// ****************************** loadScreen ****************************** //

export interface UpdateRoleRequestMessage {
    updateRoleRequest: {
        site: string,
        role: Role,
        dataAccesses: DataAccesses[]
    }
}

export interface UpdateRoleResponseMessage  extends HttpResponseMessage<{
    updateRole: Role
}> {};

export interface CreateRoleRequestMessage {
    createRoleRequest: {
        site: string,
        role: Role,
        dataAccesses: DataAccesses[]
    }
}

export interface CreatRoleResponseMessage  extends HttpResponseMessage<{
    updateRole: Role
}> {};

export interface DataAccesses {
    principalType?: AuthDataAccessPrincipalType,
    dataSourceType?: AuthDataAccessDataSourceType,
    dataSourceRefId?: string,
}

export interface Sorting extends Sort {
    active: boolean;
}

export interface RoleModel {
    roleId?: string,
    roleCode?: string,
    roleName?: string,
    statusId?: string,
    statusCode?: string,
    statusName?: string,
    originalType?: string,
    roleNameI18ns?: I18n[];
}

export interface RoleInfo extends Role {
    lastModifiedAccountName?: string,
    lastModifiedDateTime?: string,

}

export interface SearchRoleRequestMessage {
    searchCondition?: SearchCondition<{
        originType: string,
        roleCode?: string,
        roleName?: string,
        statuses?: string[]
    }>;
    loadI18n?: boolean
}

export interface SearchRoleResponseMessage extends HttpResponseMessage <{
    roles: Page<RoleModel[]>;
}> {};

export interface LoadRoleSetupByIdRequestMessage{
    roleId: string
}

export interface LoadRoleSetupByIdResponseMessage  extends HttpResponseMessage <{
    role: Role,
    authDataAccesses: DataAccesses[]
}> {};

export default new RoleSetupService();