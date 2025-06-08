import { AxiosInstance, AxiosResponse } from "axios";

class LogResponseInterceptor
{
    public static install(httpClient: AxiosInstance)
    {
        const onFulfilled = (response: AxiosResponse) => {
            console.log("### LogResponseInterceptor.onFulfilled ###");
            console.log("response => ", response);
            return response;
        };

        const onRejected = (error: any) => {
            console.log("### LogResponseInterceptor.onRejected ###");
            console.log("error => ", error);
            return Promise.reject(error);
        };

        httpClient.interceptors.response.use(onFulfilled, onRejected);
    }
}

export default LogResponseInterceptor;