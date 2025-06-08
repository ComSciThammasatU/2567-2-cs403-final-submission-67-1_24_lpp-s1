import { RouteRecordRaw } from "vue-router";
import Error404Route from "./workspace/error-404.route";

namespace FallbackRoute {
    export function build(): RouteRecordRaw {
        return {
            name: 'Fallback',
            path: "/:catchAll(.*)",
            redirect: Error404Route.getRoutePath()
        };
    }

    export function getRouteName(): string {
        return "Fallback";
    };

    export function getRoutePath(): string {
        return "/:catchAll(.*)";
    };
};

export default FallbackRoute;