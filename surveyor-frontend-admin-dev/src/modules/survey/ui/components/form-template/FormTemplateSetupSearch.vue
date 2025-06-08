<template>
    <SearchCriteriaContainer @reset-criteria-request="composer.resetSearchCriteria()" @search-request="composer.search()">
        <template v-slot:body>
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Template Name</label>
                        <input 
                            type="text" 
                            class="form-control" 
                            placeholder="ค้นหาด้วยชื่อ" 
                            v-model="composer.state.searchCriteria.templateName">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Template Group</label>
                        <select class="form-control" v-model="composer.state.searchCriteria.templateGroupId">
                            <option value="" selected>--- All ---</option>
                            <template v-for="(templateGroup, index) in composer.state.formTemplateGroups">
                                <option :value="templateGroup.id">{{ templateGroup.name }}</option>
                            </template>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Template Status</label>
                        <select class="form-control" v-model="composer.state.searchCriteria.templateStatus">
                            <option value="" selected>--- All ---</option>
                            <template v-for="(templateStatus, index) in composer.state.templateStatuses">
                                <option :value="templateStatus">{{ templateStatus }}</option>
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
    import { useFormTemplateSetupComposer } from './form-template-setup.composer';

    const composer = useFormTemplateSetupComposer();
</script>