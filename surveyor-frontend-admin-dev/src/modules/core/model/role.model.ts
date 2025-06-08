import StatusCodeConst from "@/modules/common/const/status-code.const";

export interface Role {
    id?: string;
    name?: string;
    seqNo?: number;
    remark?: string;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
};