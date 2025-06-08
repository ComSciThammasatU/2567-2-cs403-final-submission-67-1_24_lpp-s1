import EzAlert, { DialogResult } from '@/modules/common/ui/composables/ez-alert';
import { useAuthStore } from '@/app/store/auth.store';
import { AxiosInstance, AxiosResponse } from "axios";
import { useAppStore } from '@/app/store/app.store';

class AuthenResponseInterceptor
{
    public static install(httpClient: AxiosInstance)
    {
        const onFulfilled = (response: AxiosResponse) => {
            return response;
        };

        const onRejected = async (error: any) => {
            console.log("### AuthenResponseInterceptor.onRejected ###");
            console.log("error => ", error);

            if(error.response.status === 401) {
                const appStore = useAppStore();
                appStore.stopLoading();

                const authStore = useAuthStore();
                authStore.gotoLoginPage();
                authStore.clearData();

                await EzAlert.showError({
                    title: error.response.data.head.status.message.title || "Session Expired",
                    text: error.response.data.head.status.message.content || "Your session has been expired, Please Login",
                    buttonText: error.response.data.head.status.message.buttons[0].text || "Close",
                    // buttonClickedHandler: (result: DialogResult) => {
                    //     const authStore = useAuthStore();
                    //     authStore.gotoLoginPage();
                    //     authStore.clearData();
                    // }
                });

                return;
            }

            return Promise.reject(error);
        };

        httpClient.interceptors.response.use(onFulfilled, onRejected);
    }
}

export default AuthenResponseInterceptor;