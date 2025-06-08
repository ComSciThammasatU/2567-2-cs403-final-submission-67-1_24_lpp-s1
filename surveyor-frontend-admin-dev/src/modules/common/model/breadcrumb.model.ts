import { LabelI18n } from './label.model';

export interface Breadcrumb {
    type: BreadcrumbType;
    text: string;
    textI18ns?: LabelI18n[];
    href?: string;
};

export enum BreadcrumbType {
    Text = "Text",
    Link = "Link"
};