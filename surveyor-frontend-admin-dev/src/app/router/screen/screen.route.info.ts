import { Breadcrumb } from '@/modules/common/model/breadcrumb.model';
import { LabelI18n } from '@/modules/common/model/label.model';
import { RouteComponent, RouteMeta } from 'vue-router';

declare type Lazy<T> = () => Promise<T>;

namespace ScreenRouteInfo {
    export type EagerRouteComponent = RouteComponent;
    export type LazyRouteComponent = Lazy<RouteComponent>;

    export enum ViewType {
        PrimeView = "PrimeView",
        WorkspaceView = "WorkspaceView"
    };

    export enum ResolveType {
        static = "static",
        dynamic = "dynamic"
    };

    export enum RegisterType {
        eager = "eager",
        lazy = "lazy"
    };

    export interface RouteInfo {
        name: string;
        path: string;
        component: EagerRouteComponent | LazyRouteComponent;
        metadata?: RouteMetaData;
    };

    export interface RouteMetaData extends RouteMeta {
        // viewFile?: string; // ใช้กรีณีที่ resolveType = "dynamic"
        screenId: string;
        viewType: ViewType;
        resolveType: ResolveType;
        registerType: RegisterType;
        pageTitle?: string;
        pageTitleI18ns?: LabelI18n[];
        pageDescription?: string;
        pageDescriptionI18ns?: LabelI18n[];
        pageBreadcrumbs?: Breadcrumb[];
    };
};

export default ScreenRouteInfo;