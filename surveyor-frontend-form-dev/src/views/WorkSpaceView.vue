<template>
	<div class="wrapper">
        <NavBar />
		<SideBar />

        <div class="content-wrapper">
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
	import Footer from './fragment/Footer.vue';
</script>

<style scoped>
.logo {
	height: 6em;
	padding: 1.5em;
	will-change: filter;
	transition: filter 300ms;
}

.logo:hover {
	filter: drop-shadow(0 0 2em #646cffaa);
}

.logo.vue:hover {
	filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
