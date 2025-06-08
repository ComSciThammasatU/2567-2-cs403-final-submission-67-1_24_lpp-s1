<template>
    <div class="card my-2">

        <!-- card-header -->
        <div class="card-header">
            <h3 class="card-title">
                <i class="ion ion-clipboard mr-1"></i>
                ประวัติแบบสอบถาม
            </h3>
        </div>
        <!-- /.card-header -->

        <!-- card-body -->
        <div class="card-body">
            <ul class="todo-list" data-widget="todo-list">
                <template v-for="formDocumentInfo in state.formDocumentInfos">
                    <li @click="view(formDocumentInfo)">
                        <span class="text">{{ formDocumentInfo.formTemplateInfo?.formTemplate?.name }}</span>
                        <small class="badge badge-info">
                            <i class="far fa-clock"></i> 
                            {{ formDocumentInfo.formDocument.createdAt }}
                        </small>
                    </li>
                </template>
            </ul>
        </div>
        <!-- /.card-body -->

        <div class="card-footer clearfix px-2">
            <button type="button" class="btn btn-warning float-right text" @click="back()">
                <i class="fas fa-chevron-left"></i> Back
            </button>
        </div>
        
    </div>
</template>

<script setup lang="ts">
    import { onMounted, reactive } from 'vue';
    import formDocumentService from '../../services/form_document.service';
    import type { FormDocumentInfo } from '../../models/form-document-info.model';
    import { StatusType } from '../../models/http.response-message.model';
    import { useRouter } from 'vue-router';

    const router = useRouter();

    const state = reactive({
        formDocumentInfos: [] as FormDocumentInfo[]
    });

    onMounted(async () => {
        console.log("### DocumentHistory.onMounted ###");

        try {
            const response = await formDocumentService.loadUserHistoryDocuments({});
            console.log(response);

            if(response.head.status.type === StatusType.Success) {
                state.formDocumentInfos = response.body?.formDocumentInfos || [];
            }
        } catch(error: any) {
            console.error(error);
            alert("ERROR : " + error.message);
        }
    });

    function view(formDocumentInfo: FormDocumentInfo)
    {
        router.push({
            path: '/survey/info',
            query: {
                formDocumentInfo: JSON.stringify(formDocumentInfo)
            }
        });
    }

    function back()
    {
        router.back();
    }
</script>

<style scoped>

</style>