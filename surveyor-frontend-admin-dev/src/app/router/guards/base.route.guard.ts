import { NavigationGuardNext, RouteLocationNormalized } from "vue-router";

abstract class BaseRouteGuard
{
    public abstract launchGuardProtocol(to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext): any;
}

export default BaseRouteGuard;