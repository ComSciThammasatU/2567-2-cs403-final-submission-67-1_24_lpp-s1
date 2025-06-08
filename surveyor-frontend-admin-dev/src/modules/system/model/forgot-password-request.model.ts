import StatusCodeConst from "@/modules/common/const/status-code.const";
import { I18n } from "@/modules/common/model/i18n.model";

export interface ForgotPasswordRequest {
    companyCode?: string;
    email?: string;
}