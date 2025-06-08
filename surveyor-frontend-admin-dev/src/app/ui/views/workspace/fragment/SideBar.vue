<template>
    <aside id="main-sidebar" :class="themeStore.getMainSideBarClasses()">
        <router-link id="main-sidebar-brand" :class="themeStore.getMainSideBarBrandClasses()" :to="HomeRoute.getRoutePath()">
            <!-- <img :src="AssetLoader.getAppLogoImageURL()" alt="Surveyor Admin Logo" class="brand-image elevation-0"
                style="opacity: .99" /> -->
            <img :src="AssetLoader.getAppTUSurveyorLogoImgURL()" alt="TU Surveyor Admin Logo" class="brand-image elevation-0"
                style="opacity: .99" />    
            <span class="brand-text font-weight-bold"> TU Surveyor</span>
            <span class="brand-text font-weight-bold text-secondary">Admin</span>
            <!-- <span v-if="EnvironmentProvider.getEnv() !== EnvConst.prod">
                &nbsp;
                <span class="right badge badge-danger text-uppercase">{{ EnvironmentProvider.getEnv() }}</span>
                <span class="right badge badge-dark">{{ EnvironmentProvider.getAppVersion() }}</span>
            </span> -->
        </router-link>

        <div class="sidebar">
            <div class="user-panel mt-3 pb-3 mb-3 d-flex">
                <UserAvatar />

                <div class="info">
                    <a href="javascript:void(0)" class="d-block text-light font-weight-bold">{{
                        authStore.userProfile?.user?.accountName }}</a>
                </div>
            </div>

            <div class="form-inline d-none">
                <div class="input-group" data-widget="sidebar-search">
                    <input class="form-control form-control-sm form-control-sidebar" type="search" placeholder="Search"
                        aria-label="Search" />
                    <div class="input-group-append">
                        <button class="btn btn-sm btn-sidebar">
                            <i class="fas fa-search fa-fw"></i>
                        </button>
                    </div>
                </div>
            </div>

            <nav class="mt-2">
                <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu"
                    data-accordion="false">

                    <!-- User Setting -->
                    <li class="nav-header font-weight-bold text-light">User Setting</li>
                    <li class="nav-item">
                        <router-link to="/workspace/core/staff/setup" class="nav-link">
                            <i class="nav-icon fas fa-users"></i>
                            <p>เจ้าหน้าที่</p>
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/workspace/core/professor/setup" class="nav-link">
                            <i class="nav-icon fas fa-user-tie"></i>
                            <p>อาจารย์</p>
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/workspace/core/student/setup" class="nav-link">
                            <i class="nav-icon fas fa-user-graduate"></i>
                            <p>นักศึกษา</p>
                        </router-link>
                    </li>
                    <!-- User Setting -->

                    <!-- Survey -->
                    <li class="nav-header font-weight-bold text-light">Survey</li>
                    <li class="nav-item">
                        <router-link to="/workspace/survey/form-template/setup" class="nav-link">
                            <i class="nav-icon far fa-image"></i>
                            <p>เทมเพลต</p>
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/workspace/survey/form-document" class="nav-link">
                            <i class="nav-icon far fa-image"></i>
                            <p>แบบสอบถาม</p>
                        </router-link>
                    </li>
                    <!-- Survey -->
                </ul>
            </nav>
        </div>
    </aside>
</template>

<script setup lang="ts">
    import AssetLoader from "@/app/util/asset-loader";
    import { useThemeStore } from "@/app/store/theme.store";
    import { useAuthStore } from "@/app/store/auth.store";
    import UserAvatar from "@/modules/common/ui/widgets/common/UserAvatar.vue";
    import EnvironmentProvider from "@/modules/common/service/env.provider";
    import { EnvConst } from "@/modules/common/const/env.const";
    import HomeRoute from "@/app/router/routes/workspace/home.route";

    const authStore = useAuthStore();
    const themeStore = useThemeStore();
</script>