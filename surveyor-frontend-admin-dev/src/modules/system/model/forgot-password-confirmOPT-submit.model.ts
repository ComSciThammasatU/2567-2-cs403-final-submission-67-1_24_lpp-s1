import StatusCodeConst from "@/modules/common/const/status-code.const";
import { I18n } from "@/modules/common/model/i18n.model";

export interface ForgotPasswordConfirmOPTSubmit {
    id?: string,
    site?: string,
    userId?: string,
    email?: string,
    shouldConfirmOtp?: true,
    confirmOtp?: string,
    expiredPeriod?: string,
    eventSource?: string,
    oldPassword?: string,
    requestedPlatform?: string,
    resetStatus?: string,
    requestedAt?: Date,
    expiredAt?: Date,
    verifiedAt?: Date
}