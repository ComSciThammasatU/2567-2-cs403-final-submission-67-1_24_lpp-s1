import { computed, reactive } from "vue";
import { defineStore } from "pinia";

export const THEME_STORE_ID = "theme";

type Theme = "genesis-light" | "genesis-dark";

export const useThemeStore = defineStore(THEME_STORE_ID, () => {
    const state = reactive({
        currentTheme: "genesis-dark" as Theme
    });

    const currentTheme = computed(() => state.currentTheme);

    function changeTheme(theme: Theme)
    {
        console.info("### ThemeStore.changeTheme ###");
        console.info(`currentTheme => ${currentTheme.value}`);
        console.info(`theme => ${theme}`);

        console.log("### ThemeStore.changeTheme ###");
        state.currentTheme = theme;
        applyTheme();
    }

    function applyTheme()
    {
        console.info("### ThemeStore.applyTheme ###");
        console.info(`currentTheme => ${currentTheme.value}`);

        document.body.classList.add(...getBodyClasses());
        document.querySelector("#main-navbar")?.classList.add(...getMainNavBarClasses());
        document.querySelector("#main-sidebar")?.classList.add(...getMainSideBarClasses());
    }

    function getBodyClasses(): string[]
    {
        if(currentTheme.value === "genesis-light") {
            return ["hold-transition", "sidebar-mini", "layout-navbar-fixed", "layout-fixed", "accent-teal", "text-sm"];
        } else if(currentTheme.value === "genesis-dark") {
            return ["hold-transition", "sidebar-mini", "layout-navbar-fixed", "layout-fixed", "accent-teal", "text-sm"];
        }
        return [];
    }

    function getMainNavBarClasses(): string[]
    {
        if(currentTheme.value === "genesis-light") {
            return ["navbar-teal", "navbar-light"];
        } else if(currentTheme.value === "genesis-dark") {
            return ["navbar-teal", "navbar-light"];
        }
        return [];
    }

    function getMainSideBarClasses(): string[]
    {
        if(currentTheme.value === "genesis-light") {
            return ["main-sidebar", "elevation-4", "sidebar-light-teal"];
        } else if(currentTheme.value === "genesis-dark") {
            return ["main-sidebar", "elevation-4", "sidebar-dark-teal"];
        }
        return [];
    }

    function getMainSideBarBrandClasses(): string[]
    {
        if(currentTheme.value === "genesis-light") {
            return ["brand-link", "navbar-light", "text-dark"];
        } else if(currentTheme.value === "genesis-dark") {
            // return ["brand-link", "navbar-dark", "text-light"];
            return ["brand-link", "navbar-light", "text-dark"];
        }
        return [];
    }
  
    return {
        currentTheme,

        changeTheme,
        applyTheme,

        getBodyClasses,
        getMainNavBarClasses,
        getMainSideBarClasses,
        getMainSideBarBrandClasses
    };
});