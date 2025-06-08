import { RouteLocationNormalized, NavigationGuardNext } from "vue-router";
import BaseRouteGuard from "./base.route.guard";

class AuthorizeRouteGuard extends BaseRouteGuard
{
    public launchGuardProtocol(to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) 
    {
        throw new Error("Method not implemented.");
    }
}

export default new AuthorizeRouteGuard();