<template>
    <nav id="main-navbar" class="main-header navbar navbar-expand" :class="themeStore.getMainNavBarClasses()">
        <!-- Left navbar links -->
        <ul class="navbar-nav">
            <li class="nav-item">
                <a v-if="false" class="nav-link" data-widget="pushmenu" href="#" role="button">
                    <i class="fas fa-bars" style="font-size: 1.2em;"></i>
                </a>
                <a class="nav-link" href="javascript:void(0)" @click="workspaceStore.toggleSideBarMenu()">
                    <i class="fas fa-bars" style="font-size: 1.2em;"></i>
                </a>
            </li>
            <router-link :to="HomeRoute.getRoutePath()" class="nav-link" title="หน้าหลัก">
                <i class="fas fa-home" style="font-size: 1.2em;"></i>
            </router-link>
            
            <li v-if="false" class="nav-item d-none d-sm-inline-block">
                <a href="#" class="nav-link">
                    <i class="fas fa-star" style="font-size: 1.2em;"></i>
                </a>
            </li>
        </ul>

        <!-- Right navbar links -->
        <ul class="navbar-nav ml-auto">
            <setting-dropdown />
        </ul>
    </nav>
</template>

<script setup lang="ts">
    import { useAuthStore } from '@/app/store/auth.store';
    import { useWorkSpaceStore } from '@/app/store/workspace.store';
    import { useThemeStore } from '@/app/store/theme.store';
    import SettingDropdown from './navbar/SettingDropdown.vue';
    import HomeRoute from '@/app/router/routes/workspace/home.route';

    const authStore = useAuthStore();
    const workspaceStore = useWorkSpaceStore();
    const themeStore = useThemeStore();
</script>