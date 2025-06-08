import { useAuthStore } from "@/app/store/auth.store";
import { NavigationGuardNext, RouteLocationNormalized } from "vue-router";
// import ForgotPasswordRoute from "../routes/forgot-password.route";
import LoginRoute from "../routes/login.route";
import BaseRouteGuard from "./base.route.guard";

class AuthenRouteGuard extends BaseRouteGuard
{
    public launchGuardProtocol(to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) 
    {
        console.log("### AuthenRouteGuard.launchGuardProtocol ###");

        const authStore = useAuthStore();

        try {
            if(this.shouldFilter(to.path)) {
                if(authStore.hasSignedIn()) {
                    console.log("signed in..");
                    next();
                    return;
                } else {
                    console.log("not signed in..");
                    authStore.logout();
                }
            }

            next();
        } catch(error: any) {
            console.log("### AuthenRouteGuard.launchGuardProtocol.Error ###");
            console.log("error => ", error);
            next(error);
        }
    }

    protected shouldFilter(targetPath: string)
    {
        return this.getIgnorePaths().find(e => e === targetPath) === undefined;
    }

    protected getIgnorePaths(): string[]
    {
        return [
            LoginRoute.getRoutePath(),
            // ForgotPasswordRoute.getRoutePath()
        ];
    }
}

export default new AuthenRouteGuard();