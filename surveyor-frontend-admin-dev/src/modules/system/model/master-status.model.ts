import { I18n } from "@/modules/common/model/i18n.model";

export interface MasterStatus {
    id?: string;
    code?: string;
    name?: string;
    remark?: string;
    seqNo?: number;
    isActive?: boolean;
    createdBy?: string;
    createdAt?: string;
    lastModifiedBy?: string;
    lastModifiedAt?: string;
};