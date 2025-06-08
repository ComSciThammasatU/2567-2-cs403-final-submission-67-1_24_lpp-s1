import { useWorkSpaceStore } from '@/app/store/workspace.store';
import { useSettingStore } from '@/app/store/setting.store';
import BootstrapHookMissionControl from '@/app/hook/bootstrap/bootstrap-hook-mission-control';
import { useAppStore } from '@/app/store/app.store';
import { USER_PROFILE, ACCESS_TOKEN } from '@/modules/common/const/app.const';
import { UserProfile } from '@/modules/core/model/user-profile.model';
import { computed, reactive } from "vue";
import { defineStore } from 'pinia';
import Swal from 'sweetalert2';
import Auth from '@/modules/core/service/auth.service';
import appRouter from '@/app/router/app.router';
import { HttpResponseMessage, StatusType } from '@/modules/common/model/http.response-message.model';
import EzAlert from '@/modules/common/ui/composables/ez-alert';
import userService from '@/modules/system/service/user.service';
import HomeRoute from '../router/routes/workspace/home.route';
import LoginRoute from '../router/routes/login.route';
// import ForceChangePasswordRoute from '../router/routes/force-change-password.route';

export const AUTH_STORE_ID = "auth";


// ********** ACCESS TOKEN ********** //
const getAccessTokenFromStorage = (): string | null => {
    return localStorage.getItem(ACCESS_TOKEN);
};

const saveAccessTokenToStorage = (accessToken: string): void => {
    localStorage.setItem(ACCESS_TOKEN, accessToken);
};

const removeAccessTokenFromStorage = (): void => {
    localStorage.removeItem(ACCESS_TOKEN);
};
// ********** ACCESS TOKEN ********** //


// ********** FORCE CHANGE PASSWORD ********** //
const getForceChangePasswordFromStorage = (): boolean => {
    let forceChangePassword = localStorage.getItem("force_change_password");
    if(forceChangePassword) {
        return forceChangePassword.toLocaleLowerCase() === "true";
    }
    return false;
};

const saveForceChangePasswordToStorage = (forceChangePassword: boolean): void => {
    localStorage.setItem("force_change_password", new Boolean(forceChangePassword).toString());
};

const removeForceChangePasswordFromStorage = (): void => {
    localStorage.removeItem("force_change_password");
};
// ********** FORCE CHANGE PASSWORD ********** //


// ********** USER PROFILE ********** //
const getUserProfileFromStorage = (): UserProfile | null => {
    const userProfile = localStorage.getItem(USER_PROFILE);
    if(userProfile) {
        return JSON.parse(userProfile);
    }
    return null;
};

const saveUserProfileToStorage = (userProfile: UserProfile): void => {
    localStorage.setItem(USER_PROFILE, JSON.stringify(userProfile));
};

const removeUserProfileFromStorage = (): void => {
    localStorage.removeItem(USER_PROFILE);
    sessionStorage.removeItem(USER_PROFILE);
};
// ********** USER PROFILE ********** //


