import { RouteRecordRaw } from "vue-router";
import ScreenRouteInfo from '@/app/router/screen/screen.route.info';
import { WorkspaceViewScreenRouteBuilder } from '@/app/router/screen/screen.route.builder';
import { LabelI18n } from '@/modules/common/model/label.model';
import { Breadcrumb, BreadcrumbType } from '@/modules/common/model/breadcrumb.model';
import WorkSpaceLayoutRoute from "../workspace-layout.route";

class Error500RouteBuilder extends WorkspaceViewScreenRouteBuilder {
    public getScreenId(): string {
        return "screen.web.system.error.500";
    }

    public getRouteName(): string {
        return "system.error.500";
    }

    public getRoutePath(): string {
        return WorkSpaceLayoutRoute.getRoutePath() + "/error/500";
    }

    public getComponent() {
        return () => import('@/app/ui/views/workspace/error/Error500View.vue');
    }

    public getRegisterType(): ScreenRouteInfo.RegisterType {
        return ScreenRouteInfo.RegisterType.lazy;
    }

    public getPageTitle(): string {
        return "Error 500 - Internal Server Error";
    }

    public getPageTitleI18ns(): LabelI18n[] {
        return [];
    }

    public getPageDescription(): string {
        return this.getPageTitle();
    }

    public getPageDescriptionI18ns(): LabelI18n[] {
        return this.getPageTitleI18ns();
    }

    public getPageBreadcrumbs(): Breadcrumb[] {
        return [
            {
                type: BreadcrumbType.Text,
                text: 'Error'
            },
            {
                type: BreadcrumbType.Text,
                text: '500'
            }
        ];
    }
};

namespace Error500Route {
    const builder = new Error500RouteBuilder();

    export function build(): RouteRecordRaw {
        return {
            name: getRouteName(),
            path: getRoutePath(),
            component: builder.getComponent(),
            meta: buildMeta()
        };
    }

    export function getRouteName(): string {
        return builder.getRouteName();
    };

    export function getRoutePath(): string {
        return builder.getRoutePath();
    };

    function buildMeta(): ScreenRouteInfo.RouteMetaData {
        return builder.buildScreenRouteInfoMetaData();
    };
};

export default Error500Route;