import StatusCodeConst from "@/modules/common/const/status-code.const";

export interface RoleInfo{
    roleId?: string;
    activatedDate?: string;
    expiredDate?: string;
    status?: StatusCodeConst;
}