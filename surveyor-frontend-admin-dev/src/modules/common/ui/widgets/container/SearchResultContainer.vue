<template>
    <div class="card">
        <!-- card-header -->
        <div class="card-header">
            <slot name="header">
                <slot name="header-title">
                    <h3 class="card-title text-maroon">{{ state.label.value }}</h3>
                </slot>

                <slot name="header-tools">
                </slot>
            </slot>
        </div>
        <!-- /.card-header -->

        <!-- card-body -->
        <div class="card-body p-2">
            <slot name="body"></slot>
        </div>
        <!-- /.card-body -->

        <!-- card-footer -->
        <div class="card-footer clearfix">
            <slot name="footer"></slot>
        </div>
        <!-- /.card-footer -->
    </div>
</template>

<script setup lang="ts">
    import { onMounted, reactive } from 'vue';
    import { Label } from '@/modules/common/model/label.model';

    const props = defineProps<{
        label?: Label;
    }>();

    const state = reactive({
        label: props.label || {
            key: 'header',
            value: 'ผลการค้นหา',
            i18ns: [
                {
                    langCode: 'en',
                    value: 'Search Result'
                },
                {
                    langCode: 'th',
                    value: 'ผลการค้นหา'
                }
            ]
        }
    });

    onMounted(() => {
        
    });
</script>