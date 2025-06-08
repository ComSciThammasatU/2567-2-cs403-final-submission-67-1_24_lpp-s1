<template>
    <div class="container-fluid">

        <div class="row">
            <div class="col-md-5">
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

                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>ชื่อแบบสอบถาม : 
                                        <span class="text-muted">
                                            {{ composer.state.selectedFormDocumentInfo?.formTemplateInfo?.formTemplate?.name }}
                                        </span>
                                    </label>
                                </div>
                            </div>

                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Template Code : 
                                        <span class="text-muted">
                                            {{ composer.state.selectedFormDocumentInfo?.formTemplateInfo?.formTemplate?.code }} (Rev. {{ composer.state.selectedFormDocumentInfo?.formTemplateInfo?.formTemplate?.revision }})
                                        </span>
                                    </label>
                                </div>
                            </div>

                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>ผู้ทำแบบสอบถาม : 
                                        <span class="text-muted">
                                            {{ composer.state.selectedFormDocumentInfo?.submittedUser?.accountName }}
                                        </span>
                                    </label>
                                </div>
                            </div>

                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>วันที่ส่งแบบสอบถาม : 
                                        <span class="text-muted">
                                            {{ composer.state.selectedFormDocumentInfo?.formDocument?.submittedAt }}
                                        </span>
                                    </label>
                                </div>
                            </div>

                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>สถานะแบบสอบถาม : 
                                        <span class="text-muted">
                                            {{ composer.state.selectedFormDocumentInfo?.formDocument?.documentStatus }}
                                        </span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.card-body -->
                </div>
            </div>

            <div class="col-md-7 py-0 mt-0 mb-2">
                <div class="card my-2">
                    <!-- card-header -->
                    <div class="card-header">
                        <h3 class="card-title text-maroon">
                            ข้อมูลแบบสอบถามที่ผู้ใช้ส่ง
                        </h3>
                    </div>
                    <!-- /.card-header -->

                    <!-- card-body -->
                    <div class="card-body">
                        <div class="row">
                            <iframe ref="iframeRef" src="pages/form_element_view.html" width="100%" height="600px" frameborder="0" class="mt-2"></iframe>
                        </div>
                    </div>
                    <!-- /.card-body -->
                </div>
            </div>
        </div>

        <div v-if="false" class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>ชื่อแบบสอบถาม : 
                        <span class="text-muted">
                            {{ composer.state.selectedFormDocumentInfo?.formTemplateInfo?.formTemplate?.name }}
                        </span>
                    </label>
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>Template Code : 
                        <span class="text-muted">
                            {{ composer.state.selectedFormDocumentInfo?.formTemplateInfo?.formTemplate?.code }} (Rev. {{ composer.state.selectedFormDocumentInfo?.formTemplateInfo?.formTemplate?.revision }})
                        </span>
                    </label>
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>ผู้ทำแบบสอบถาม : 
                        <span class="text-muted">
                            {{ composer.state.selectedFormDocumentInfo?.submittedUser?.accountName }}
                        </span>
                    </label>
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>วันที่ส่งแบบสอบถาม : 
                        <span class="text-muted">
                            {{ composer.state.selectedFormDocumentInfo?.formDocument?.submittedAt }}
                        </span>
                    </label>
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>สถานะแบบสอบถาม : 
                        <span class="text-muted">
                            {{ composer.state.selectedFormDocumentInfo?.formDocument?.documentStatus }}
                        </span>
                    </label>
                </div>
            </div>
        </div>
    </div>

    <div class="card-footer">
        <div class="row text-right">
            <div class="offset-sm-2 col-sm-10">
                <BackButton @click="goBack()" />
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { onMounted, onUnmounted } from 'vue';
    import { useFormDocumentComposer } from './form-document.composer';
    import BackButton from '@/modules/common/ui/widgets/common/form/button/BackButton.vue';

    const composer = useFormDocumentComposer();

    onMounted(() => {
        const elementDatas = [];

        const formDocumentInfo = composer.state.selectedFormDocumentInfo;

        if(formDocumentInfo?.formTemplateInfo?.formTemplateElements) {
            for(let i=0; i<formDocumentInfo?.formTemplateInfo?.formTemplateElements.length; i++ ) {
                let formTemplateElement = formDocumentInfo?.formTemplateInfo?.formTemplateElements[i];
                let elementData = formTemplateElement.elementData;
                if(elementData) {
                    if(formDocumentInfo.formDocumentElements) {
                        let formDocumentElement = formDocumentInfo.formDocumentElements.find((e: any) => e.templateElementId === formTemplateElement.id);
                        if(formDocumentElement) {
                            elementData.disabled = true;
                            // elementData.className += ` row-${(i+1)} col-sm-6 col-md-4 col-lg-4`;
                            elementData.value = formDocumentElement.elementValue;
                        }
                    }

                    elementDatas.push(elementData);
                }
            }
        }

        sessionStorage.setItem("form_document_element_data", JSON.stringify(elementDatas));
    });

    onUnmounted(() => {
        sessionStorage.removeItem("form_document_element_data");
    });

    function goBack() {
        composer.toListMode();
    }
    
</script>