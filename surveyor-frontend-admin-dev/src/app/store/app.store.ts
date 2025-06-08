import { computed, reactive } from "vue";
import { defineStore } from 'pinia';

export const APP_STORE_ID = "app";

export const useAppStore = defineStore(APP_STORE_ID, () => {
    const state = reactive({
        loading: false
    });

    const loading = computed(() => state.loading);

    const startLoading = () => {
        state.loading = true;
    };

    const stopLoading = () => {
        state.loading = false;
    };
  
    return { state, loading, startLoading, stopLoading }
});