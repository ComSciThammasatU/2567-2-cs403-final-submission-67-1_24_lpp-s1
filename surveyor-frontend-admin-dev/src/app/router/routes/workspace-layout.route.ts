import ScreenRouteInfo from '@/app/router/screen/screen.route.info';
import { PrimeViewScreenRouteBuilder } from '@/app/router/screen/screen.route.builder';
import { RouteRecordRaw } from "vue-router";
import WorkSpaceLayout from '@/app/ui/views/workspace/WorkSpaceLayout.vue';

class WorkSpaceLayoutRouteBuilder extends PrimeViewScreenRouteBuilder {
    public getScreenId(): string {
        return "screen.web.core.workspace_layout";
    }

    public getRouteName(): string {
        return "core.workspace_layout";
    }

    public getRoutePath(): string {
        return "/workspace";
    }

    public getComponent() {
        return WorkSpaceLayout;
    }

    public getRegisterType(): ScreenRouteInfo.RegisterType {
        return ScreenRouteInfo.RegisterType.eager;
    }
};

namespace WorkSpaceLayoutRoute {
    const builder = new WorkSpaceLayoutRouteBuilder();

    export function build(nestedRoutes: RouteRecordRaw[]): RouteRecordRaw {
        return {
            name: getRouteName(),
            path: getRoutePath(),
            component: builder.getComponent(),
            children: nestedRoutes,
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

export default WorkSpaceLayoutRoute;