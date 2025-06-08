<template>
    <section>
        <div class="row">
            <div class="col-md-12 text-center align-middle" style="margin-top: 17%;">
                <img :src="AssetLoader.getAppLogo3xImageURL()" alt="" style="height: 50%;">
            </div>
        </div>
    </section>
</template>

<script lang="ts" setup>
    // @ts-nocheck

    import AssetLoader from '@/app/util/asset-loader';
</script>