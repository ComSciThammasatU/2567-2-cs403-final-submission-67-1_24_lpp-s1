<template>
    <ez-input-field 
        :labe="props.label" 
        :input="props.input">

        <template #input>
            <ez-text-input
                :size="props.input.size"
                :state="props.input.state"
                v-model="value" />
        </template>

    </ez-input-field>
</template>

<script setup lang="ts">
    import { computed } from 'vue';

    interface Props {
        label?: {
            text?: string
        },

        input: {
            size?: "s" | "m" | "l",
            state?: "normal" | "valid" | "warning" | "invalid"
        },

        modelValue?: string | number;
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
</script>