<template>
    <slot>
        <button :class="state.buttonClass" @click="emit('click', $event)">
            <i :class="state.icon"></i> {{ state.label?.value }}
        </button>
    </slot>
</template>

<script setup lang="ts">
    import { onMounted, reactive } from 'vue';
    import { Label } from '@/modules/common/model/label.model';

    const props = defineProps<{
        buttonClass?: string;
        icon?: string;
        label?: Label;
    }>();

    const emit = defineEmits(['click']);

    const state = reactive({
        buttonClass: props.buttonClass || 'btn btn-sm btn-outline-danger mx-1',
        icon: props.icon || 'fa fa-trash',
        label: props.label
    });

    onMounted(() => {
        
    });
</script>