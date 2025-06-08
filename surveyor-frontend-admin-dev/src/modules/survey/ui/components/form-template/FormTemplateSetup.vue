<template>
    <section>
        <template v-if="composer.state.mode === 'list'">
            <FormTemplateSetupSearch />
            <FormTemplateSetupList />
        </template>
        <template v-else-if="composer.state.mode === 'entry'">
            <FormTemplateSetupEntry />
        </template>
    </section>
</template>

<script setup lang="ts">
    import { onMounted, onUnmounted } from 'vue';
    import { useFormTemplateSetupComposer } from './form-template-setup.composer';
    import FormTemplateSetupSearch from './FormTemplateSetupSearch.vue';
    import FormTemplateSetupList from './FormTemplateSetupList.vue';
    import FormTemplateSetupEntry from './FormTemplateSetupEntry.vue';
    
    const composer = useFormTemplateSetupComposer();

    onMounted(async () => {
        console.log("### FormTemplateSetup.onMounted ###");
        composer.init();
    });

    onUnmounted(async () => {
        console.log("### FormTemplateSetup.onUnmounted ###");
        composer.destroy();
    });
</script>