<template>
    <SearchResultContainer>
        <template v-slot:header-tools>
            <div v-if="false" class="card-tools">
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
                                    Username
                                </th>
                                <th class="align-middle text-center">
                                    <span>Email</span>
                                </th>
                                <th class="align-middle text-center">
                                    รหัส นศ
                                </th>
                                <th class="align-middle text-center">
                                    ชื่อ นศ
                                </th>
                                <th class="align-middle text-center">
                                    ระดับ
                                </th>
                                <th class="align-middle text-center">
                                    ชั้นปี
                                </th>
                                <th class="align-middle text-center">
                                    Action
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(studentInfo, index) in composer.state.studentInfos">
                                <td class="align-middle text-center">{{ index + 1 }}.</td>
                                <td class="align-middle text-center">{{ studentInfo.user.username }}</td>
                                <td class="align-middle text-center">{{ studentInfo.user.email }}</td>
                                <td class="align-middle text-center">{{ studentInfo.student.studentCode }}</td>
                                <td class="align-middle text-center">{{ studentInfo.user.accountName }}</td>
                                <td class="align-middle text-center">{{ studentInfo.student.degreeId }}</td>
                                <td class="align-middle text-center">{{ studentInfo.student.classLevel }}</td>
                                <td class="align-middle text-center">
                                    <UpdateActionButton @click="editHandler(studentInfo)" />
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
    import { useStudentSetupComposer } from './student-setup.composer';
    import { StudentInfo } from '@/modules/core/model/student-info.model';

    const composer = useStudentSetupComposer();

    function addHandler() {
        composer.toAddEntryMode();
        composer.resetForm();
    }

    function editHandler(studentInfo: StudentInfo) {
        console.log("### editHandler ###");

        if(! studentInfo.userGroups) {
            studentInfo.userGroups = [];
        }

        console.log(studentInfo);

        composer.toEditEntryMode();

        composer.state.studentInfoForm = {
            studentId: studentInfo.student.id,
            studentCode: studentInfo.student.studentCode,
            studyDegreeId: studentInfo.student.degreeId,
            studyClassLevel: studentInfo.student.classLevel,
            userId: studentInfo.user.id,
            username: studentInfo.user.username,
            email: studentInfo.user.email,
            accountName: studentInfo.user.accountName,
            userGroupIds: studentInfo.userGroups.map(e => e.id),
            status: studentInfo.user.status
        };
    }
</script>