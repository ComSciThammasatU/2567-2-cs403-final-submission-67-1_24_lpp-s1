import { AxiosInstance, AxiosRequestConfig } from "axios";
import { useAuthStore } from '@/app/store/auth.store';

class AuthHeaderSetupRequestInterceptor
{
    public static install(httpClient: AxiosInstance)
    {
        const onFulfilled = (config: AxiosRequestConfig) => {
            console.log("### AuthHeaderSetupRequestInterceptor.onFulfilled ###");
            console.log("config => ", config);

            const authStore = useAuthStore();
            const accessToken = authStore.state.accessToken;

            if(accessToken) {
                config.headers = {
                    Authorization: `Bearer ${accessToken}`
                };
            }

            return config;
        };

        const onRejected = (error: any) => {
            console.log("### AuthHeaderSetupRequestInterceptor.onRejected ###");
            console.log("config => ", error);
            return Promise.reject(error);
        };

        httpClient.interceptors.request.use(onFulfilled, onRejected);
    }
}

export default AuthHeaderSetupRequestInterceptor;