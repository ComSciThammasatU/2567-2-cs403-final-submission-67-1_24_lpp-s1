import StatusCodeConst from '@/modules/common/const/status-code.const';

export interface UserType {
    id?: string;
    name?: string;
    genus?: UserGenus;
    remark?: string;
    seqNo?: number;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
};

export enum UserGenus {
    Human = 'Human',
    System = 'System',
    Unknown = 'Unknown'
};