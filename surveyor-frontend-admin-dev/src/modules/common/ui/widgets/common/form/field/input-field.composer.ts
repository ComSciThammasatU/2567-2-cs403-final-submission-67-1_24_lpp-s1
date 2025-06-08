import { computed } from 'vue';

interface InputFieldProps {
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

export function useInputFieldComposer() {
    const props = defineProps<InputFieldProps>();

    const emit = defineEmits(['update:modelValue']);

    const value = computed({
        get() {
            return props.modelValue;
        },
        set(value) {
            emit('update:modelValue', value);
        }
    });

    return {
        props,
        emit,
        value
    };
};