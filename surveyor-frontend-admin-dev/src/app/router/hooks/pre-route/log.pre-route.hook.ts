import { NavigationGuardNext, RouteLocationNormalized } from "vue-router";
import roleService from "@/modules/system/service/role.service";
import BasePreRouteHook from "./base.pre-route.hook";

class LogPreRouteHook extends BasePreRouteHook
{
    public async executeHook(to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) 
    {
        console.log("### LogPreRouteHook.executeHook ###");
        console.log(`from => ${from.path}`);
        console.log(`to => ${to.path}`);
        //const roles = (await roleService.fetchAll()).data;
        //console.log("roles => ", roles);
        next();
    }
}

export default new LogPreRouteHook();