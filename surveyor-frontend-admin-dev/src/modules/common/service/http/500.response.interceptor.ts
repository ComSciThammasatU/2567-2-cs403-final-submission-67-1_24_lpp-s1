import { AxiosInstance, AxiosResponse } from "axios";
import appRouter from '@/app/router/app.router';
import Error500Route from '@/app/router/routes/workspace/error-500.route';

class InternalServerErrorResponseInterceptor
{
    public static install(httpClient: AxiosInstance)
    {
        const onFulfilled = (response: AxiosResponse) => {
            return response;
        };

        const onRejected = (error: any) => {
            console.log("### InternalServerErrorResponseInterceptor.onRejected ###");
            console.log("error => ", error);

            if(error.response.status === 500) {
                appRouter.getRouter().push({ path: Error500Route.getRoutePath() });
                return;
            }

            return Promise.reject(error);
        };

        httpClient.interceptors.response.use(onFulfilled, onRejected);
    }
}

export default InternalServerErrorResponseInterceptor;