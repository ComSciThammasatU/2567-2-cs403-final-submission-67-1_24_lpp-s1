import { useWorkSpaceStore } from "@/app/store/workspace.store";
import AbstractBootstrapHook from "./bootstrap.hook";

class WorkSpaceLoadingBootstrapHook extends AbstractBootstrapHook
{
    public async launchHookOperation(): Promise<void> 
    {
        console.info("### WorkSpaceLoadingBootstrapHook.launchHookOperation ###");

        const workspaceStore = useWorkSpaceStore();
        workspaceStore.loadWorkSpaceInfo();
    }
}

export default new WorkSpaceLoadingBootstrapHook();