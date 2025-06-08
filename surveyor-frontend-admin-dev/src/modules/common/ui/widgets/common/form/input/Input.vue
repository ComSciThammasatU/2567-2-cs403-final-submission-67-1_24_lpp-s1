<template>
    <input 
        :type="props.type" 
        :class="getInputClasses()"
        v-model="value" 
        @keyup.enter="$emit('on-enter')" />
</template>


<script setup lang="ts">
    import { computed } from 'vue';

    interface Props {
        type: "text" | "password" | "number" | "date" | "email" | "phone"
        modelValue?: string | number;
        size?: "s" | "m" | "l",
        state?: "normal" | "valid" | "warning" | "invalid"
    };

    const props = defineProps<Props>();

    const emit = defineEmits(['update:modelValue', 'on-enter']);

    const value = computed({
        get() {
            return props.modelValue;
        },
        set(value) {
            emit('update:modelValue', value);
        }
    });

    function getInputClasses()
    {
        return [
            "form-control",
            getInputSizeClass(),
            getInputStateClass()
        ];
    }

    function getInputSizeClass()
    {

        if(props.size === 's') {
            return "form-control-sm";
        }

        if(props.size === 'm') {
            return "";
        }

        if(props.size === 'l') {
            return "form-control-lg";
        }

        return "";
    }

    function getInputStateClass()
    {
        if(props.state === 'normal') {
            return "";
        }

        if(props.state === 'valid') {
            return "is-valid";
        }

        if(props.state === 'warning') {
            return "is-warning";
        }

        if(props.state === 'invalid') {
            return "is-invalid";
        }

        return "";
    }
</script>