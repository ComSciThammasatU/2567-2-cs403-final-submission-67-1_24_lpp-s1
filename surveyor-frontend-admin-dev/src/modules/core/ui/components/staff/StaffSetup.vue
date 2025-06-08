<template>
    <section>
        <template v-if="composer.state.mode === 'list'">
            <StaffSetupSearch />
            <StaffSetupList />
        </template>
        <template v-else-if="composer.state.mode === 'entry'">
            <StaffSetupEntry />
        </template>
    </section>
</template>

<script setup lang="ts">
    import { onMounted, onUnmounted } from 'vue';
    import { useStaffSetupComposer } from './staff-setup.composer';
    import StaffSetupEntry from './StaffSetupEntry.vue';
    import StaffSetupList from './StaffSetupList.vue';
    import StaffSetupSearch from './StaffSetupSearch.vue';
    
    const composer = useStaffSetupComposer();

    onMounted(async () => {
        console.log("### StaffSetup.onMounted ###");

        composer.init();
    });

    onUnmounted(async () => {
        console.log("### StaffSetup.onUnmounted ###");
        composer.destroy();
    });
</script>