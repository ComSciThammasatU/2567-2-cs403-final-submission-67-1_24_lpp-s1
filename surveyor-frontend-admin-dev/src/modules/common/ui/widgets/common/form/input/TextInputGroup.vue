<template>
    
    <div class="input-group mb-3">
        <slot name="append-left">
        </slot>

        <slot name="input">
            <ez-text-input
                :size="props.input.size"
                :state="props.input.state"
                v-model="value" 
                @on-enter="$emit('on-input-enter')" />
        </slot>

        <slot name="append-right">
            <div class="input-group-append">
                <div class="input-group-text">
                    <span :class="props.append?.right?.faClass"></span>
                </div>
            </div>
        </slot>
    </div>
</template>

<script setup lang="ts">
    import { computed } from 'vue';

    interface Props {
        append?: {
            left?: {
                faClass?: string;
            },
            right?: {
                faClass?: string;
            }
        },

        input: {
            size?: "s" | "m" | "l",
            state?: "normal" | "valid" | "warning" | "invalid"
        },

        modelValue?: string;
    };

    const props = defineProps<Props>();

    const emit = defineEmits(['update:modelValue', 'on-input-enter']);

    const value = computed({
        get() {
            return props.modelValue;
        },
        set(value) {
            console.log(`TextInputGroup.value.set: ${value}`);
            emit('update:modelValue', value);
        }
    });

    // function onInterEnterHandler()
    // {
    //     emit('on-input-enter');
    // }
</script>