export const useAuthStore = defineStore(AUTH_STORE_ID, () => {
    const state = reactive({
        accessToken: getAccessTokenFromStorage(),
        forceChangePassword: getForceChangePasswordFromStorage(),
        userProfile: getUserProfileFromStorage()
    });

    const userProfile = computed(() => state.userProfile);

    async function login(requestMessage: Auth.LoginRequestMessage, callbackURL?: string) {
        console.info("### AuthStore.login ###");

        const appStore = useAppStore();

        try {
            appStore.startLoading();

            const responseMessage: HttpResponseMessage<Auth.LoginBodyResponseMessage> = await Auth.loginService.login(requestMessage);

            console.log("responseMessage => ", responseMessage);

            if(responseMessage.head.status.type === StatusType.Success && responseMessage.body) {
                const payloadData = responseMessage.body;

                state.accessToken = payloadData.accessToken;
                saveAccessTokenToStorage(payloadData.accessToken);

                state.userProfile = payloadData.userProfile;
                saveUserProfileToStorage(payloadData.userProfile);

                await BootstrapHookMissionControl.launchBootstrapHookMission();
                if(callbackURL) {
                    appRouter.getRouter().replace({ path: callbackURL });
                } else {
                    appRouter.getRouter().replace({ path: HomeRoute.getRoutePath() });
                }
            } else {
                EzAlert.showError({
                    title: responseMessage.head.status.title,
                    text: responseMessage.head.status.message,
                    buttonText: "OK"
                });
            }
        } catch(error: any) {
            console.error(error);

            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 2000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            });
    
            Toast.fire({
                icon: 'error',
                title: JSON.stringify(error.message)
            });
        } finally {
            appStore.stopLoading();
        }
    }

    async function logout() {
        console.info("### AuthStore.logout ###");

        const appStore = useAppStore();

        try {
            appStore.startLoading();
            const response: HttpResponseMessage<Auth.LogoutBodyResponseMessage> = await Auth.logoutService.logout({});
        } catch(error: any) {
            console.error(error);

            // const Toast = Swal.mixin({
            //     toast: true,
            //     position: 'top-end',
            //     showConfirmButton: false,
            //     timer: 2000,
            //     timerProgressBar: true,
            //     didOpen: (toast) => {
            //         toast.addEventListener('mouseenter', Swal.stopTimer)
            //         toast.addEventListener('mouseleave', Swal.resumeTimer)
            //     }
            // });
    
            // Toast.fire({
            //     icon: 'error',
            //     title: JSON.stringify(error.message)
            // });
        } finally {
            clearData();
            // appRouter.destroyWorkSpaceDynamicRoutes();
            gotoLoginPage();
            appStore.stopLoading();
        }
    }

    async function forceChangePassword(callbackURL?: string)
    {
        console.info("### AuthStore.forceChangePassword ###");

        // await BootstrapHookMissionControl.launchBootstrapHookMission();

        state.forceChangePassword = false;
        saveForceChangePasswordToStorage(state.forceChangePassword);

        if(callbackURL) {
            appRouter.getRouter().push({ path: callbackURL });   
        } else {
            appRouter.getRouter().push({ path: HomeRoute.getRoutePath() });
        }

        await appRouter.setupWorkSpaceDynamicRoutes();
        BootstrapHookMissionControl.launchBootstrapHookMission();
    }

    function clearData() {
        state.accessToken = null;
        state.forceChangePassword = false;
        state.userProfile = null;

        removeAccessTokenFromStorage();
        removeForceChangePasswordFromStorage();
        removeUserProfileFromStorage();

        const workspaceStore = useWorkSpaceStore();
        const settingStore = useSettingStore();

        workspaceStore.destroy();
        settingStore.destroy();
    }

    function gotoLoginPage() {
        const previousRoutePath = appRouter.getPreviousRoutePath();
        let callbackURL = HomeRoute.getRoutePath();
        console.log("previousRoutePath => ", previousRoutePath);

        if(previousRoutePath !== '' && previousRoutePath !== LoginRoute.getRoutePath() && !previousRoutePath.startsWith('/error')) {
            callbackURL = previousRoutePath;
        }
        console.log(`callbackURL => ${callbackURL}`);

        appRouter.getRouter().replace({ path: LoginRoute.getRoutePath(), query: { callbackURL: callbackURL } });
    }

    function gotoForceChangePasswordPage() {
        // appRouter.getRouter().replace({ path: ForceChangePasswordRoute.getRoutePath() });
    }

    async function loadUserProfile() {
        console.log("### AuthStore.loadUserProfile ###");

        const userProfile = await userService.loadCurrentUserProfile();
        if(userProfile) {
            state.userProfile = userProfile;
            saveUserProfileToStorage(userProfile);
        }
    }

    function setUserProfile(userProfile?: UserProfile): void {
        console.log("### AuthStore.setUserProfile ###");

        if(userProfile) {
            state.userProfile = userProfile;
            saveUserProfileToStorage(userProfile);
        }
    }

    function loadAllGrantedAuthority() {

    }

    function loadGrantedAuthorityByView(viewName: string) {

    }

    function refreshAccessToken() {

    }

    function hasSignedIn(): boolean {
        console.log("### AuthStore.hasSignedIn ###");
        return state.accessToken !== null;
    }

    function shouldForceChangePassword(): boolean {
        console.log("### AuthStore.shouldForceChangePassword ###");
        return state.forceChangePassword;
    }
  
    return {
        state,
        userProfile,

        login,
        logout,
        forceChangePassword,
        loadUserProfile,
        setUserProfile,
        refreshAccessToken,
        hasSignedIn,
        shouldForceChangePassword,
        clearData,
        gotoLoginPage,
        gotoForceChangePasswordPage
    }
});