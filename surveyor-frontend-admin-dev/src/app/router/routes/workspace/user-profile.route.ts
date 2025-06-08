import ScreenRouteInfo from '@/app/router/screen/screen.route.info';
import { WorkspaceViewScreenRouteBuilder } from '@/app/router/screen/screen.route.builder';
import { RouteRecordRaw } from "vue-router";
import { LabelI18n } from "@/modules/common/model/label.model";
import { Breadcrumb, BreadcrumbType } from "@/modules/common/model/breadcrumb.model";
import WorkSpaceLayoutRoute from '../workspace-layout.route';

class UserProfileRouteBuilder extends WorkspaceViewScreenRouteBuilder {
    public getScreenId(): string {
        return "screen.web.system.user.profile";
    }

    public getRouteName(): string {
        return "system.user.profile";
    }

    public getRoutePath(): string {
        return WorkSpaceLayoutRoute.getRoutePath() + "/system/user/profile";
    }

    public getComponent() {
        return () => import('@/app/ui/views/workspace/system/user/UserProfileView.vue');
    }

    public getRegisterType(): ScreenRouteInfo.RegisterType {
        return ScreenRouteInfo.RegisterType.lazy;
    }

    public getPageTitle(): string {
        return "User Profile"
    }

    public getPageTitleI18ns(): LabelI18n[] {
        return [
            {
                langCode: "en",
                value: "User Profile"
            },
            {
                langCode: "th",
                value: "โปรไฟล์"
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
        return [
            {
                type: BreadcrumbType.Text,
                text: "Setting",
                textI18ns: [
                    {
                        langCode: "en",
                        value: "Setting"
                    },
                    {
                        langCode: "th",
                        value: "ตั้งค่า"
                    }
                ]
            },
            {
                type: BreadcrumbType.Text,
                text: "User Profile",
                textI18ns: [
                    {
                        langCode: "en",
                        value: "User Profile"
                    },
                    {
                        langCode: "th",
                        value: "โปรไฟล์"
                    }
                ]
            }
        ];
    }
};

namespace UserProfileRoute {
    const builder = new UserProfileRouteBuilder();

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

export default UserProfileRoute;