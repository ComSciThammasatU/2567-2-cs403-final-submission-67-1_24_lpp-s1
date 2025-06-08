<template>
    <section>
        <template v-if="composer.state.mode === 'list'">
            <FormDocumentSearch />
            <FormDocumentList />
        </template>
        <template v-else-if="composer.state.mode === 'info'">
            <FormDocumentInfo />
        </template>
    </section>
</template>

<script setup lang="ts">
    import { onMounted, onUnmounted } from 'vue';
    import { useFormDocumentComposer } from './form-document.composer';
    import FormDocumentSearch from './FormDocumentSearch.vue';
    import FormDocumentList from './FormDocumentList.vue';
    import FormDocumentInfo from './FormDocumentInfo.vue';

    const composer = useFormDocumentComposer();

    onMounted(async () => {
        console.log("### FormDocument.onMounted ###");
        composer.init();
    });

    onUnmounted(async () => {
        console.log("### FormDocument.onUnmounted ###");
        composer.destroy();
    });
</script>