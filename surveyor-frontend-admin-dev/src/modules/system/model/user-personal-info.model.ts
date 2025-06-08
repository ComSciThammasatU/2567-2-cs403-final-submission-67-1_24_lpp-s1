import StatusCodeConst from '@/modules/common/const/status-code.const';

export interface UserPersonalInfo {
    id?: string;
    userId?: string;
    genderId?: string;
    nameTitleId?: string;
    firstName?: string;
    lastName?: string;
    nickName?: string;
    birthDate?: Date;
    nationId?: string;
    raceId?: string;
    religionId?: string;
    mobileNo?: string;
    registAddressId?: string;
    contactAddressId?: string;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
};