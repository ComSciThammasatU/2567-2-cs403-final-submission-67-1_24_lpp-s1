import { useAppStore } from '@/app/store/app.store';
import { NavigationGuardNext, RouteLocationNormalized } from "vue-router";
import httpClient from "@/modules/common/service/http/http-client.service";
import BasePreRouteHook from "./base.pre-route.hook";

class LoadScreenRouteHook extends BasePreRouteHook
{
    public async executeHook(to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) 
    {
        console.log("### LoadScreenRouteHook.executeHook ###");
        try {
            //const resp = await httpClient.get("/api/load-screen");
            //console.log(`resp => `, resp);
            // await this.loadScreen();
            next();
        } catch(error: any) {
            console.log("### LoadScreenRouteHook.executeHook.Error ###");
            console.log("error => ", error);
            next(error);
        }
    }

    protected async loadScreen()
    {
        return new Promise((resolve: any, reject: any) => {
            setTimeout(() => {
                resolve();
            }, 500);
        });
    }
}

export default new LoadScreenRouteHook();