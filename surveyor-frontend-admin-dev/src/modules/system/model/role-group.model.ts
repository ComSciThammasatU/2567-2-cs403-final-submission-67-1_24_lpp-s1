import StatusCodeConst from "@/modules/common/const/status-code.const";

export interface RoleGroup {
    id?: string;
    site?: string;
    code?: string;
    name?: string;
    seqNo?: string;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
};