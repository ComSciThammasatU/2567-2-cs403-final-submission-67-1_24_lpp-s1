<template>
    <SearchCriteriaContainer @reset-criteria-request="composer.resetSearchCriteria()" @search-request="composer.search()">
        <template v-slot:body>
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Template</label>
                        <select class="form-control" v-model="composer.state.searchCriteria.templateId">
                            <option value="" selected disabled>--- Select Template ---</option>

                            <template v-for="(templateGroup, index) in composer.state.formTemplateGroups">
                                <optgroup :label="templateGroup.name">
                                    <template v-for="(template, index) in composer.state.formTemplates">
                                        <option :value="template.id">{{ template.name }}</option>
                                    </template>
                                </optgroup>
                            </template>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Document Status</label>
                        <select class="form-control" v-model="composer.state.searchCriteria.documentStatus">
                            <!-- <option value="" selected>--- All ---</option> -->
                            <option value="" selected disabled>--- Select Status ---</option>
                            
                            <template v-for="(documentStatus, index) in composer.state.documentStatuses">
                                <option :value="documentStatus">{{ documentStatus }}</option>
                            </template>
                        </select>
                    </div>
                </div>
            </div>
        </template>
    </SearchCriteriaContainer>
</template>

<script setup lang="ts">
    import SearchCriteriaContainer from '@/modules/common/ui/widgets/container/SearchCriteriaContainer.vue';
    import { useFormDocumentComposer } from './form-document.composer';

    const composer = useFormDocumentComposer();
</script>