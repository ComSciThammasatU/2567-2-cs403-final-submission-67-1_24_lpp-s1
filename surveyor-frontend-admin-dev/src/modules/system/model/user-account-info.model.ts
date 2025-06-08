import StatusCodeConst from "@/modules/common/const/status-code.const";

export interface userAccount {
    id?: string;
    site?: string;
    companyRefId?: string;
    code?: string;
    username?: string;
    password?: string;
    email?: string;
    admin?: boolean;
    reSendEmail?: boolean;
    accountName?: string;
    userTypeId?: string;
    activatedDate?: string;
    expiredDate?: string;
    profileImageId?: string;
    profileImageBase64?: string;
    profileImageURL?: string;
    removeProfileImage?: boolean;
    status?: StatusCodeConst;
    statusId?: string,
    statusCode?: string,
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
}

export interface personalInfo{
    id?: string;
    nameTitleId?: string;
    firstName?: string;
    lastName?: string;
}