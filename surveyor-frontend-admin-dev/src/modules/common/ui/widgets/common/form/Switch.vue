<template>
    <input 
        :id="id"
        type="checkbox" 
        name="my-checkbox" 
        v-model="value"
        data-bootstrap-switch 
        data-off-color="danger" 
        data-on-color="success" />
</template>

<script setup lang="ts">
    // @ts-nocheck

    import { computed, onMounted } from 'vue';

    interface Props {
        id: string;
        modelValue: boolean;
    };

    const props = defineProps<Props>();

    const emit = defineEmits(['update:modelValue']);

    const value = computed({
        get() {
            return props.modelValue;
        },
        set(value) {
            console.log("set...");
            $(`#${props.id}`).bootstrapSwitch('state', props.modelValue);
            emit('update:modelValue', value);
        }
    });

    onMounted(() => {
        // $(`#${props.id}`).bootstrapSwitch('state', props.modelValue);

        $(`#${props.id}`).bootstrapSwitch();

        $(`#${props.id}`).on("mouseup", (event) => {
            console.log("switch.change...");

            // dispatchEvent(new Event('change', { bubbles: true, cancelable: true }));

            // console.log($(`#${props.id}`).val());
            // emit("update:modelValue", $(`#${props.id}`).val());

            // $(`#${props.id}`).bootstrapSwitch('state', props.modelValue);
        });
    });
</script>

<style scoped>

</style>