import { AxiosHeaders } from "axios";
import type { AxiosInstance, InternalAxiosRequestConfig } from "axios";

class AuthHeaderSetupRequestInterceptor
{
    public static install(httpClient: AxiosInstance)
    {
        const onFulfilled = (config: InternalAxiosRequestConfig) => {
            console.log("### AuthHeaderSetupRequestInterceptor.onFulfilled ###");
            console.log("config => ", config);

            const accessToken = localStorage.getItem("access_token");

            if (accessToken) {
                config.headers = config.headers || new AxiosHeaders();

                if (config.headers instanceof AxiosHeaders) {
                    config.headers.set('Authorization', `Bearer ${accessToken}`);
                } else {
                    (config.headers as any)['Authorization'] = `Bearer ${accessToken}`;
                }
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