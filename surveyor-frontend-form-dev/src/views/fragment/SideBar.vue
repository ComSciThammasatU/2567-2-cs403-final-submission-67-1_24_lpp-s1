<template>
    <aside class="main-sidebar sidebar-dark-primary elevation-4 text-sm">
        <!-- Brand Logo -->
        <a href="index3.html" class="brand-link">
            <img src="/favicon.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
            <span class="brand-text font-weight-light">TU Surveyor Form</span>
        </a>

        <!-- Sidebar -->
        <div class="sidebar">
            <!-- Sidebar user panel (optional) -->
            <div class="user-panel mt-3 pb-3 mb-3 d-flex">
                <div class="info">
                    <a href="#" class="d-block">{{ state.accountName }}</a>
                </div>
            </div>

            <!-- SidebarSearch Form -->
            <div class="form-inline">
                
            </div>
        </div>
    </aside>

</template>

<script setup lang="ts">
    import { onMounted, reactive } from 'vue';

    const state = reactive({
        accountName: ''
    });

    onMounted(() => {
        state.accountName = localStorage.getItem("account_name") || '';
    });
</script>