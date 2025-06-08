<template>
    <div :id="props.id"></div>
</template>

<script setup lang="ts">
    // @ts-nocheck
    import ResourceLoader from "@/app/util/resource-loader";
    import { QrcodOptions } from "@/modules/common/model/qrcode-options";
    import { onMounted, watch } from 'vue';

    const props = defineProps<{
        id: string,
        options?: QrcodOptions;
    }>();

    const generateQRCode = async (options) => {
        await ResourceLoader.loadScriptAsync("https://cdn.jsdelivr.net/npm/easyqrcodejs-arm@3.7.2/dist/easy.qrcode.min.js");
        const element = document.getElementById(props.id);
        if (element) {
            element.innerHTML = ""; // Clear the previous QR code
            new QRCode(element, options);
        }
    };

    onMounted(async () => {
        await generateQRCode(props.options);
    });

    watch(() => props.options, async (newOptions) => {
        await generateQRCode(newOptions);
    }, { deep: true });

</script>