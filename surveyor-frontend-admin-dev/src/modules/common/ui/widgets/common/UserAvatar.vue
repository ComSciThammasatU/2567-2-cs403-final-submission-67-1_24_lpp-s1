<template>
    <div class="image">
        <img :src="profileImageURL" class="user-image img-circle elevation-2" alt="User Image" :style="{'background-color': backgroundColor}" />
    </div>
</template>

<script setup lang="ts">
    import { computed } from 'vue';
    import { useUserAvatarComposer } from '@/modules/common/composer/user-avatar.composer';

    const userAvatarComposer = useUserAvatarComposer();

    const profileImageURL = computed(() => {
        return userAvatarComposer.profileImageURL.value;
    });

    const backgroundColor = computed(() => {
        return userAvatarComposer.backgroundColor.value;
    });
</script>