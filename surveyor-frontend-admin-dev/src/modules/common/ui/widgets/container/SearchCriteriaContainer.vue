<template>
    <div class="card">
        <div class="card-header">
            <slot name="header">
                <h3 class="card-title text-maroon">{{ state.label.value }}</h3>

                <slot name="header-tools">
                </slot>
            </slot>
        </div>

        <div class="card-body">
            <slot name="body">
            </slot>
        </div>

        <div class="card-footer">
            <slot name="footer">
                <div class="text-right">
                    <ResetButton @click="emit('resetCriteriaRequest')" />
                    &nbsp;
                    <SearchButton @click="emit('searchRequest')" />
                </div>
            </slot>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { onMounted, reactive } from 'vue';
    import { Label } from '@/modules/common/model/label.model';
    import ResetButton from '../common/form/button/ResetButton.vue';
    import SearchButton from '../common/form/button/SearchButton.vue';

    const props = defineProps<{
        label?: Label;
    }>();

    const emit = defineEmits(['resetCriteriaRequest', 'searchRequest']);

    const state = reactive({
        label: props.label || {
            key: 'header',
            value: 'ระบุเงื่อนไขการค้นหา',
        }
    });

    onMounted(() => {
        
    });
</script>