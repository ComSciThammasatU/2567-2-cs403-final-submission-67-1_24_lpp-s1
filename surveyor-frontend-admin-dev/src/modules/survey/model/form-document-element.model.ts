import StatusCodeConst from "@/modules/common/const/status-code.const";

export interface FormDocumentElement {
    id?: string;
    documentId?: string;
    templateId?: string;
    templateElementId?: string;
    elementTypeId?: string;
    elementValue?: string;
    orderNo?: number;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
};