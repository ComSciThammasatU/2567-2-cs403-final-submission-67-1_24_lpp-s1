import { I18n } from './i18n.model';
import { Action } from './menu.model';

export interface FavouriteMenu {
    favouriteMenuId: string;
    userId: string;
    menuId: string;
    platform: 'mobile' | 'web';
    menuTitle: string;
    menuTitleI18ns?: I18n[];
    menuDescription?: string;
    menuDescriptionI18ns?: I18n[];
    iconFontClass?: string;
    action: Action;
    href: string;
    screenTarget?: '_self' | '_blank';
    seqNo: number;
};