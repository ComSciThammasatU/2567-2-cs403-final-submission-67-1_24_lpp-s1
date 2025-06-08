import type { AxiosInstance } from "axios";
import axios from "axios";
import EnvironmentProvider from "../env.provider";
import AuthHeaderSetupRequestInterceptor from "./auth-header-setup.interceptor";
import AuthenResponseInterceptor from "./401.response.interceptor";

const httpClient: AxiosInstance = axios.create({
    baseURL: EnvironmentProvider.getServerEndpoint(),
    headers: {
        "Content-type": "application/json",
    }
});

AuthHeaderSetupRequestInterceptor.install(httpClient);
AuthenResponseInterceptor.install(httpClient);

export default httpClient;