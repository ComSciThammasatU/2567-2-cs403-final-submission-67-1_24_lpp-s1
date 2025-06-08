import axios, { AxiosInstance } from "axios";
import AuthenResponseInterceptor from "./401.response.interceptor";
import AuthorizeResponseInterceptor from "./403.response.interceptor";
import NotFoundResponseInterceptor from "./404.response.interceptor";
import InternalServerErrorResponseInterceptor from "./500.response.interceptor";
import AuthHeaderSetupRequestInterceptor from "./auth-header-setup.request.interceptor";
import LogResponseInterceptor from "./log.response.interceptor";
import EnvironmentProvider from "../env.provider";

const httpClient: AxiosInstance = axios.create({
    baseURL: EnvironmentProvider.getServerEndpoint(),
    headers: {
        "Content-type": "application/json",
    }
});

AuthHeaderSetupRequestInterceptor.install(httpClient);

LogResponseInterceptor.install(httpClient);
AuthenResponseInterceptor.install(httpClient);
AuthorizeResponseInterceptor.install(httpClient);
// NotFoundResponseInterceptor.install(httpClient);
InternalServerErrorResponseInterceptor.install(httpClient);

export default httpClient;