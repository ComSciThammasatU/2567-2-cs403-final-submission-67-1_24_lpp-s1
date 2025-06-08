import StatusCodeConst from "@/modules/common/const/status-code.const";
import { I18n } from "@/modules/common/model/i18n.model";

export interface AuthPrivilege {
    id?: string;
    authPolicyId: string;
    authPolicyName?: string;
    programId: string;
    programAction?: string[];
    principalType?: string;
    principalRefId?: string;
    principalName?: string;
    permission?: string;
    status?: string;
    statusId?: string;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
    authPrivilegeStamp?: string;

    nameI18ns?: I18n[];
};