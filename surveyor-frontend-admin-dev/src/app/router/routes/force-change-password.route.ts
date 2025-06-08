import ScreenRouteInfo from '@/app/router/screen/screen.route.info';
import { PrimeViewScreenRouteBuilder } from '@/app/router/screen/screen.route.builder';
import { RouteRecordRaw } from "vue-router";

class ForceChangePasswordRouteBuilder extends PrimeViewScreenRouteBuilder {
    public getScreenId(): string {
        return "screen.web.system.auth.force_change_password";
    }

    public getRouteName(): string {
        return "system.auth.force_change_password";
    }

    public getRoutePath(): string {
        return "/system/auth/force-change-password";
    }

    public getComponent() {
        return () => import('@/modules/system/ui/views/auth/ForceChangePasswordView.vue');
    }

    public getRegisterType(): ScreenRouteInfo.RegisterType {
        return ScreenRouteInfo.RegisterType.lazy;
    }
};

namespace ForceChangePasswordRoute {
    const builder = new ForceChangePasswordRouteBuilder();

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

export default ForceChangePasswordRoute;