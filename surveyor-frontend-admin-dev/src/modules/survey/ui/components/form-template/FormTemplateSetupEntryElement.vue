<template>
    <div>
        <!-- <button @click="sendMessageToIframe('sayHello', {'text': 'YOH!', no: 1})">Send Message</button> -->
        <iframe ref="iframeRef" src="pages/form_element_setting.html" width="100%" height="600px" frameborder="0" class="mt-2"></iframe>
    </div>
</template>

<script setup lang="ts">
    import { onMounted, onUnmounted, ref } from 'vue';
    import { useFormTemplateSetupComposer } from './form-template-setup.composer';
    
    const composer = useFormTemplateSetupComposer();
    const iframeRef = ref<HTMLIFrameElement | null>(null);

    onMounted(() => {
        console.log("### FormTemplateSetupEntryElement.onMounted ###");

        window.addEventListener('message', handleMessageFromIframe);
    });

    onUnmounted(async () => {
        console.log("### FormTemplateSetupEntryElement.onUnmounted ###");

        window.removeEventListener('message', handleMessageFromIframe);
    });

    function sendMessageToIframe(action: string, payload: any) {
        if (iframeRef.value?.contentWindow) {
            iframeRef.value.contentWindow.postMessage({action, payload}, '*');
            console.log('Message sent to iframe');
        }
    }

    function handleMessageFromIframe(event: MessageEvent) {
        console.log('Received message from iframe:', event.data);

        const { action, payload } = event.data || {};

        if(action === "submit") {
            console.log("submit success");
            composer.search();
            composer.toListMode();
        }
    }
</script>