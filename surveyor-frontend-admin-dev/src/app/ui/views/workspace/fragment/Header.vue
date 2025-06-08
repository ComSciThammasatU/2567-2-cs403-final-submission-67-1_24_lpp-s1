<template>
    <div class="content-header">
        <div class="container-fluid">
            <div class="row p-2 mb-2 card-footer">
                <div class="col-sm-6 d-none">
                    <h5 class="m-0">{{ pageTitle }}</h5>
                </div>

                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-left">
                        <template v-for="(breadcrumb, index) in pageBreadcrumbs" :key="index">
                            <li v-if="breadcrumb.type === BreadcrumbType.Link" class="breadcrumb-item">
                                <a href="javascript:void(0)">{{ getBreadcrumbText(breadcrumb) }}</a>
                            </li>
                            <li v-else-if="index === pageBreadcrumbs.length - 1" class="breadcrumb-item active text-bold text-dark">{{ getBreadcrumbText(breadcrumb) }}</li>
                            <li v-else class="breadcrumb-item active">{{ getBreadcrumbText(breadcrumb) }}</li>
                        </template>
                    </ol>
                </div>
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
</template>

<script setup lang="ts">
    import { computed } from 'vue';
    import { useWorkSpaceStore } from '@/app/store/workspace.store';
    import { Breadcrumb, BreadcrumbType } from '@/modules/common/model/breadcrumb.model';

    const workspaceStore = useWorkSpaceStore();

    const pageTitle = computed(() => workspaceStore.pageTitle);

    const pageBreadcrumbs = computed(() => workspaceStore.pageBreadcrumbs);

    function getBreadcrumbText(breadcrumb: Breadcrumb): string
    {
        return breadcrumb.text;
    }

    console.log("pageBreadcrumbs => ", pageBreadcrumbs);
</script>