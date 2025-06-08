<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" class="form-control" placeholder="Enter username" v-model="composer.state.studentInfoForm.username">
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>Email</label>
                    <input type="text" class="form-control" placeholder="Enter email" v-model="composer.state.studentInfoForm.email">
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>รหัส นศ</label>
                    <input type="text" class="form-control" placeholder="Enter Student Code" v-model="composer.state.studentInfoForm.studentCode">
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>ชื่อ นศ</label>
                    <input type="text" class="form-control" placeholder="Enter Student name" v-model="composer.state.studentInfoForm.accountName">
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>ระดับ</label>
                    <select class="form-control select2bs4" style="width: 100%;" v-model="composer.state.studentInfoForm.studyDegreeId">
                        <option value="" selected disabled>--- เลือกระดับการศึกษา ---</option>
                        <option value="Bachelor">Bachelor</option>
                        <option value="Master">Master</option>
                        <option value="Doctor">Doctor</option>
                    </select>
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label>ชั้นปี</label>
                    <input type="text" class="form-control" placeholder="Enter Student name" v-model="composer.state.studentInfoForm.studyClassLevel">
                </div>
            </div>
        </div>

        <div class="row">
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
                                v-model="composer.state.studentInfoForm.userGroupIds">
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
    import { useStudentSetupComposer } from './student-setup.composer';

    const composer = useStudentSetupComposer();

    function goBack() {
        composer.toListMode();
    }
    
</script>