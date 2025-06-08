import { AxiosInstance, AxiosResponse } from "axios";
import appRouter from '@/app/router/app.router';
import Error404Route from '@/app/router/routes/workspace/error-404.route';

class NotFoundResponseInterceptor
{
    public static install(httpClient: AxiosInstance)
    {
        const onFulfilled = (response: AxiosResponse) => {
            return response;
        };

        const onRejected = (error: any) => {
            console.log("### NotFoundResponseInterceptor.onRejected ###");
            console.log("error => ", error);

            if(error.response.status === 404) {
                appRouter.getRouter().push({ path: Error404Route.getRoutePath() });
                return;
            }

            return Promise.reject(error);
        };

        httpClient.interceptors.response.use(onFulfilled, onRejected);
    }
}

export default NotFoundResponseInterceptor;