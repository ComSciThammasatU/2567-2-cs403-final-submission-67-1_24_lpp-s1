import ScreenRouteInfo from '@/app/router/screen/screen.route.info';
import { PrimeViewScreenRouteBuilder } from '@/app/router/screen/screen.route.builder';
import { RouteRecordRaw } from "vue-router";
import LoginView from '@/modules/system/ui/views/auth/LoginView.vue';

class LoginRouteBuilder extends PrimeViewScreenRouteBuilder {
    public getScreenId(): string {
        return "screen.web.system.auth.login";
    }

    public getRouteName(): string {
        return "system.auth.login";
    }

    public getRoutePath(): string {
        return "/login";
    }

    public getComponent() {
        return LoginView;
    }

    public getRegisterType(): ScreenRouteInfo.RegisterType {
        return ScreenRouteInfo.RegisterType.eager;
    }
};

namespace LoginRoute {
    const builder = new LoginRouteBuilder();

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

export default LoginRoute;