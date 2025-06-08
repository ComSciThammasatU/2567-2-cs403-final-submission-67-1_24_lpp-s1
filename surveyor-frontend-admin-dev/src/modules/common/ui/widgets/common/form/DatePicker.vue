<template>
    <Datepicker 
        :placeholder="state.placeholder" 
        :format="state.displayFormat" 
        :modelType="state.modelFormat" 
        :minDate="props.minDate" 
        :maxDate="props.maxDate" 
        :disabled-dates="props.disabledDates" 
        :autoApply="state.autoApply" 
        :showNowButton="props.showNowButton" 
        :enableTimePicker="false" 
        v-model="value" />
</template>

<script setup lang="ts">
    import { computed, onMounted, reactive } from 'vue';

    interface Props {
        placeholder?: string;
        displayFormat?: string;
        modelFormat?: string;
        minDate?: Date | string;
        maxDate?: Date | string;
        disabledDates?: Date[] | string[];
        autoApply?: boolean;
        showNowButton?: boolean;
        modelValue?: string | Date;
    };

    const props = defineProps<Props>();

    const emit = defineEmits(['update:modelValue']);

    const value = computed({
        get() {
            return props.modelValue;
        },
        set(value) {
            emit('update:modelValue', value);
        }
    });

    const state = reactive({
        placeholder: props.placeholder,
        displayFormat: props.displayFormat || 'yyyy-MM-dd',
        modelFormat: props.modelFormat || 'yyyy-MM-dd',
        autoApply: props.autoApply || true,
        showNowButton: props.showNowButton || false
    });

    onMounted(() => {
        
    });
</script>