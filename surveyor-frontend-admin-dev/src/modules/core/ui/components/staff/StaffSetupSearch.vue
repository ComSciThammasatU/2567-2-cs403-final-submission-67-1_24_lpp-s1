<template>
    <SearchCriteriaContainer @reset-criteria-request="composer.resetSearchCriteria()" @search-request="composer.search()">
        <template v-slot:body>
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>ชื่อ</label>
                        <input 
                            type="text" 
                            class="form-control" 
                            placeholder="ค้นหาด้วยชื่อ" 
                            v-model="composer.state.searchCriteria.staffName">
                    </div>
                </div>
            </div>
        </template>
    </SearchCriteriaContainer>
</template>

<script setup lang="ts">
    import SearchCriteriaContainer from '@/modules/common/ui/widgets/container/SearchCriteriaContainer.vue';
    import { useStaffSetupComposer } from './staff-setup.composer';

    const composer = useStaffSetupComposer();
</script>