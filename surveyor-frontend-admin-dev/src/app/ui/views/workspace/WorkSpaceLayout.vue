<template>
    <div class="wrapper">
        <NavBar />

        <SideBar />

        <div class="content-wrapper">
            <Header />

            <!-- Main content -->
            <section class="content">
                <div class="container-fluid">
                    <router-view></router-view>
                </div>
            </section>
            <!-- /.content -->
        </div>

        <Footer />
    </div>
</template>

<script setup lang="ts">
    import NavBar from './fragment/NavBar.vue';
    import SideBar from './fragment/SideBar.vue';
    import Header from './fragment/Header.vue';
    import Footer from './fragment/Footer.vue';
    import { onMounted, onUnmounted } from 'vue';

    onMounted(async () => {
        console.info("### WorkSpaceLayout.onMounted ###");

        document.body.classList.remove("hold-transition", "login-page");
    });

    onUnmounted(() => {
        console.info("### WorkSpaceLayout.onUnmounted ###");
    });
</script>