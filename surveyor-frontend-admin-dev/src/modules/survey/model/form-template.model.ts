import StatusCodeConst from "@/modules/common/const/status-code.const";

export interface FormTemplate {
    id?: string;
    code?: string;
    revision?: number;
    rootTemplateId?: string;
    parentTemplateId?: string;
    templateGroupId?: string;
    name?: string;
    description?: string;
    templateStatus?: TemplateStatus;
    notify?: boolean;
    remark?: string;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
};

export enum TemplateStatus {
    Pending = "Pending",
    Open = "Open",
    Closed = "Closed",
};