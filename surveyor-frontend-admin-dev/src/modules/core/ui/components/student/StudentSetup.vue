<template>
    <section>
        <template v-if="composer.state.mode === 'list'">
            <StudentSetupSearch />
            <StudentSetupList />
        </template>
        <template v-else-if="composer.state.mode === 'entry'">
            <StudentSetupEntry />
        </template>
    </section>
</template>

<script setup>
    import { onMounted, onUnmounted } from 'vue';
    import { useStudentSetupComposer } from './student-setup.composer';
    import StudentSetupEntry from './StudentSetupEntry.vue';
    import StudentSetupList from './StudentSetupList.vue';
    import StudentSetupSearch from './StudentSetupSearch.vue';
    
    const composer = useStudentSetupComposer();

    onMounted(async () => {
        console.log("### StudentSetup.onMounted ###");
        composer.init();
    });

    onUnmounted(async () => {
        console.log("### StudentSetup.onUnmounted ###");
        composer.destroy();
    });
</script>