<template>
    <SearchResultContainer>
        <template v-slot:header-tools>
            <div class="card-tools">
                <div class="input-group input-group-sm py-1">
                    &nbsp;
                    <CreateActionButton @click="addHandler()" />
                </div>
            </div>
        </template>

        <template v-slot:body>
            <!-- <template> -->
                <div class="table-responsive">
                    <!-- text-nowrap -->
                    <table class="table table-hover text-nowrap">
                        <thead>
                            <tr>
                                <th class="align-middle text-center">
                                    #
                                </th>
                                <th class="align-middle text-center">
                                    Code
                                </th>
                                <th class="align-middle text-center">
                                    Revision
                                </th>
                                <th class="align-middle text-center">
                                    Name
                                </th>
                                <th class="align-middle text-center">
                                    Group
                                </th>
                                <!-- <th class="align-middle text-center">
                                    Created By
                                </th> -->
                                <th class="align-middle text-center">
                                    Template Status
                                </th>
                                <th class="align-middle text-center">
                                    Action
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(formTemplateInfo, index) in composer.state.formTemplateInfos">
                                <td class="align-middle text-center">{{ index + 1 }}.</td>
                                <td class="align-middle text-center">{{ formTemplateInfo.formTemplate.code }}</td>
                                <td class="align-middle text-center">{{ formTemplateInfo.formTemplate.revision }}</td>
                                <td class="align-middle text-center">{{ formTemplateInfo.formTemplate.name }}</td>
                                <td class="align-middle text-center">{{ composer.getFormTemplateGroupName(formTemplateInfo.formTemplate) }}</td>
                                <!-- <td class="align-middle text-center">{{ formTemplateInfo.formTemplate.createdBy }}</td> -->
                                <td class="align-middle text-center">{{ formTemplateInfo.formTemplate.templateStatus }}</td>
                                <td class="align-middle text-center">
                                    <UpdateActionButton @click="editHandler(formTemplateInfo)" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            <!-- </template> -->
        </template>
    </SearchResultContainer>
</template>

<script setup lang="ts">
    import CreateActionButton from '@/modules/common/ui/widgets/common/form/button/CreateActionButton.vue';
    import SearchResultContainer from '@/modules/common/ui/widgets/container/SearchResultContainer.vue';
    import UpdateActionButton from '@/modules/common/ui/widgets/common/form/button/UpdateActionButton.vue';
    import { useFormTemplateSetupComposer } from './form-template-setup.composer';
    import { FormTemplateInfo } from '@/modules/survey/model/form-template-info.model';

    const composer = useFormTemplateSetupComposer();

    function addHandler() {
        composer.toAddEntryMode();
        composer.resetForm();
    }

    function editHandler(formTemplateInfo: FormTemplateInfo) {
        composer.toEditEntryMode();

        composer.state.templateForm = {
            templateId: formTemplateInfo.formTemplate?.id,
            templateGroupId: formTemplateInfo.formTemplate?.templateGroupId,
            templateCode: formTemplateInfo.formTemplate?.code,
            templateName: formTemplateInfo.formTemplate?.name,
            templateDescription: formTemplateInfo.formTemplate?.description,
            remark: formTemplateInfo.formTemplate?.remark,
            templateStatus: formTemplateInfo.formTemplate?.templateStatus,
            status: formTemplateInfo.formTemplate?.status,
            userGroupIds: formTemplateInfo.formTemplateAuthorities?.map(e => e.principalRefId),
        };

        if(formTemplateInfo.formTemplate?.id) {
            sessionStorage.setItem("form_template_id", formTemplateInfo.formTemplate?.id);

            if(formTemplateInfo.formTemplateElements) {
                let elementDatas = [];
                for(let i=0; i<formTemplateInfo.formTemplateElements.length; i++) {
                    let formTemplateElement = formTemplateInfo.formTemplateElements[i];
                    if(formTemplateElement.elementData) {
                        elementDatas.push(formTemplateElement.elementData);
                    }
                }

                if(elementDatas) {
                    sessionStorage.setItem('form_template_element_data', JSON.stringify(elementDatas));
                }
            }
        }
    }
</script>