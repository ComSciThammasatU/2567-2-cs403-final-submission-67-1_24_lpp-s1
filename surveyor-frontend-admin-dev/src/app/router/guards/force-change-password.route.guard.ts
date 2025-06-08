import { useAuthStore } from "@/app/store/auth.store";
import { NavigationGuardNext, RouteLocationNormalized } from "vue-router";
// import ForceChangePasswordRoute from "../routes/force-change-password.route";
import BaseRouteGuard from "./base.route.guard";

class ForceChangePasswordRouteGuard extends BaseRouteGuard
{
    public launchGuardProtocol(to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) 
    {
        console.log("### ForceChangePasswordRouteGuard.launchGuardProtocol ###");

        const authStore = useAuthStore();

        try {
            // if(this.matchCondition(to, from)) {
            //     if(from.path === ForceChangePasswordRoute.getRoutePath()) {
            //         location.reload();
            //         return false;
            //     } else {
            //         authStore.gotoForceChangePasswordPage();
            //     }
            // }

            next();
        } catch(error: any) {
            console.log("### ForceChangePasswordRouteGuard.launchGuardProtocol.Error ###");
            console.log("error => ", error);
            next(error);
        }
    }

    // protected matchCondition(to: RouteLocationNormalized, from: RouteLocationNormalized): boolean
    // {
    //     const authStore = useAuthStore();
    //     return authStore.hasSignedIn() && authStore.shouldForceChangePassword() && to.path !== ForceChangePasswordRoute.getRoutePath();
    // }
}

export default new ForceChangePasswordRouteGuard();