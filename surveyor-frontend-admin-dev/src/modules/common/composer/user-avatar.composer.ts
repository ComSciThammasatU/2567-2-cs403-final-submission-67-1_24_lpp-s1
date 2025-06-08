import { computed } from 'vue';
import { useAuthStore } from '@/app/store/auth.store';
import avatarImage from '@/assets/img/avatar/avatar-blank.avif';

export const useUserAvatarComposer = () => {
    const authStore = useAuthStore();

    const profileImageURL = computed(() => {
        if(authStore.userProfile && authStore.userProfile.profileImageURL) {
            return authStore.userProfile.profileImageURL;
        }
        return avatarImage;
    });

    // const backgroundColor = computed(() => "#576574");
    // const backgroundColor = computed(() => "#222f3e");
    const backgroundColor = computed(() => "#95a5a6");

    return {
        profileImageURL,
        backgroundColor
    };
};