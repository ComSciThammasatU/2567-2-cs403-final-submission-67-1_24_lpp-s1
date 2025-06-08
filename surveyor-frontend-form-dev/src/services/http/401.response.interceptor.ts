import type { AxiosInstance, AxiosResponse } from "axios";
import router from "../../router";
import authService from "../auth.service";

class AuthenResponseInterceptor
{
    public static install(httpClient: AxiosInstance)
    {
        const onFulfilled = (response: AxiosResponse) => {
            console.log("### AuthenResponseInterceptor.onFulfilled ###");
            return response;
        };

        const onRejected = (error: any) => {
            console.log("### AuthenResponseInterceptor.onRejected ###");
            console.log("config => ", error);

            if(error.response.status === 401) {
                // alert("No Session OR Session Expired, Please Login");
                authService.clearSession();
                router.push({ path: '/login' });
                return new Promise(() => { /* never resolve/reject */ });;
            }

            return Promise.reject(error);
        };

        httpClient.interceptors.response.use(onFulfilled, onRejected);
    }
}

export default AuthenResponseInterceptor;