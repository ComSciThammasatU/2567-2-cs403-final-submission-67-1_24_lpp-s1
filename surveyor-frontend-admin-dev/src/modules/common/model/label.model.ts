export interface Label {
    key: string;
    value: string;
    i18ns?: LabelI18n [];
};

export interface LabelI18n {
    langCode: string;
    value: string;
};