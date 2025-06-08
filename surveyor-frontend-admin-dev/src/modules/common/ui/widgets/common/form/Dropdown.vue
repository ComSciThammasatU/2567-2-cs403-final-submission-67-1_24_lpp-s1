<template>
    <select :id="id" 
            :data-placeholder="placeholder" 
            class="form-control select2" 
            style="width: 100%;" 
            v-model="value"
            :multiple="isMultiple">
        <template v-for="(item) in items" :key="item.id">
            <option :value="item.value">{{ item.text }}</option>
        </template>
    </select>
</template>

<script setup lang="ts">
    import { computed, nextTick, onMounted, onUnmounted, reactive, watch } from 'vue';

    interface ItemModel {
        value: string;
        text: string;
    };

    interface Props {
        id: string;
        selectionMode?: "single" | "multiple";
        placeholder?: string;
        items: ItemModel[];
        modelValue: string | string[];
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

    const isMultiple = computed(() => props.selectionMode === 'multiple');

    onMounted(() => {
        buildDropdown();

        watch(props, async (newProps: Props, oldProps: Props) => {
            await nextTick();
            destroyDropdown();
            buildDropdown();
        });
    });

    onUnmounted(() => {
        destroyDropdown();
    });

    function buildDropdown()
    {
        console.log("--- buildDropdown ---");
        $(`#${props.id}`).select2({
            theme: 'bootstrap4'
        });

        $(`#${props.id}`).on("change", (event) => {
            console.log("dropdown.change");
            console.log(event);

            dispatchEvent(new Event('change', { bubbles: true, cancelable: true }));

            console.log($(`#${props.id}`).val());
            emit("update:modelValue", $(`#${props.id}`).val());
        });
    }

    function destroyDropdown()
    {
        console.log("--- destroyDropdown ---");
        $(`#${props.id}`).select2("destroy");
    }
</script>

<style scoped>

</style>