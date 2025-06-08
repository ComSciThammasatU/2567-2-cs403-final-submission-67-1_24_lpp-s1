import StatusCodeConst from "@/modules/common/const/status-code.const";
import { I18n } from "@/modules/common/model/i18n.model";

export interface PasswordPolicy {
    id?: string;
    site?: string;
    companyRefId?: string;
    minLength?: number;
    maxLength?: number;
    minNumberLength?: number;
    minLowercaseLetterLength?: number;
    minCapitalLetterLength?: number;
    minSpecialCharacterLength?: number;
    specialCharacter?: string;
    expiredEnabled?: boolean;
    expiredPeriod?: number;
    checkDuplicateEnabled?: boolean;
    checkDuplicateTimes?: number;
    lockedEnabled?: boolean;
    lockedTimesLimit?: number;
    content?: string;
    supportContact?: string;
    remark?: string;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;

    contentI18ns:I18n[];
    supportContactI18ns:I18n[];
}