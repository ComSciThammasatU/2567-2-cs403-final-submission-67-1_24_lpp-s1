import { AxiosInstance, AxiosResponse } from "axios";
import appRouter from '@/app/router/app.router';
import EzAlert, { DialogResult } from '@/modules/common/ui/composables/ez-alert';
import Error403Route from '@/app/router/routes/workspace/error-403.route';
import { useAppStore } from "@/app/store/app.store";

class AuthorizeResponseInterceptor
{
    public static install(httpClient: AxiosInstance)
    {
        const onFulfilled = (response: AxiosResponse) => {
            return response;
        };

        const onRejected = async (error: any) => {
            console.log("### AuthorizeResponseInterceptor.onRejected ###");
            console.log("error => ", error);

            if(error.response.status === 403) {
                const appStore = useAppStore();
                appStore.stopLoading();

                await EzAlert.showError({
                    title: error.response.data.head.status.message.title || "Access Denied",
                    text: error.response.data.head.status.message.content || "No granted authority to access this api endpoint",
                    buttonText: error.response.data.head.status.message.buttons[0].text || "Close",
                    buttonClickedHandler: (result: DialogResult) => {
                        appRouter.getRouter().push({ path: Error403Route.getRoutePath() });
                    }
                });
                
                return;
            }

            return Promise.reject(error);
        };

        httpClient.interceptors.response.use(onFulfilled, onRejected);
    }
}

export default AuthorizeResponseInterceptor;