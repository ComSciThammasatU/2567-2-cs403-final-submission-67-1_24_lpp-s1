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
                                    Staff Name
                                </th>
                                <th class="align-middle text-center">
                                    Action
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(staffInfo, index) in composer.state.staffInfos">
                                <td class="align-middle text-center">{{ index + 1 }}.</td>
                                <td class="align-middle text-center">{{ staffInfo.user.username }}</td>
                                <td class="align-middle text-center">{{ staffInfo.user.email }}</td>
                                <td class="align-middle text-center">{{ staffInfo.user.accountName }}</td>
                                <td class="align-middle text-center">
                                    <UpdateActionButton @click="editHandler(staffInfo)" />
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
    import { useStaffSetupComposer } from './staff-setup.composer';
import { StaffInfo } from '@/modules/core/model/staff-info.model';

    const composer = useStaffSetupComposer();

    function addHandler() {
        composer.toAddEntryMode();
        composer.resetForm();
    }

    function editHandler(staffInfo: StaffInfo) {
        composer.toEditEntryMode();

        composer.state.staffInfoForm = {
            userId: staffInfo.user.id,
            username: staffInfo.user.username,
            email: staffInfo.user.email,
            accountName: staffInfo.user.accountName,
            roleIds: staffInfo.roles.map(e => e.id),
            userGroupIds: staffInfo.userGroups.map(e => e.id),
            status: staffInfo.user.status
        };
    }
</script>