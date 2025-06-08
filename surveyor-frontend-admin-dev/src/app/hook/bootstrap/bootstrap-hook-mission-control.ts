import { useAuthStore } from "@/app/store/auth.store";
import EnvironmentProvider from "@/modules/common/service/env.provider";
import themeInitialBootstrapHook from "./theme-initial.bootstrap.hook";
import workspaceLoadingBootstrapHook from "./workspace-loading.bootstrap.hook";

class BootstrapHookMissionControl
{
    public static async launchBootstrapHookMission(): Promise<void>
    {
        console.info("### BootstrapHookMissionControl.lanuchBootstrapHookMission ###");

        console.log(`APP_NAME => ${EnvironmentProvider.getAppName()}`);
        console.log(`APP_VERSION => ${EnvironmentProvider.getAppVersion()}`);
        console.log(`MODE => ${EnvironmentProvider.getMode()}`);
        console.log(`RUNNING_MODE => ${EnvironmentProvider.getRunningMode()}`);
        console.log(`ENV => ${EnvironmentProvider.getEnv()}`);
        console.log(`BASE_URL => ${EnvironmentProvider.getBaseURL()}`);
        console.log(`SERVER_ENDPOINT => ${EnvironmentProvider.getServerEndpoint()}`);

        const authStore = useAuthStore();

        themeInitialBootstrapHook.launchHookOperation();

        if(authStore.hasSignedIn()) {
            // workspaceLoadingBootstrapHook.launchHookOperation();
        }
    }
}

export default BootstrapHookMissionControl;