import StatusCodeConst from '@/modules/common/const/status-code.const';

export interface UserGroup {
    id?: string;
    name?: string;
    remark?: string;
    seqNo?: number;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
};