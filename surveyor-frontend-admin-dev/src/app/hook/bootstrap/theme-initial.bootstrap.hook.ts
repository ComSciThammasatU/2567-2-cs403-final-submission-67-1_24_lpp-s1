import { useThemeStore } from "@/app/store/theme.store";
import AbstractBootstrapHook from "./bootstrap.hook";

class ThemeInitialBootstrapHook extends AbstractBootstrapHook
{
    public async launchHookOperation(): Promise<void> 
    {
        console.info("### ThemeInitialBootstrapHook.launchHookOperation ###");

        const themeStore = useThemeStore();
        themeStore.applyTheme();
    }
}

export default new ThemeInitialBootstrapHook();