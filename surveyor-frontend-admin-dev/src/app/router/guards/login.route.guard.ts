import { useAuthStore } from "@/app/store/auth.store";
import { NavigationGuardNext, RouteLocationNormalized } from "vue-router";
import LoginRoute from "../routes/login.route";
import BaseRouteGuard from "./base.route.guard";

class LoginRouteGuard extends BaseRouteGuard
{
    public launchGuardProtocol(to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) 
    {
        console.log("### LoginRouteGuard.launchGuardProtocol ###");

        const authStore = useAuthStore();

        if(to.path === LoginRoute.getRoutePath() && authStore.hasSignedIn()) {
            next({ path: from.path });
        } else {
            next();
        }
    }
}

export default new LoginRouteGuard();