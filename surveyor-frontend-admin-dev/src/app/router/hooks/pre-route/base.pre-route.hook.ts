import { RouteLocationNormalized, NavigationGuardNext } from 'vue-router';

abstract class BasePreRouteHook
{
    public abstract executeHook(to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext): any;
}

export default BasePreRouteHook;