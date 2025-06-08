<template>
    <Datepicker 
        :placeholder="state.placeholder" 
        :format="state.displayFormat" 
        :modelType="state.modelFormat" 
        :autoApply="state.autoApply" 
        :showNowButton="state.showNowButton" 
        :enableSeconds="state.enableSeconds" 
        :timePicker="true" 
        v-model="value" />
</template>

<script setup lang="ts">
    import { computed, onMounted, reactive } from 'vue';

    interface ValueType {
        hours: number;
        minutes: number;
        seconds: number;
    };

    interface Props {
        placeholder?: string;
        displayFormat?: "HH:mm" | "HH:mm:ss";
        modelFormat?: "HH:mm" | "HH:mm:ss";
        autoApply?: boolean;
        showNowButton?: boolean;
        enableSeconds?: boolean;
        modelValue: any;
    };

    const props = defineProps<Props>();

    const emit = defineEmits(['update:modelValue']);

    const value = computed({
        get() {
            return props.modelValue;
        },
        set(value: { hours: number, minutes: number, seconds: number }) {
            // emit('update:modelValue', `${value.hours}:${value.minutes}`);
            emit('update:modelValue', value);
        }
    });

    const state = reactive({
        placeholder: props.placeholder,
        displayFormat: props.displayFormat || 'HH:mm',
        modelFormat: props.modelFormat || 'HH:mm',
        autoApply: props.autoApply || true,
        showNowButton: props.showNowButton || false,
        enableSeconds: props.enableSeconds || false
    });

    onMounted(() => {
        
    });
</script>