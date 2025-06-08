import StatusCodeConst from '@/modules/common/const/status-code.const';

export interface Student {
    id?: string;
    userId?: string;
    studentCode?: string;
    degreeId?: string;
    classLevel?: number;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
};