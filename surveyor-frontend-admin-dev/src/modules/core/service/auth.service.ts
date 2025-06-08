import httpClient from '@/modules/common/service/http/http-client.service';
import { BaseBody } from '../../common/model/http.response-message.model';
import { UserProfile } from '@/modules/core/model/user-profile.model';
import { HttpResponseMessage } from '@/modules/common/model/http.response-message.model';

namespace Auth {

    /****************************** <Login> ******************************/
    class LoginService {
        // public async loadScreen(requestMessage: LoginLoadScreenRequestMessage): Promise<HttpResponseMessage<LoginBodyLoadScreenResponseMessage>>
        // {
        //     console.log("### Auth.LoginService.loadScreen ###");
        //     const apiEndpoint = "/api/core/user/auth/login/load-screen";
        //     const response = await httpClient.post<HttpResponseMessage<LoginBodyLoadScreenResponseMessage>>(apiEndpoint, requestMessage);
        //     return response.data;
        // }

        public async login(requestMessage: LoginRequestMessage): Promise<HttpResponseMessage<LoginBodyResponseMessage>> 
        {
            console.log("requestMessage => ", requestMessage);
            const apiEndpoint = "/api/core/user/auth/login";
            const responseMessage = await httpClient.post<HttpResponseMessage<LoginBodyResponseMessage>>(apiEndpoint, requestMessage);
            return responseMessage.data;
        }
    }

    // export interface LoginLoadScreenRequestMessage {
        
    // };

    // export interface LoginBodyLoadScreenResponseMessage extends BaseBody {
        
    // };

    export interface LoginRequestMessage {
        clientAppInfo: {
            name: string;
            version: string;
            platform: string;
        },
        username: string;
        password: string;
    }

    export interface LoginBodyResponseMessage extends BaseBody {
        accessToken: string;
        userProfile: UserProfile;
    }

    export const loginService = new LoginService();
    /****************************** </Login> ******************************/


    /****************************** <Logout> ******************************/
    class LogoutService {
        public async logout(requestMessage: LogoutRequestMessage): Promise<HttpResponseMessage<LogoutBodyResponseMessage>> {
            const apiEndpoint = "/api/core/user/auth/logout";
            const response = await httpClient.post<HttpResponseMessage<LogoutBodyResponseMessage>>(apiEndpoint, requestMessage);
            return response.data;
        }
    }

    export interface LogoutRequestMessage {

    }

    export interface LogoutBodyResponseMessage extends BaseBody {
        authSessionId: string
    }

    export const logoutService = new LogoutService();
    /****************************** </Logout> ******************************/
}

export default Auth;