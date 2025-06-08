<template>
    <div class="card my-2">

        <!-- card-header -->
        <div class="card-header">
            <h3 class="card-title">
                <i class="ion ion-clipboard mr-1"></i>
                รอดำเนินการ
            </h3>
        </div>
        <!-- /.card-header -->

        <!-- card-body -->
        <div class="card-body">
            <ul class="todo-list" data-widget="todo-list">
                <template v-for="formTemplateInfo in state.formTemplateInfos">
                    <li @click="edit(formTemplateInfo)">
                        <span class="text">{{ formTemplateInfo.formTemplate?.name }}</span>
                        <small class="badge badge-info">
                            <i class="far fa-clock"></i> 
                            {{ formTemplateInfo.formTemplate?.createdAt }}
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
    import formTemplateService from '../../services/form_template.service';
    import type { FormTemplateInfo } from '../../models/form-template-info.model';
    import { StatusType } from '../../models/http.response-message.model';
    import { useRouter } from 'vue-router';

    const router = useRouter();

    const state = reactive({
        formTemplateInfos: [] as FormTemplateInfo[]
    });

    onMounted(async () => {
        console.log("### DocumentHistory.onMounted ###");

        try {
            const response = await formTemplateService.loadToDoList({});
            console.log(response);

            if(response.head.status.type === StatusType.Success) {
                state.formTemplateInfos = response.body?.formTemplateInfos || [];
            }
        } catch(error: any) {
            console.error(error);
            alert("ERROR : " + error.message);
        }
    });

    function edit(formTemplateInfo: FormTemplateInfo)
    {
        router.push({
            path: '/survey/form',
            query: {
                formTemplateInfo: JSON.stringify(formTemplateInfo)
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