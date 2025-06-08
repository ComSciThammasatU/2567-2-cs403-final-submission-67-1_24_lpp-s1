<template>
    <div class="form-group">
        <slot name="label">
            <label>{{ props.label?.text }}</label>
        </slot>

        <slot name="input">
            <ez-input
                :type="props.input.type"
                :size="props.input.size"
                :state="props.input.state"
                v-model="value" />
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
            type?: "text" | "password" | "number" | "date" | "email" | "phone"
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