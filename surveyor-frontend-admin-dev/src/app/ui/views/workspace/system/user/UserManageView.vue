<template>
    <UserManage />
</template>

<script setup lang="ts">
    import { onMounted, onUnmounted } from 'vue';
    import { useScreenSentinelComposer } from '@/modules/common/composer/screen-sentinel.composer';
    import UserManage from '@/modules/system/ui/components/user/manage/UserManage.vue';
    import { useUserManageComposer } from '@/modules/system/ui/components/user/manage/user-manage.composer';
    import { useAppStore } from '@/app/store/app.store';
    import { StatusType } from '@/modules/common/model/http.response-message.model';

    const appStore = useAppStore();
    const screenSentinelComposer = useScreenSentinelComposer();
    const composer = useUserManageComposer();

    onMounted(() => {
        console.log("### UserManageView.onMounted ###");
        
        composer.init();
        loadScreen();
    });

    onUnmounted(() => {
        console.log("### UserManageView.onUnmounted ###");
        composer.destroy();
    });

    async function loadScreen()
    {
        try {
            appStore.startLoading();
            const response = await composer.loadScreen();
            if(response?.head.status.type === StatusType.Success) {
                screenSentinelComposer.verifyAccessPermission(response.body);
            }
        } catch(error) {
            console.error(error);
        } finally {
            appStore.stopLoading();
        }
    }
</script>