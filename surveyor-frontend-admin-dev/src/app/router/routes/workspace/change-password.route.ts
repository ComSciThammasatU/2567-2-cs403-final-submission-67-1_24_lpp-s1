import ScreenRouteInfo from '@/app/router/screen/screen.route.info';
import { WorkspaceViewScreenRouteBuilder } from '@/app/router/screen/screen.route.builder';
import { RouteRecordRaw } from "vue-router";
import { LabelI18n } from "@/modules/common/model/label.model";
import { Breadcrumb, BreadcrumbType } from "@/modules/common/model/breadcrumb.model";
import WorkSpaceLayoutRoute from '../workspace-layout.route';

class ChangePasswordRouteBuilder extends WorkspaceViewScreenRouteBuilder {
    public getScreenId(): string {
        return "screen.web.system.auth.change_password";
    }

    public getRouteName(): string {
        return "system.auth.change_password";
    }

    public getRoutePath(): string {
        return WorkSpaceLayoutRoute.getRoutePath() + "/system/auth/change-password";
    }

    public getComponent() {
        return () => import('@/modules/system/ui/views/auth/ChangePasswordView.vue');
    }

    public getRegisterType(): ScreenRouteInfo.RegisterType {
        return ScreenRouteInfo.RegisterType.lazy;
    }

    public getPageTitle(): string {
        return "Change Password"
    }

    public getPageTitleI18ns(): LabelI18n[] {
        return [
            {
                langCode: "en",
                value: "Change Password"
            },
            {
                langCode: "th",
                value: "เปลี่ยนรหัสผ่าน"
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
                text: "Change Password",
                textI18ns: [
                    {
                        langCode: "en",
                        value: "Change Password"
                    },
                    {
                        langCode: "th",
                        value: "เปลี่ยนรหัสผ่าน"
                    }
                ]
            }
        ];
    }
};

namespace ChangePasswordRoute {
    const builder = new ChangePasswordRouteBuilder();

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

export default ChangePasswordRoute;