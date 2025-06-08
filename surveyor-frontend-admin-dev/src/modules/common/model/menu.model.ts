import { Breadcrumb } from './breadcrumb.model';
import { SideBarMenuMode } from '@/modules/common/const/sidebar-menu-mode.const';

export interface Menu {
    menuId: string;
    programId?: string;
    screenId?: string;
    itemType: ItemType;
    hierachyType: HierachyType;
    hierachyLevel: number;
    seqNo: number;
    title: string;
    titleI18ns?: { langCode: string, value: string } [];
    description: string;
    descriptionI18ns?: { langCode: string, value: string } [];
    breadcrumbs?: Breadcrumb[];
    iconType: IconType;
    iconImageURI?: string;
    iconFontClass?: string;
    action: Action;
    href?: string; // path ที่จะ routing vue component (action=Route) หรือ url ที่จะ redirect (action=Redirect)
    viewPath?: string; // path ของ vue component file
    screenTarget?: '_self' | '_blank',
    lazyLoad?: boolean;
    dropdownOpen?: boolean;
    sideMenuMode?: SideBarMenuMode;
    active: boolean;
    children?: Menu[];

    // loadScreenApiEndpoint: string; // api endpoint ที่จะใช้ load screen (grantedAuthority, label, data) ของ view นั้น (กรณีที่ระบุ)
    // onRouteLeaveErrorAction: RouteLeaveErrorAction; // จะ hanlde การ routing error จากเมนูนี้อย่างไร
};

export enum ItemType {
    Section = "Section", 
    MenuDropdown = "MenuDropdown", 
    MenuItem = "MenuItem"
};

export enum HierachyType {
    Root = "Root", 
    Middle = "Middle", 
    Leaf = "Leaf"
};

export enum IconType {
    Image = "Image", 
    AwesomeFont = "AwesomeFont"
};

export enum Action {
    Route = "Route", // routing ไปยัง view อื่นๆที่กำหนด path ใน href
    Redirect = "Redirect", // redirect ไปยัง href (ใช้ <a> แทน <router-link>)
    ToggleDropdown = "ToggleDropdown", // เปิด-ปิด dropdown menu
    None = "None"
}

export enum RouteLeaveErrorAction {
    RedirectErrorPage = "RedirectErrorPage", // ให้ redirect ไปที่หน้าจอ error (401, 403, 404, 500)
    ShowAlert = "ShowAlert", // อยู่หน้าเดิม และโชว์ Error Alert Msg
    Ignore = "Ignore" // อยู่หน้าเดิม ไม่ทำอะไร
}