import StatusCodeConst from "@/modules/common/const/status-code.const";
import { PrincipalType } from "../../core/constant/principal-type.const";
import { Permission } from "@/modules/core/constant/permission.const";

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