<template>
    <div class="card my-2">

        <!-- card-header -->
        <div class="card-header">
            <h3 class="card-title text-maroon">
                รายละเอียดข้อมูลแบบสอบถาม
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
                                {{ state.formDocumentInfo?.formTemplateInfo?.formTemplate?.name }}
                            </span>
                        </label>
                    </div>

                    <div class="form-group">
                        <label>โค้ดแบบสอบถาม : 
                            <span class="text-muted">
                                {{ state.formDocumentInfo?.formTemplateInfo?.formTemplate?.code }}
                            </span>
                        </label>
                    </div>

                    <div class="form-group">
                        <label>เวอร์ชันแบบสอบถาม : 
                            <span class="text-muted">
                                {{ state.formDocumentInfo?.formTemplateInfo?.formTemplate?.revision }}
                            </span>
                        </label>
                    </div>

                    <div class="form-group">
                        <label>วันที่ส่งแบบสอบถาม : 
                            <span class="text-muted">
                                {{ state.formDocumentInfo?.formDocument?.createdAt }}
                            </span>
                        </label>
                    </div>
                </div>
            </form>

            <hr>

            <iframe ref="iframeRef" src="/pages/document_form.html" width="100%" height="360px" frameborder="0" class="mt-2"></iframe>
        </div>
        <!-- /.card-body -->

        <!-- card-footer -->
        <div class="card-footer clearfix px-2">
            <button type="button" class="btn btn-warning float-right text mr-2" @click="back()">
                <i class="fas fa-chevron-left"></i> Back
            </button>
        </div>
        <!-- /.card-footer -->

    </div>

</template>

<script setup lang="ts">
    import { onMounted, onUnmounted, ref, reactive } from 'vue';
    import { useRoute, useRouter } from 'vue-router';
    import type { FormDocumentInfo } from '../../models/form-document-info.model';

    const route = useRoute();
    const router = useRouter();

    const iframeRef = ref<HTMLIFrameElement | null>(null);

    const state = reactive({
        formDocumentInfo: undefined as FormDocumentInfo | undefined
    });

    onMounted(() => {
        console.log("### DocumentInfo.onMounted ###");

        clearSession();
        loadFormDocumentInfo();
        setupSession();

        console.log(state);
    });

    onUnmounted(() => {
        console.log("### DocumentInfo.onUnmounted ###");

        clearSession();
    });

    function loadFormDocumentInfo()
    {
        if(route.query.formDocumentInfo) {
            state.formDocumentInfo = JSON.parse(route.query.formDocumentInfo as string);
        }
    }

    function clearSession()
    {
        sessionStorage.removeItem("form_template_element_data");
    }

    function setupSession()
    {
        const elementDatas = [];

        if(state.formDocumentInfo?.formTemplateInfo?.formTemplateElements) {
            for(let i=0; i<state.formDocumentInfo?.formTemplateInfo?.formTemplateElements.length; i++ ) {
                let formTemplateElement = state.formDocumentInfo?.formTemplateInfo?.formTemplateElements[i];
                let elementData = formTemplateElement.elementData;
                if(elementData) {
                    if(state.formDocumentInfo.formDocumentElements) {
                        let formDocumentElement = state.formDocumentInfo.formDocumentElements.find(e => e.templateElementId === formTemplateElement.id);
                        if(formDocumentElement) {
                            elementData.disabled = true;
                            elementData.value = formDocumentElement.elementValue;
                        }
                    }

                    elementDatas.push(elementData);
                }
            }
        }

        sessionStorage.setItem("form_template_element_data", JSON.stringify(elementDatas));
    }

    function back()
    {
        console.log("### DocumentForm.back ###");
        router.back();
    }
</script>