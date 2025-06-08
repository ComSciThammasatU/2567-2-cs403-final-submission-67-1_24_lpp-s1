import StatusCodeConst from "@/modules/common/const/status-code.const";

export interface FormTemplateElement {
    // id?: string;
    // templateId?: string;
    // orderNo?: number;
    // property?: string;
    // elementData?: any;
    // status?: StatusCodeConst;
    // createdBy?: string;
    // createdAt?: Date;
    // lastModifiedBy?: string;
    // lastModifiedAt?: Date;

    id?: string;
    templateId?: string;
    elementCode?: string;
    elementTypeId?: string;
    orderNo?: number;
    title?: string;
    description?: string;
    property?: string;
    elementData?: any;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: Date;
    lastModifiedBy?: string;
    lastModifiedAt?: Date;
};