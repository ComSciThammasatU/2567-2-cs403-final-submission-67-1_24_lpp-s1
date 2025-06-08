<template>
    <li class="nav-item dropdown user-menu">
        <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">
            <img :src="profileImageURL" class="user-image img-circle elevation-2" alt="User Image" :style="{'background-color': userAvatarComposer.backgroundColor.value}">
        </a>
        <ul class="dropdown-menu dropdown-menu-sm dropdown-menu-right">
            <!-- User image -->
            <li class="user-header bg-light">
                <img :src="profileImageURL" class="img-circle elevation-3" alt="User Image">
                <p>
                    <div class="font-weight-bold fms-text-primary-deep">{{ authStore.userProfile?.user?.accountName }}</div>
                </p>
            </li>
            <!-- Menu Footer-->
            <li class="user-footer">
                <template v-if="settingStore.profileMenuItem.visible">
                    <a href="javascript:void(0)" class="btn btn-outline-dark btn-block text-left disabled" @click="gotoUserProfile()">
                        <i :class="settingStore.profileMenuItem.iconClass" style="font-size: 1.25em;"></i> 
                        &nbsp; {{ settingStore.profileMenuItemLabel }}
                    </a>
                </template>

                <template v-if="settingStore.changePasswordMenuItem.visible">
                    <a href="javascript:void(0)" class="btn btn-outline-dark btn-block text-left" @click="gotoChangePassword()">
                        <i :class="settingStore.changePasswordMenuItem.iconClass" style="font-size: 1.25em;"></i> 
                        &nbsp; {{ settingStore.changePasswordMenuItemLabel }}
                    </a>
                </template>

                <template v-if="settingStore.signoutMenuItem.visible">
                    <a href="javascript:void(0)" class="btn btn-outline-dark btn-block text-left" @click="requestLogout()">
                        <i :class="settingStore.signoutMenuItem.iconClass" style="font-size: 1.25em;"></i> 
                        &nbsp; {{ settingStore.signoutMenuItemLabel }}
                    </a>
                </template>
            </li>
        </ul>
    </li>
</template>

<script setup lang="ts">
    import { computed } from 'vue';
    import { useAuthStore } from '@/app/store/auth.store';
    import { useUserAvatarComposer } from '@/modules/common/composer/user-avatar.composer';
    import EzAlert from '@/modules/common/ui/composables/ez-alert';
    import { useSettingStore } from '@/app/store/setting.store';
    import appRouter from '@/app/router/app.router';
    // import UserProfileRoute from "@/app/router/routes/workspace/user-profile.route";
    // import ChangePasswordRoute from '@/app/router/routes/workspace/change-password.route';

    const authStore = useAuthStore();
    const userAvatarComposer = useUserAvatarComposer();
    const settingStore = useSettingStore();

    const profileImageURL = computed(() => userAvatarComposer.profileImageURL.value);

    function gotoUserProfile()
    {
        // appRouter.getRouter().push({ path: UserProfileRoute.getRoutePath() });
    }

    function gotoChangePassword()
    {
        // appRouter.getRouter().push({ path: ChangePasswordRoute.getRoutePath() });
    }

    async function requestLogout()
    {
        const result = await EzAlert.showConfirm({
            title: "Sign Out",
            text: "Are you sure you want to sign out?",
            buttonText: "Yes",
            cancelButtonText: "No",
        });

        if(result.isConfirmed) {
            authStore.logout();
        }
    }
</script>