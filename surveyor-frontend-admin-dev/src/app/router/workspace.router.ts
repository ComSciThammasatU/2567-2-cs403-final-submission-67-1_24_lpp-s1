import { RouteRecordRaw } from "vue-router";
import { Action, ItemType, Menu } from "@/modules/common/model/menu.model";
import HomeRoute from './routes/workspace/home.route';
// import UserProfileRoute from "./routes/workspace/user-profile.route";
// import ChangePasswordRoute from "./routes/workspace/change-password.route";
import Error403Route from './routes/workspace/error-403.route';
import Error404Route from './routes/workspace/error-404.route';
import Error500Route from './routes/workspace/error-500.route';
import coreWorkspaceRouter from "./routes/workspace/core/core.workspace.router";
import surveyWorkspaceRouter from "./routes/workspace/survey/survey.workspace.router";

class WorkspaceRouter
{
    protected routes: RouteRecordRaw[] = [];

    public async setup()
    {
        console.log("### WorkspaceRouter.setup ###");

        this.buildStaticRoutes();
    }

    public buildStaticRoutes()
    {
        console.log("### WorkSpaceRouter.buildStaticRoutes ###");

        this.routes = [
            HomeRoute.build(),
            // UserProfileRoute.build(),
            // ChangePasswordRoute.build(),

            Error403Route.build(),
            Error404Route.build(),
            Error500Route.build(),

            ...coreWorkspaceRouter.buildRoutes(),
            ...surveyWorkspaceRouter.buildRoutes()
        ];
    }

    public async buildDynamicRoutes()
    {
        console.log("### WorkSpaceRouter.buildDynamicRoutes ###");

        // const menuStore = useMenuStore();
        // const menus = await menuStore.loadMyMenus();

        // menus.forEach((menu: Menu) => {
        //     this.addDynamicRoute(this.routes, menu);
        // });
    }

    protected addDynamicRoute(routes: RouteRecordRaw[], menu: Menu)
    {
        if(menu.itemType === ItemType.Section) {
            if(menu.children && menu.children.length > 0) {
                menu.children.forEach(child => {
                    this.addDynamicRoute(routes, child);
                });
            }
        } else if(menu.itemType == ItemType.MenuDropdown) {
            if(menu.children && menu.children.length > 0) {
                menu.children.forEach(child => {
                    this.addDynamicRoute(routes, child);
                });
            }
        } else if(menu.itemType === ItemType.MenuItem) {
            if(menu.action === Action.Route) {
                routes.push({
                    path: menu.href as string,
                    component: () => import(`../${menu.viewPath}`),
                    meta: {
                        routeResolveType: 'dynamic',
                        menu: menu,
                        menuId: menu.menuId
                    }
                });
            }
        }
    }

    public getRoutes(): RouteRecordRaw[]
    {
        return this.routes;
    }

    public getStaticRoutes(): RouteRecordRaw[]
    {
        return this.routes.filter(e => e.meta?.routeResolveType === 'static');
    }

    public getDynamicRoutes(): RouteRecordRaw[]
    {
        return this.routes.filter(e => e.meta?.routeResolveType === 'dynamic');
    }

    public removeDynamicRoutes()
    {
        this.routes = this.getStaticRoutes();
    }
}

export default new WorkspaceRouter();