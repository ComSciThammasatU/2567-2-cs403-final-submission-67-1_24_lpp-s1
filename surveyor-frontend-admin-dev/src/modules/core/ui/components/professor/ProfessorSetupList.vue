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
                                    Username
                                </th>
                                <th class="align-middle text-center">
                                    <span>Email</span>
                                </th>
                                <th class="align-middle text-center">
                                    Professor Name
                                </th>
                                <th class="align-middle text-center">
                                    Action
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(professorInfo, index) in composer.state.professorInfos">
                                <td class="align-middle text-center">{{ index + 1 }}.</td>
                                <td class="align-middle text-center">{{ professorInfo.user.username }}</td>
                                <td class="align-middle text-center">{{ professorInfo.user.email }}</td>
                                <td class="align-middle text-center">{{ professorInfo.user.accountName }}</td>
                                <td class="align-middle text-center">
                                    <UpdateActionButton @click="editHandler(professorInfo)" />
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
    import { useProfessorSetupComposer } from './professor-setup.composer';
    import { ProfessorInfo } from '@/modules/core/model/professor-info.model';

    const composer = useProfessorSetupComposer();

    function addHandler() {
        composer.toAddEntryMode();
        composer.resetForm();
    }

    function editHandler(professorInfo: ProfessorInfo) { 
        composer.toEditEntryMode();

        composer.state.professorInfoForm = { 
            userId: professorInfo.user.id,
            username: professorInfo.user.username,
            email: professorInfo.user.email,
            accountName: professorInfo.user.accountName,
            roleIds: professorInfo.roles.map(e => e.id),
            userGroupIds: professorInfo.userGroups.map(e => e.id),
            status: professorInfo.user.status
        };
    }
</script>