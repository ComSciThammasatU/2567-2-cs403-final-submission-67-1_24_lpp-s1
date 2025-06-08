<template>
    <div style="border: 1px solid red;" v-auto-animate>
        Display: <input type="checkbox" v-model="state.display">
        <div v-if="state.display">
            <h6>Content</h6>
            <div style="border: 1px solid blue;">
                <slot :display="state.display" :displayName="authStore.state.userProfile?.accountName"></slot>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { reactive } from 'vue';
    import { useAuthStore } from '@/app/store/auth.store';

    const authStore = useAuthStore();

    const state = reactive({
        display: false
    });
</script>