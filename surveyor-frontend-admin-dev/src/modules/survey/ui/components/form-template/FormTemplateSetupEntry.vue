<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>Template Group</label>
                    <select class="form-control" v-model="composer.state.templateForm.templateGroupId">
                        <option value="" selected disabled>--- Select Template Group ---</option>
                        <template v-for="(formTemplateGroup, index) in composer.state.formTemplateGroups">
                            <option :value="formTemplateGroup.id">{{ formTemplateGroup.name }}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>Template Code</label>
                    <input type="text" class="form-control" placeholder="Enter Template Code" v-model="composer.state.templateForm.templateCode">
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>Template Name</label>
                    <input type="text" class="form-control" placeholder="Enter Template Name" v-model="composer.state.templateForm.templateName">
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>Template Description</label>
                    <textarea 
                        class="form-control" 
                        rows="3" placeholder="Enter Template Description" 
                        v-model="composer.state.templateForm.templateDescription">
                    </textarea>
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>Template Status</label>
                    <select class="form-control" v-model="composer.state.templateForm.templateStatus">
                        <option value="" selected disabled>--- Select Template Status ---</option>
                        <template v-for="(templateStatus, index) in composer.state.templateStatuses">
                            <option :value="templateStatus">{{ templateStatus }}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="col-md-6" v-if="false">
                <div class="form-group">
                    <label>Template Status</label>
                    <select class="form-control" v-model="composer.state.templateForm.status">
                        <template v-for="(statusCode, index) in composer.state.statusCodes">
                            <option :value="statusCode">{{ statusCode }}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>User Group Authorities</label>

                    <template v-for="(userGroup, index) in composer.state.userGroups">
                        <div class="form-check">
                            <input 
                                class="form-check-input" 
                                style="width: 16px; height: 16px;"
                                type="checkbox" 
                                :id="'userGroup' + index" 
                                :value="userGroup.id" 
                                v-model="composer.state.templateForm.userGroupIds">
                            <label class="form-check-label text-md" :for="'userGroup' + index">
                                {{ userGroup.name }}
                            </label>
                        </div>
                    </template>
                </div>
            </div>
        </div>
    </div>

    <div class="card-footer">
        <div class="row text-right">
            <div class="offset-sm-2 col-sm-10">
                <BackButton @click="goBack()" />
                &nbsp;
                <SaveButton @click="save()" />
            </div>
        </div>
    </div>

    <FormTemplateSetupEntryElement />
</template>

<script setup lang="ts">
    import BackButton from '@/modules/common/ui/widgets/common/form/button/BackButton.vue';
    import SaveButton from '@/modules/common/ui/widgets/common/form/button/SaveButton.vue';
    import FormTemplateSetupEntryElement from './FormTemplateSetupEntryElement.vue';
    import { useFormTemplateSetupComposer } from './form-template-setup.composer';

    const composer = useFormTemplateSetupComposer();

    function goBack() {
        composer.toListMode();
    }

    function save() {
        composer.save();
    }
    
</script>