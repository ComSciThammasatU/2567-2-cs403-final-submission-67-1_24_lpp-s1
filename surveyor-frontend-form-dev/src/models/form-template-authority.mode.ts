import type { Permission } from "../const/permission.const";
import type { PrincipalType } from "../const/principal-type.const";
import type StatusCodeConst from "../const/status-code.const";

export interface FormTemplateAuthority {
    id?: string;
    templateId?: string;
    principalType?: PrincipalType;
    principalRefId?: string;
    permission?: Permission;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
};