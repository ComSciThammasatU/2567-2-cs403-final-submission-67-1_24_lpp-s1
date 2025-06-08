import ScreenRouteInfo from '@/app/router/screen/screen.route.info';
import { WorkspaceViewScreenRouteBuilder } from '@/app/router/screen/screen.route.builder';
import { RouteRecordRaw } from "vue-router";
import { LabelI18n } from "@/modules/common/model/label.model";
import { Breadcrumb } from "@/modules/common/model/breadcrumb.model";
import HomeView from '@/app/ui/views/workspace/HomeView.vue';

class HomeRouteBuilder extends WorkspaceViewScreenRouteBuilder {
    public getScreenId(): string {
        return "screen.web.core.home";
    }

    public getRouteName(): string {
        return "core.home";
    }

    public getRoutePath(): string {
        return "/";
    }

    public getComponent() {
        return HomeView;
    }

    public getRegisterType(): ScreenRouteInfo.RegisterType {
        return ScreenRouteInfo.RegisterType.eager;
    }

    public getPageTitle(): string {
        return "Home"
    }

    public getPageTitleI18ns(): LabelI18n[] {
        return [
            {
                langCode: "en",
                value: "Home"
            },
            {
                langCode: "th",
                value: "หน้าหลัก"
            }
        ];
    }

    public getPageDescription(): string {
        return this.getPageTitle();
    }

    public getPageDescriptionI18ns(): LabelI18n[] {
        return this.getPageTitleI18ns();
    }

    public getPageBreadcrumbs(): Breadcrumb[] {
        return [];
    }
};

namespace HomeRoute {
    const builder = new HomeRouteBuilder();

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

export default HomeRoute;