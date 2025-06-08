<template>
    <div class="form-group">
        <slot name="label">
            <label>{{ props.label?.text }}</label>
        </slot>

        <slot name="input">
            <ez-text-input-group 
                :input="props.input"
                :append="props.append"
                :modelValue="props.modelValue" 
                @input="$emit('update:modelValue', $event.target.value)" 
                @on-input-enter="$emit('on-input-enter')" />
        </slot>
    </div>
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

        append?: {
            left?: {
                faClass?: string;
            },
            right?: {
                faClass?: string;
            }
        },

        modelValue?: string;
    };

    const props = defineProps<Props>();

    const emit = defineEmits(['update:modelValue', 'on-input-enter']);
</script>