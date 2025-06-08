import { RouteLocationNormalized } from 'vue-router';

abstract class BasePostRouteHook 
{
    public abstract executeHook(to: RouteLocationNormalized, from: RouteLocationNormalized): any;
}

export default BasePostRouteHook;