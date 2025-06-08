<template>
	<div class="container-fluid">
		<div class="row mt-2 mb-4">
			<div class="col-12 text-center">
				<a href="#" class="h3 text-navy"><b class="text-maroon">TU Surveyor</b> Form</a>
			</div>
		</div>
		
		<div class="row">
			
			<div class="col-lg-3 col-md-6">
				<!-- small box -->
				<div class="small-box bg-warning">
					<div class="inner">
						<h3>{{ state.formTemplateInfos.length }}</h3>
						<p>รอดำเนินการ</p>
					</div>
					<div class="icon">
						<i class="ion ion-stats-bars"></i>
					</div>
					<!-- <a href="#" class="small-box-footer">รายละเอียด <i class="fas fa-arrow-circle-right"></i></a> -->
					<router-link to="/survey/to-do-list" class="small-box-footer">
						รายละเอียด <i class="fas fa-arrow-circle-right"></i>
					</router-link>
				</div>
			</div>

			<div class="col-lg-3 col-md-6">
				<!-- small box -->
				<div class="small-box bg-success">
					<div class="inner">
						<h3>{{ state.formDocumentInfos.length }}</h3>
						<p>ประวัติแบบสอบถาม</p>
					</div>
					<div class="icon">
						<i class="ion ion-bag"></i>
					</div>
					<!-- <a href="#" class="small-box-footer">รายละเอียด <i class="fas fa-arrow-circle-right"></i></a> -->
					<router-link to="/survey/history" class="small-box-footer">
						รายละเอียด <i class="fas fa-arrow-circle-right"></i>
					</router-link>
				</div>
			</div>

		</div>
	</div>
</template>

<script setup lang="ts">
	import { onMounted, reactive } from 'vue';
    import formTemplateService from '../services/form_template.service';
	import type { FormTemplateInfo } from '../models/form-template-info.model';
	import formDocumentService from '../services/form_document.service';
    import type { FormDocumentInfo } from '../models/form-document-info.model';
	import { StatusType } from '../models/http.response-message.model';

	const state = reactive({
        formTemplateInfos: [] as FormTemplateInfo[],
		formDocumentInfos: [] as FormDocumentInfo[]
    });

	onMounted(() => {
		console.log('### HomeView.onMounted ###');

		loadPendingDocuments();
		loadHistoryDocuments();
	});

	async function loadPendingDocuments()
	{
		console.log('### HomeView.loadPendingDocuments ###');

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
	}

	async function loadHistoryDocuments()
	{
		console.log('### HomeView.loadHistoryDocuments ###');

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
	}
</script>

<style scoped>
.logo {
	height: 6em;
	padding: 1.5em;
	will-change: filter;
	transition: filter 300ms;
}

.logo:hover {
	filter: drop-shadow(0 0 2em #646cffaa);
}

.logo.vue:hover {
	filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
