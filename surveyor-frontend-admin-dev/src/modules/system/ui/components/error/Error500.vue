<template>
    <section>
        <div class="error-page">
            <h2 class="headline text-danger">500</h2>
            <div class="error-content">
                <h3>
                    <i class="fas fa-exclamation-triangle text-danger"></i> Exception Occured
                </h3>
                <p class="px-4">
                    <h6>เกิดข้อผิดพลาด, ไม่สามารถดำเนินการตามที่คุณร้องขอ กรุณาติดต่อผู้ดูแลระบบ</h6>
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