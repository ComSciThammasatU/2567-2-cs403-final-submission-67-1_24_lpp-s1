import { createRouter, RouteRecordRaw, createWebHashHistory, Router, RouteLocationNormalized, RouteLocationNormalizedLoaded } from 'vue-router';
import { useAppStore } from '@/app/store/app.store';

import LoginRoute from './routes/login.route';
// import ForgotPasswordRoute from './routes/forgot-password.route';
// import ForceChangePasswordRoute from './routes/force-change-password.route';
import WorkSpaceLayoutRoute from './routes/workspace-layout.route';
import FallbackRoute from './routes/fallback.route';

import logPreRouteHook from './hooks/pre-route/log.pre-route.hook';
import workspaceRouter from './workspace.router';
import loadScreenHook from './hooks/pre-route/load-screen.hook';
import authenRouteGuard from './guards/authen.route.guard';
import loginRouteGuard from './guards/login.route.guard';
import forceChangePasswordRouteGuard from './guards/force-change-password.route.guard';

class AppRouter
{
    protected router?: Router;
    protected previousRoutePath: string = '';

    public async setup()
    {
        console.log("### AppRouter.setup ###");

        await workspaceRouter.setup();

        const routes: RouteRecordRaw[] = [
            LoginRoute.build(),
            // ForceChangePasswordRoute.build(),
            // ForgotPasswordRoute.build(),

            WorkSpaceLayoutRoute.build(workspaceRouter.getRoutes()),

            FallbackRoute.build()
        ];

        console.log("routes => ", routes);

        this.router = createRouter({
            routes: routes,
            history: createWebHashHistory(),
            linkActiveClass: "active",
            linkExactActiveClass: "active"
        });

        this.installPreRouteHooks();
        this.installGuards();
        this.installResolveRouteHook();
        this.installPostRouteHooks();

        this.router.onError((error: any, to: RouteLocationNormalized, from: RouteLocationNormalizedLoaded) => {
            console.log("!!! router.onError !!!");
            console.error(error);

            const appStore = useAppStore();
            appStore.stopLoading();
        });
    }

    protected getWorkSpaceLayoutRoute(): RouteRecordRaw | undefined
    {
        return this.router?.getRoutes().find(e => e.name === WorkSpaceLayoutRoute.getRouteName());
    }

    public async setupWorkSpaceDynamicRoutes()
    {
        console.log("### AppRouter.setupWorkSpaceDynamicRoutes ###");

        await workspaceRouter.buildDynamicRoutes();
        this.router?.removeRoute(WorkSpaceLayoutRoute.getRouteName());
        this.router?.addRoute(WorkSpaceLayoutRoute.build(workspaceRouter.getRoutes()));
    }

    public destroyWorkSpaceDynamicRoutes()
    {
        console.log("### AppRouter.destroyWorkSpaceDynamicRoutes ###");

        workspaceRouter.removeDynamicRoutes();
        this.router?.removeRoute(WorkSpaceLayoutRoute.getRouteName());
        this.router?.addRoute(WorkSpaceLayoutRoute.build(workspaceRouter.getRoutes()));
    }

    protected installPreRouteHooks()
    {
        this.router?.beforeEach(async (to, from, next) => logPreRouteHook.executeHook(to, from, next));
    }

    protected installGuards()
    {
        // local auth (check accessToken from authStore)
        this.router?.beforeEach(async (to, from, next) => loginRouteGuard.launchGuardProtocol(to, from, next));
        this.router?.beforeEach(async (to, from, next) => authenRouteGuard.launchGuardProtocol(to, from, next));
        this.router?.beforeEach(async (to, from, next) => forceChangePasswordRouteGuard.launchGuardProtocol(to, from, next));
    }

    protected installResolveRouteHook()
    {
        // load-screen-info

        this.router?.beforeEach((to, from) => {
            const appStore = useAppStore();
            appStore.startLoading();
        });

        this.router?.beforeEach(async (to, from, next) => loadScreenHook.executeHook(to, from, next));

        this.router?.beforeResolve((to, from) => {
            const appStore = useAppStore();
            appStore.stopLoading();
        });
    }

    protected installPostRouteHooks()
    {
        this.router?.afterEach((to, from, failure) => {
            console.log("!!! router.asfterEach.1 !!!");
            this.previousRoutePath = to.path;
            if(failure) {
                console.error("failure => ", failure);
            }
        });
    }

    public getRouter(): Router
    {
        return this.router as Router;
    }

    public getPreviousRoutePath()
    {
        return this.previousRoutePath;
    }
}

export default new AppRouter();