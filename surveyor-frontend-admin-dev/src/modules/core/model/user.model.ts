import StatusCodeConst from '@/modules/common/const/status-code.const';

export interface User {
    id?: string;
    username?: string;
    password?: string;
    email?: string;
    accountName?: string;
    userTypeId?: string;
    accountLocked?: boolean;
    passwordLocked?: string;
    forceChangePassword?: string;
    loginFailTimes?: string;
    accountActivatedAt?: Date;
    accountExpiredAt?: Date;
    passwordExpiredAt?: Date;
    profileImageId?: string;
    profileImageURL?: string;
    profileImageBase64?: string;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
};