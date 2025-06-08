<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" class="form-control" placeholder="Enter username" v-model="composer.state.staffInfoForm.username">
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>Email</label>
                    <input type="text" class="form-control" placeholder="Enter email" v-model="composer.state.staffInfoForm.email">
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>Staff Name</label>
                    <input type="text" class="form-control" placeholder="Enter staff name" v-model="composer.state.staffInfoForm.accountName">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>Roles</label>

                    <template v-for="(role, index) in composer.state.roles">
                        <div class="form-check">
                            <input 
                                class="form-check-input" 
                                style="width: 16px; height: 16px;"
                                type="checkbox" 
                                :id="'role' + index" 
                                :value="role.id" 
                                v-model="composer.state.staffInfoForm.roleIds">
                            <label class="form-check-label text-md" :for="'userGroup' + index">
                                {{ role.name }}
                            </label>
                        </div>
                    </template>
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>User Groups</label>

                    <template v-for="(userGroup, index) in composer.state.userGroups">
                        <div class="form-check">
                            <input 
                                class="form-check-input" 
                                style="width: 16px; height: 16px;"
                                type="checkbox" 
                                :id="'userGroup' + index" 
                                :value="userGroup.id" 
                                v-model="composer.state.staffInfoForm.userGroupIds">
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
                <SaveButton @click="composer.save()" />
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import BackButton from '@/modules/common/ui/widgets/common/form/button/BackButton.vue';
    import SaveButton from '@/modules/common/ui/widgets/common/form/button/SaveButton.vue';
    import { useStaffSetupComposer } from './staff-setup.composer';

    const composer = useStaffSetupComposer();

    function goBack() {
        composer.toListMode();
    }
    
</script>