<template>
    <div class="card my-2">

        <!-- card-header -->
        <div class="card-header">
            <h3 class="card-title text-maroon">
                ทำแบบสอบถาม
            </h3>
        </div>
        <!-- /.card-header -->

        <!-- card-body -->
        <div class="card-body">
            <form>
                <div class="card-body m-0 p-0 text-sm">
                    <div class="form-group">
                        <label>ชื่อแบบสอบถาม : 
                            <span class="text-muted">
                                {{ state.formTemplateInfo?.formTemplate?.name }}
                            </span>
                        </label>
                    </div>

                    <div class="form-group">
                        <label>โค้ดแบบสอบถาม : 
                            <span class="text-muted">
                                {{ state.formTemplateInfo?.formTemplate?.code }}
                            </span>
                        </label>
                    </div>

                    <div class="form-group">
                        <label>เวอร์ชันแบบสอบถาม : 
                            <span class="text-muted">
                                {{ state.formTemplateInfo?.formTemplate?.revision }}
                            </span>
                        </label>
                    </div>
                </div>
            </form>

            <hr>

            <iframe ref="iframeRef" src="/pages/document_form.html" width="100%" height="360px" frameborder="0" class="mt-2"></iframe>
        </div>
        <!-- /.card-body -->

        <div class="card-footer clearfix px-2">
            <button type="button" class="btn btn-primary float-right text" @click="requestSubmit()">
                <i class="fas fa-save"></i> Submit
            </button>
            
            <button type="button" class="btn btn-warning float-right text mr-2" @click="back()">
                <i class="fas fa-chevron-left"></i> Back
            </button>
        </div>

    </div>
</template>

<script setup lang="ts">
    import { onMounted, onUnmounted, ref, reactive } from 'vue';
    import { useRoute, useRouter } from 'vue-router';
    import formDocumentService from '../../services/form_document.service';
    import type { FormTemplateInfo } from '../../models/form-template-info.model';
    import { StatusType } from '../../models/http.response-message.model';

    const iframeRef = ref<HTMLIFrameElement | null>(null);

    const state = reactive({
        formTemplateInfo: undefined as FormTemplateInfo | undefined
    });

    const route = useRoute();
    const router = useRouter();

    onMounted(() => {
        console.log("### DocumentForm.onMounted ###");

        clearSession();
        loadFormTemplateInfo();
        setupSession();

        console.log(state);

        window.addEventListener('message', handleMessageFromIframe);
    });

    onUnmounted(() => {
        console.log("### DocumentForm.onUnmounted ###");

        clearSession();
    });

    function loadFormTemplateInfo()
    {
        if(route.query.formTemplateInfo) {
            state.formTemplateInfo = JSON.parse(route.query.formTemplateInfo as string);
        }
    }

    function clearSession()
    {
        sessionStorage.removeItem("form_template_element_data");
    }

    function setupSession()
    {
        const elementDatas = [];

        if(state.formTemplateInfo?.formTemplateElements) {
            for(let i=0; i<state.formTemplateInfo?.formTemplateElements.length; i++ ) {
                let elementData = state.formTemplateInfo?.formTemplateElements[i].elementData;
                if(elementData) {
                    elementDatas.push(elementData);
                }
            }
        }

        sessionStorage.setItem("form_template_element_data", JSON.stringify(elementDatas));
    }

    function handleMessageFromIframe(event: MessageEvent) {
        console.log('Received message from iframe:', event.data);

        const { action, payload } = event.data || {};

        console.log("action: ", action);
        console.log("payload: ", payload);

        if(action === "submit_confirm") {
            submit(payload.elementValues);
        }
    }

    function sendMessageToIframe(action: string, payload: any) {
        if (iframeRef.value?.contentWindow) {
            iframeRef.value.contentWindow.postMessage({action, payload}, '*');
            console.log('Message sent to iframe');
        }
    }

    function requestSubmit()
    {
        console.log("### DocumentForm.requestSubmit ###");
        sendMessageToIframe("submit_request", null);
    }

    function back()
    {
        console.log("### DocumentForm.back ###");
        router.back();
    }

    async function submit(elementValues: {name: string, value: string}[])
    {
        console.log("### DocumentForm.submit ###");

        type Element = {
            templateElementId: string;
            elementTypeId: string;
            elementValue: string;
            orderNo: number
        };

        let elements: Element[] = [];

        if(elementValues) {
            for(let i=0; i<elementValues.length; i++) {
                let elementValue = elementValues[i];
                let templateElement = state.formTemplateInfo?.formTemplateElements?.find(e => e.elementCode === elementValue.name);

                if(templateElement) {
                    let element: Element = {
                        templateElementId: templateElement.id || "",
                        elementTypeId: elementValue.name,
                        elementValue: elementValue.value,
                        orderNo: i+1
                    };

                    elements.push(element);
                }
            }
        }

        try {
            const response = await formDocumentService.submitForm({
                requestPayload: {
                    templateId: state.formTemplateInfo?.formTemplate?.id || "",
                    elements: elements
                }
            });
            console.log(response);

            if(response.head.status.type === StatusType.Success) {
                back();
            } else {
                alert("บันทึกข้อมูลไม่สำเร็จ เนื่องจาก : " + response.head.status.message);
            }
        } catch(error: any) {
            console.error(error);
            alert("ERROR : " + error.message);
        }
    }
</script>

<style scoped>

</style>