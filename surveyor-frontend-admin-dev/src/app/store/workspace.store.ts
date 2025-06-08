import appRouter from '@/app/router/app.router';
import { useAuthStore } from '@/app/store/auth.store';
import { Label } from '@/modules/common/model/label.model';
import { Breadcrumb, BreadcrumbType } from '@/modules/common/model/breadcrumb.model';
import { computed, reactive } from "vue";
import { defineStore } from 'pinia';
import { SideBarMenuMode } from '@/modules/common/const/sidebar-menu-mode.const';
import workspaceService from '@/modules/core/service/workspace.service';
import { StatusType } from '@/modules/common/model/http.response-message.model';
import EzAlert from '@/modules/common/ui/composables/ez-alert';

export const WORKSPACE_STORE_ID = "workspace";

const getSideBarMenuMode = (): SideBarMenuMode => {
    if(document.body.classList.contains("sidebar-collapse")) {
        return SideBarMenuMode.Collapsed;
    } else {
        return SideBarMenuMode.Expanded;
    }
};

export const useWorkSpaceStore = defineStore(WORKSPACE_STORE_ID, () => {
    const authStore = useAuthStore();

    const state = reactive({
        labels: [] as Label[],
        pageTitle: '' as string,
        pageBreadcrumbs: [] as Breadcrumb[],
        sidebarMenuMode: getSideBarMenuMode()
    });

    function updatePageTitle()
    {   
        if(appRouter.getRouter().currentRoute && appRouter.getRouter().currentRoute.value.meta) {
            const pageTitle = appRouter.getRouter().currentRoute.value.meta.pageTitle as string || '';
            if(pageTitle) {
                state.pageTitle = pageTitle;
                return;
            }
        }
    }

    const labels = computed(() => state.labels);

    const pageTitle = computed(() => {
        updatePageTitle();
        return state.pageTitle;
    });

    const pageBreadcrumbs = computed(() => {
        let breadcrumbs: Breadcrumb[] = [
            {
                type: BreadcrumbType.Text,
                text: pageTitle.value
            }
        ];

        if(appRouter.getRouter().currentRoute && appRouter.getRouter().currentRoute.value.meta) {
            const pageBreadcrumbs = appRouter.getRouter().currentRoute.value.meta.pageBreadcrumbs as Breadcrumb[] || undefined;
            if(pageBreadcrumbs && pageBreadcrumbs.length > 0) {
                breadcrumbs = pageBreadcrumbs;
            }
        }

        return breadcrumbs;
    });

    const sidebarMenuMode = computed(() => state.sidebarMenuMode);

    const applySideBarMenuMode = (mode?: SideBarMenuMode) => {
        if(mode === undefined) {
            mode = SideBarMenuMode.Expanded;
        }

        if(mode === SideBarMenuMode.Expanded) {
            expandSideBarMenu();
        } else if(mode === SideBarMenuMode.Collapsed) {
            collapseSideBarMenu();
        }
    };

    const expandSideBarMenu = () => {
        document.body.classList.remove("sidebar-collapse");
        state.sidebarMenuMode = SideBarMenuMode.Expanded;
    };

    const collapseSideBarMenu = () => {
        document.body.classList.add("sidebar-collapse");
        state.sidebarMenuMode = SideBarMenuMode.Collapsed;
    };

    const toggleSideBarMenu = () => {
        if(sidebarMenuMode.value === SideBarMenuMode.Expanded) {
            collapseSideBarMenu();
        } else if(sidebarMenuMode.value === SideBarMenuMode.Collapsed) {
            expandSideBarMenu();
        }
    };

    async function loadWorkSpaceInfo()
    {
        console.info("### WorkSpaceStore.loadWorkSpaceInfo ###");

        try {
            const response = await workspaceService.loadWorkSpaceInfo();

            if(response.head.status.type === StatusType.Success) {
                
                const workspaceInfoResponse = response.body;

                if(workspaceInfoResponse) {
                    state.labels = workspaceInfoResponse.labels;

                    authStore.setUserProfile(workspaceInfoResponse.payloadData.userProfile);
                }

            } else {
                EzAlert.showError({
                    title: response.head.status.title,
                    text: response.head.status.message,
                    buttonText: "OK"
                });
            }
        } catch(error: any) {
            EzAlert.showError({
                title: error.name,
                text: error.message,
                buttonText: "Close"
            });
        }
    }

    function destroy()
    {
        console.info("### WorkSpaceStore.destroy ###");

        state.labels = [];
        state.pageTitle = '';
        state.pageBreadcrumbs = [];
    }
  
    return {
        labels,
        pageTitle,
        pageBreadcrumbs,
        sidebarMenuMode,

        applySideBarMenuMode,
        expandSideBarMenu,
        collapseSideBarMenu,
        toggleSideBarMenu,

        loadWorkSpaceInfo,
        destroy
    };
});