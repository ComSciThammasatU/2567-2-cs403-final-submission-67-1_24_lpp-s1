<template>
    <nav id="main-navbar" class="main-header navbar navbar-expand navbar-light bg-maroon border-bottom-0 text-sm">
        <!-- Left navbar links -->
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" data-widget="pushmenu" href="#" role="button">
                    <i class="fas fa-bars" style="font-size: 1.2em;"></i>
                </a>
            </li>

            <router-link to="/" class="nav-link" title="หน้าหลัก">
                <i class="fas fa-home" style="font-size: 1.2em;"></i>
            </router-link>
        </ul>

        <!-- Right navbar links -->
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="javascript:void(0)" role="button" @click="logout()">
                    <i class="fas fa-sign-out-alt" style="font-size: 1.2em;"></i>
                </a>
            </li>
        </ul>
    </nav>
</template>

<script setup lang="ts">
    import authService from '../../services/auth.service';
    import { useRouter } from 'vue-router';

    const router = useRouter();

    async function logout()
    {
        console.log("logout");

        try {
            const response = await authService.logout({});
            console.log(response);
        } catch(error: any) {
            console.log(error);
        } finally {
            authService.clearSession();
            router.push('/login');
        }
    }
</script>