import { Breadcrumb } from "@/modules/common/model/breadcrumb.model";
import { LabelI18n } from "@/modules/common/model/label.model";
import { RouteRecordRaw } from "vue-router";
import ScreenRouteInfo from "./screen.route.info";

export abstract class ScreenRouteBuilder {
    public abstract getRouteName(): string;
    public abstract getRoutePath(): string;
    public abstract getComponent(): ScreenRouteInfo.EagerRouteComponent | ScreenRouteInfo.LazyRouteComponent;
    public abstract getScreenId(): string;
    public abstract getViewType(): ScreenRouteInfo.ViewType;
    public abstract getResolveType(): ScreenRouteInfo.ResolveType;
    public abstract getRegisterType(): ScreenRouteInfo.RegisterType;

    public buildScreenRouteInfo(): ScreenRouteInfo.RouteInfo
    {
        return {
            name: this.getRouteName(),
            path: this.getRoutePath(),
            component: this.getComponent(),
            metadata: this.buildScreenRouteInfoMetaData()
        };
    }

    public buildScreenRouteInfoMetaData(): ScreenRouteInfo.RouteMetaData
    {
        return {
            screenId: this.getScreenId(),
            viewType: this.getViewType(),
            resolveType: this.getResolveType(),
            registerType: this.getRegisterType()
        };
    }

    public buildRouteRecord(): RouteRecordRaw
    {
        const routeInfo: ScreenRouteInfo.RouteInfo = this.buildScreenRouteInfo();

        return {
            name: routeInfo.name,
            path: routeInfo.path,
            component: routeInfo.component,
            meta: this.buildScreenRouteInfoMetaData()
        };
    }
};


export abstract class PrimeViewScreenRouteBuilder extends ScreenRouteBuilder {
    public getViewType(): ScreenRouteInfo.ViewType {
        return ScreenRouteInfo.ViewType.PrimeView;
    }

    public getResolveType(): ScreenRouteInfo.ResolveType {
        return ScreenRouteInfo.ResolveType.static;
    }
};


export abstract class WorkspaceViewScreenRouteBuilder extends ScreenRouteBuilder {
    public abstract getPageTitle(): string;
    public abstract getPageTitleI18ns(): LabelI18n[];
    public abstract getPageDescription(): string;
    public abstract getPageDescriptionI18ns(): LabelI18n[];
    public abstract getPageBreadcrumbs(): Breadcrumb[];

    public getViewType(): ScreenRouteInfo.ViewType {
        return ScreenRouteInfo.ViewType.WorkspaceView;
    }

    public getResolveType(): ScreenRouteInfo.ResolveType {
        return ScreenRouteInfo.ResolveType.static;
    }

    public buildScreenRouteInfoMetaData(): ScreenRouteInfo.RouteMetaData
    {
        console.info("### WorkspaceViewScreenRouteBuilder.buildScreenRouteInfoMetaData ###");

        return {
            ...super.buildScreenRouteInfoMetaData(),
            pageTitle: this.getPageTitle(),
            pageTitleI18ns: this.getPageTitleI18ns(),
            pageDescription: this.getPageDescription(),
            pageDescriptionI18ns: this.getPageDescriptionI18ns(),
            pageBreadcrumbs: this.getPageBreadcrumbs()
        };
    }
};