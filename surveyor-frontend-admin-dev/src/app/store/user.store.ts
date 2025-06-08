import { computed, reactive } from "vue";
import { User } from "@/modules/core/model/user.model";
import userService from "@/modules/system/service/user.service";

const state = reactive({
    users: [] as User[]
});

const users = computed(() => state.users);

const loadUsers = async () => {
    state.users = await userService.loadAll();
};

const addNewUser = async (username: string, password: string) => {
    return new Promise((resolve, reject) => {
        const user: User = {
            id: new Date().toISOString(),
            username,
            password
        };

        state.users.push(user);

        resolve(user);
    });
};

export {
    // state,
    users,
    loadUsers,
    addNewUser
};