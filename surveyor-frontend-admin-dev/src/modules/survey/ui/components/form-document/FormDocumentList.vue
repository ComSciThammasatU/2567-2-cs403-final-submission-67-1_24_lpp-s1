<template>
    <SearchResultContainer>
        <template v-slot:body>
            <div class="table-responsive">
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
                                Survey Name
                            </th>
                            <th class="align-middle text-center">
                                Submitted User
                            </th>
                            <th class="align-middle text-center">
                                Submitted At
                            </th>
                            <th class="align-middle text-center">
                                Document Status
                            </th>
                            <th class="align-middle text-center">
                                Action
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(formDocumentInfo, index) in composer.state.formDocumentInfos">
                            <td class="align-middle text-center">{{ index + 1 }}.</td>
                            <td class="align-middle text-center">{{ formDocumentInfo.formTemplateInfo.formTemplate.code }}</td>
                            <td class="align-middle text-center">{{ formDocumentInfo.formTemplateInfo.formTemplate.revision }}</td>
                            <td class="align-middle text-center">{{ formDocumentInfo.formTemplateInfo.formTemplate.name }}</td>
                            <td class="align-middle text-center">{{ formDocumentInfo.submittedUser.accountName }}</td>
                            <td class="align-middle text-center">{{ formDocumentInfo.formDocument.submittedAt }}</td>
                            <td class="align-middle text-center">{{ formDocumentInfo.formDocument.documentStatus }}</td>
                            <td class="align-middle text-center">
                                <ViewActionButton @click="infoHandler(formDocumentInfo)" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </template>
    </SearchResultContainer>
</template>

<script setup lang="ts">
    import SearchResultContainer from '@/modules/common/ui/widgets/container/SearchResultContainer.vue';
    import ViewActionButton from '@/modules/common/ui/widgets/common/form/button/ViewActionButton.vue';
    import { useFormDocumentComposer } from './form-document.composer';
    import { FormDocumentInfo } from '@/modules/survey/model/form-document-info.model';

    const composer = useFormDocumentComposer();

    function infoHandler(formDocumentInfo: FormDocumentInfo) {
        composer.toInfoMode();
        composer.state.selectedFormDocumentInfo = formDocumentInfo;
    }
</script>