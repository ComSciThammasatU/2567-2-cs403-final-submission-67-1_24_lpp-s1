<template>
    <section>
        <div class="error-page">
            <h2 class="headline text-warning">404</h2>
            <div class="error-content">
                <h3>
                    <i class="fas fa-exclamation-triangle text-warning"></i> Not Found
                </h3>
                <p class="px-4">
                    <h6>ไม่พบบริการที่ร้องขอ</h6>
                </p>
                <p class="px-4">
                    <router-link :to="HomeRoute.getRoutePath()"><h6>กลับไปยังหน้าหลัก</h6></router-link>
                </p>
            </div>
        </div>
    </section>
</template>

<script setup lang="ts">
    import HomeRoute from '@/app/router/routes/workspace/home.route';
</script>