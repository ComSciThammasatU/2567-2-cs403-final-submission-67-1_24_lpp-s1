<style scoped>
    a {
        color: #42b983;
    }

    label {
        margin: 0 0.5em;
        font-weight: bold;
    }

    code {
        background-color: #eee;
        padding: 2px 4px;
        border-radius: 4px;
        color: #304455;
    }
</style>

<template>
    <h1>User List</h1>

    <ul>
        <li v-for="(user, index) in users" :key="user.id">
            {{ Number(index + 1) }}. {{ user.id }} : {{ user.username }}
        </li>
    </ul>

    <!-- <input type="text" class="form-control"> -->

    <div>
        current user: {{ state.currentUser }}
    </div>
</template>

<script setup lang="ts">
    // import $ from "jquery";
    // import select2 from 'select2';
    import * as userStore from '@/app/store/user.store';
    import { computed, onMounted, reactive, ref } from 'vue';
    import { User } from '@/modules/system/model/user.model';
    import userService from '@/modules/system/service/user.service';

    const state = reactive({
        users: [] as User[],
        currentUser: undefined as User | undefined
    });

    const select = ref(null);

    const users = computed(() => {
        return userStore.users.value;
    });

    onMounted(async () => {
        console.log(`### UserList.onMounted ###`);

        // state.users = await userService.loadAll();

        userStore.loadUsers();

        state.currentUser = state.users[0];

        // $('.select2').select2();
        setTimeout(() => {
            attach();
        }, 100);
    });

    function attach(): void {
        console.log("!!! attach !!!");
        console.log(select);
        // $(select).select2();
    }
</script>