<template>
	<PageLoader v-show="loading" />
	
	<router-view></router-view>
</template>

<script setup lang="ts">
	import { computed } from 'vue';
	import PageLoader from '@/modules/common/ui/widgets/common/PageLoader.vue';
	import { useAppStore } from '@/app/store/app.store';

	const appStore = useAppStore();

	const loading = computed(() => appStore.loading);
</script>

<style>
	@import url('@/assets/css/style.css');
	/* @import url('@/assets/css/theme/fms-genesis.css'); */
</style>