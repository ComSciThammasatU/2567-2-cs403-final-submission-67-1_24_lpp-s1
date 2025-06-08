import StatusCodeConst from "@/modules/common/const/status-code.const";

export interface FormDocument {
    id?: string;
    templateId?: string;
    documentNo?: string;
    documentStatus?: DocumentStatus;
    submittedUserId?: string;
    submittedAt?: Date;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
};

export enum DocumentStatus {
    Pending = "Pending",
    Processing = "Processing",
    Completed = "Completed",
};