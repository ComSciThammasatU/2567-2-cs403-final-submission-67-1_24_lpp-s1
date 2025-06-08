import StatusCodeConst from '@/modules/common/const/status-code.const';

export interface Language {
    id?: string;
    code?: string;
    name?: string;
    aliasCode?: string;
    locale?: string;
    logoImageId?: string;
    iconFontClass?: string;
    seqNo?: number;
    status?: StatusCodeConst;
    createdBy?: string;
    createdAt?: string;
    lastModifiedBy?: string;
    lastModifiedAt?: string;
};