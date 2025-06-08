<template>
    <section>
        <template v-if="composer.state.mode === 'list'">
            <ProfessorSetupSearch />
            <ProfessorSetupList />
        </template>
        <template v-else-if="composer.state.mode === 'entry'">
            <ProfessorSetupEntry />
        </template>
    </section>
</template>

<script setup lang="ts">
    import { onMounted, onUnmounted } from 'vue';
    import { useProfessorSetupComposer } from './professor-setup.composer';
    import ProfessorSetupEntry from './ProfessorSetupEntry.vue';
    import ProfessorSetupList from './ProfessorSetupList.vue';
    import ProfessorSetupSearch from './ProfessorSetupSearch.vue';
    
    const composer = useProfessorSetupComposer();

    onMounted(async () => {
        console.log("### ProfessorSetup.onMounted ###");

        composer.init();
    });

    onUnmounted(async () => {
        console.log("### ProfessorSetup.onUnmounted ###");
        composer.destroy();
    });
</script>