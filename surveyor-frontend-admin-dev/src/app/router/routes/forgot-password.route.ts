import ScreenRouteInfo from '@/app/router/screen/screen.route.info';
import { PrimeViewScreenRouteBuilder } from '@/app/router/screen/screen.route.builder';
import { RouteRecordRaw } from "vue-router";

class ForgotPasswordRouteBuilder extends PrimeViewScreenRouteBuilder {
    public getScreenId(): string {
        return "screen.web.system.auth.forgot_password";
    }

    public getRouteName(): string {
        return "system.auth.forgot_password";
    }

    public getRoutePath(): string {
        return "/system/auth/forgot-password";
    }

    public getComponent() {
        return () => import('@/modules/system/ui/views/auth/ForgotPasswordView.vue');
    }

    public getRegisterType(): ScreenRouteInfo.RegisterType {
        return ScreenRouteInfo.RegisterType.lazy;
    }
};

namespace ForgotPasswordRoute {
    const builder = new ForgotPasswordRouteBuilder();

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

export default ForgotPasswordRoute;