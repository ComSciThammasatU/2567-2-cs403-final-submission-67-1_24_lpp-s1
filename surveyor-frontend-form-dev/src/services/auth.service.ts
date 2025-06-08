import { StatusType, type HttpResponseMessage } from "../models/http.response-message.model";
import EnvironmentProvider from "./env.provider";
import httpClient from "./http/http-client.service";

class AuthService
{
    public async login(requestMessage: LoginRequestMessage): Promise<LoginResponseMessage> 
    {
        console.info("### AuthService.login ###");

        if(! requestMessage.clientAppInfo) {
            requestMessage.clientAppInfo = {
                name: EnvironmentProvider.getAppName(),
                version: EnvironmentProvider.getAppVersion(),
                platform: "web"
            }
        }

        const apiEndpoint = '/api/core/user/auth/login';
        const response = await httpClient.post<LoginResponseMessage>(apiEndpoint, requestMessage);

        if(response.status === 200 && response.data.head.status.type === StatusType.Success) {
            if(response.data.body?.accessToken) {
                localStorage.setItem("access_token", response.data.body?.accessToken);
                localStorage.setItem("user_id", response.data.body?.userProfile.user.id);
                localStorage.setItem("username", response.data.body?.userProfile.user.username);
                localStorage.setItem("account_name", response.data.body?.userProfile.user.accountName);
            }
        }

        return response.data;
    }

    public async logout(requestMessage: LogoutRequestMessage): Promise<LogoutResponseMessage> 
    {
        console.info("### AuthService.logout ###");

        const apiEndpoint = '/api/core/user/auth/logout';
        const response = await httpClient.post<LogoutResponseMessage>(apiEndpoint, requestMessage);

        if(response.status === 200 && response.data.head.status.type === StatusType.Success) {
            this.clearSession();
        }

        return response.data;
    }

    public clearSession()
    {
        localStorage.removeItem("access_token");
        localStorage.removeItem("user_id");
        localStorage.removeItem("username");
        localStorage.removeItem("account_name");
    }
}

export interface LoginRequestMessage {
    clientAppInfo?: {
        name: string;
        version: string;
        platform: string;
    },

    username: string;
    password: string;
};

export interface LoginResponseMessage extends HttpResponseMessage<{
    accessToken: string;
    userProfile: {
        user: {
            id: string;
            username: string;
            accountName: string;
        }
    }
}> {};

export interface LogoutRequestMessage {
    
};

export interface LogoutResponseMessage extends HttpResponseMessage<{
    
}> {};

export default new AuthService();