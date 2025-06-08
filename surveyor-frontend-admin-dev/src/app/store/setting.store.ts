import { useWorkSpaceStore } from '@/app/store/workspace.store';
import { computed, reactive } from "vue";
import { defineStore } from 'pinia';
import { Label } from "@/modules/common/model/label.model";

export const SETTING_STORE_ID = "setting";

export interface SettingMenuItem {
    visible: boolean;
    iconClass: string;
    label: Label;
    badges?: {
        color: string;
        label: string;
    }[];
};

function buildProfileMenuItem(): SettingMenuItem
{
    const workspaceStore = useWorkSpaceStore();

    return {
        visible: true,
        iconClass: 'fas fa-user',
        label: workspaceStore.labels.find(e => e.key === 'label_menu_item_profile') || {
            key: 'label',
            value: 'Profile'
        }
    };
}

function buildChangePasswordMenuItem(): SettingMenuItem
{
    const workspaceStore = useWorkSpaceStore();

    return {
        visible: true,
        iconClass: 'fas fa-unlock-alt',
        label: workspaceStore.labels.find(e => e.key === 'label_menu_item_change_password') || {
            key: 'label',
            value: 'Change Password',
        }
    };
}

function buildSignOutMenuItem(): SettingMenuItem
{
    const workspaceStore = useWorkSpaceStore();

    return {
        visible: true,
        iconClass: 'fas fa-sign-out-alt',
        label: workspaceStore.labels.find(e => e.key === 'label_menu_item_signout') || {
            key: 'label',
            value: 'Sign out',
        }
    };
}

export const useSettingStore = defineStore(SETTING_STORE_ID, () => {
    const state = reactive({
        profileMenuItem: buildProfileMenuItem() as SettingMenuItem,
        changePasswordMenuItem: buildChangePasswordMenuItem() as SettingMenuItem,
        signoutMenuItem: buildSignOutMenuItem() as SettingMenuItem
    });

    const profileMenuItem = computed(() => state.profileMenuItem);
    const changePasswordMenuItem = computed(() => state.changePasswordMenuItem);
    const signoutMenuItem = computed(() => state.signoutMenuItem);

    const profileMenuItemLabel = computed(() => profileMenuItem.value.label.value);
    const changePasswordMenuItemLabel = computed(() => changePasswordMenuItem.value.label.value);
    const signoutMenuItemLabel = computed(() => signoutMenuItem.value.label.value);

    function destroy()
    {
        console.log("### SettingStore.destroy ###");
    }
  
    return {
        profileMenuItem,
        changePasswordMenuItem,
        signoutMenuItem,

        profileMenuItemLabel,
        changePasswordMenuItemLabel,
        signoutMenuItemLabel,

        destroy
    };
});