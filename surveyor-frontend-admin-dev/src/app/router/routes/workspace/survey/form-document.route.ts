import { RouteRecordRaw } from 'vue-router';
import { WorkspaceViewScreenRouteBuilder } from "@/app/router/screen/screen.route.builder";
import ScreenRouteInfo from "@/app/router/screen/screen.route.info";
import { Breadcrumb } from "@/modules/common/model/breadcrumb.model";
import { LabelI18n } from "@/modules/common/model/label.model";
import WorkSpaceLayoutRoute from '../../workspace-layout.route';

class FormDocumentRouteBuilder extends WorkspaceViewScreenRouteBuilder {
    public getScreenId(): string {
        return "screen.web.survey.form-document";
    }

    public getRouteName(): string {
        return "survey.form-document";
    }

    public getRoutePath(): string {
        return WorkSpaceLayoutRoute.getRoutePath() + "/survey/form-document";
    }

    public getComponent() {
        return () => import('@/modules/survey/ui/views/FormDocumentView.vue');
    }

    public getRegisterType(): ScreenRouteInfo.RegisterType {
        return ScreenRouteInfo.RegisterType.lazy;
    }

    public getPageTitle(): string {
        return "แบบสอบถาม";
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
        return [];
    }
};

namespace FormDocumentRoute {
    const builder = new FormDocumentRouteBuilder();

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

export default FormDocumentRoute;