<template>
    <Login :callback-url="state.callbackURL" />
</template>

<script lang="ts" setup>
    import { reactive } from 'vue';
    import { useRoute } from 'vue-router';
    import Login from '@/modules/system/ui/components/auth/Login.vue';
    import HomeRoute from '@/app/router/routes/workspace/home.route';

    const router = useRoute();

    const state = reactive({
        callbackURL: router.query['callbackURL'] as string || HomeRoute.getRoutePath()
    });
</script>