import type StatusCodeConst from "../const/status-code.const";

export interface FormTemplateGroup {
    id?: string;
    name?: string;
    remark?: string;
    status?: StatusCodeConst;
};