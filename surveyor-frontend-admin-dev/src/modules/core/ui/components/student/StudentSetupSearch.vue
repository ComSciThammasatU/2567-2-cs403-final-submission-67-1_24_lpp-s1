<template>
    <SearchCriteriaContainer @reset-criteria-request="composer.resetSearchCriteria()" @search-request="composer.search()">
        <template v-slot:body>
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>รหัส นศ</label>
                        <input 
                            type="text" 
                            class="form-control" 
                            placeholder="ค้นหาด้วยรหัส นศ" 
                            v-model="composer.state.searchCriteria.studentCode">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>ชื่อ นศ</label>
                        <input 
                            type="text" 
                            class="form-control" 
                            placeholder="ค้นหาด้วยชื่อ นศ" 
                            v-model="composer.state.searchCriteria.studentName">
                    </div>
                </div>
            </div>
        </template>
    </SearchCriteriaContainer>
</template>

<script setup lang="ts">
    import SearchCriteriaContainer from '@/modules/common/ui/widgets/container/SearchCriteriaContainer.vue';
    import { useStudentSetupComposer } from './student-setup.composer';

    const composer = useStudentSetupComposer();
</script>