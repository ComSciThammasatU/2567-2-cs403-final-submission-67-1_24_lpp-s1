import { ForgotPasswordPasswordPolicy } from './../model/forgot-pasword-password-policy.model';
import { HttpLoadScreenBodyResponseMessage } from "@/modules/common/model/http.load-screen-body-message.model";
import { BaseBody, HttpResponseMessage } from "@/modules/common/model/http.response-message.model";
import ezrouteHttpClient from "@/modules/common/service/http/ezroute.http-client";
import axios, { AxiosInstance, AxiosResponse } from "axios";
import { ForgotPasswordConfirmOPTSubmit } from "../model/forgot-password-confirmOPT-submit.model";

class ForgotPasswordService
{
    public async requestLoadScreen(requestMessage: ForgotPasswordLoadScreenRequestMessage): Promise<HttpResponseMessage<ForgotPasswordBodyLoadScreenResponseMessage>>
    {
        console.log("### Auth.LoginService.loadScreen ###");
        const apiEndpoint = "/api/ezasset/auth/forgot-password/load-screen";
        const response = await ezrouteHttpClient.post<HttpResponseMessage<ForgotPasswordBodyLoadScreenResponseMessage>>(apiEndpoint, requestMessage);
        return response.data;
    }

    public async requestSubmit(requestMessage: ForgotPasswordSubmitRequestMessage): Promise<HttpResponseMessage<ForgotPasswordSubmitBodyResponseMessage>> 
    {
        const apiEndpoint = "/api/ezasset/auth/forgot-password/submit";
        const responseMessage = await ezrouteHttpClient.post<HttpResponseMessage<ForgotPasswordSubmitBodyResponseMessage>>(apiEndpoint, requestMessage);
        return responseMessage.data;
    }

    public async confirmOTPLoadScreen(appServerURL: string, requestMessage: ConfirmOTPRequestMessage): Promise<AxiosResponse<ConfirmOTPLoadScreenResponseMessage>>
    {
        console.info("### confirmOTPLoadScreen.loadScreen ###");

        const httpClient: AxiosInstance = axios.create({
            baseURL: appServerURL, 
            headers: {
                "Content-type": "application/json",
            },
        });

        const apiEndpoint = '/api/system/auth/forgot-password/confirm-otp/load-screen';
        return httpClient.post<ConfirmOTPLoadScreenResponseMessage>(apiEndpoint, requestMessage);
    }
    public async resendConfirmOTP(appServerURL: string, requestMessage: ResendConfirmOTPRequestMessage): Promise<ResendConfirmOTPResponseMessage>
    {
        console.info("### resendConfirmOTP ###");

        const httpClient: AxiosInstance = axios.create({
            baseURL: appServerURL, 
            headers: {
                "Content-type": "application/json",
            },
        });

        const apiEndpoint = '/api/system/auth/forgot-password/confirm-otp/resend';
        const response = await httpClient.post<ResendConfirmOTPResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    // submitConfirmOTP
    public async submitConfirmOTP(appServerURL: string, requestMessage: ConfirmOTPSubmitRequestMessage): Promise<ConfirmOTPSubmitResponseMessage>
    {
        console.info("### submitConfirmOTP ###");

        const httpClient: AxiosInstance = axios.create({
            baseURL: appServerURL, 
            headers: {
                "Content-type": "application/json",
            },
        });

        const apiEndpoint = '/api/system/auth/forgot-password/confirm-otp/submit';
        const response = await httpClient.post<ConfirmOTPSubmitResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

    // resetLoadScreen
    public async resetLoadScreen(appServerURL: string, requestMessage: ResetLoadScreenRequestMessage): Promise<AxiosResponse<ResetLoadScreenResponseMessage>>
    {
        console.info("### resetLoadScreen.loadScreen ###");

        const httpClient: AxiosInstance = axios.create({
            baseURL: appServerURL, 
            headers: {
                "Content-type": "application/json",
            },
        });

        const apiEndpoint = '/api/system/auth/forgot-password/reset/load-screen';
        return httpClient.post<ResetLoadScreenResponseMessage>(apiEndpoint, requestMessage);
    }

    // submitResetPassword
    public async submitResetPassword(appServerURL: string, requestMessage: ResetPasswordSubmitRequestMessage): Promise<ResetPasswordSubmitResponseMessage>
    {
        console.info("### submitResetPassword ###");

        const httpClient: AxiosInstance = axios.create({
            baseURL: appServerURL, 
            headers: {
                "Content-type": "application/json",
            },
        });

        const apiEndpoint = 'api/system/auth/forgot-password/reset/submit';
        const response = await httpClient.post<ResetPasswordSubmitResponseMessage>(apiEndpoint, requestMessage);
        return response.data;
    }

}

export interface ForgotPasswordLoadScreenRequestMessage {
    clientPlatform: string;
    langCode?: string;
};

export interface ForgotPasswordBodyLoadScreenResponseMessage extends BaseBody {
    labels: any;
};

export interface ForgotPasswordSubmitRequestMessage {
    app: {
        name: string;
        version: string;
        platform: string;
        os: {
            name: string;
            version: string;
        }
    },
    companyCode?: string;
    email?: string;
    langCode: string;
};

export interface ForgotPasswordSubmitBodyResponseMessage extends BaseBody {
    appServerURL: string;
    requestId: string;
    confirmOTP: string;
    confirmExpiredPeriod: string;
    confirmExpiredAt: string;
};

export interface ConfirmOTPRequestMessage {
    requestId: string;
    langCode?: string;
};

export interface ConfirmOTPLoadScreenResponseMessage extends HttpResponseMessage<HttpLoadScreenBodyResponseMessage<{
    
}>> {};

export interface ResendConfirmOTPRequestMessage {
    previousRequestId: string;
    langCode?: string;
};

export interface ResendConfirmOTPResponseMessage extends HttpResponseMessage<{
    requestId: string;
    confirmOTP: string;
    confirmExpiredPeriod: string;
    confirmExpiredAt: string;
}> {};

export interface ConfirmOTPSubmitRequestMessage {
    requestId?: string;
    confirmOTP?: string;
};

export interface ConfirmOTPSubmitResponseMessage extends HttpResponseMessage<{
    resetPasswordLog: ForgotPasswordConfirmOPTSubmit;
}> {};

export interface ResetLoadScreenRequestMessage {
    requestId: string;
    langCode?: string;
};

export interface ResetLoadScreenResponseMessage extends HttpResponseMessage<HttpLoadScreenBodyResponseMessage<ForgotPasswordPasswordPolicy>> {};

export interface ResetPasswordSubmitRequestMessage {
    requestId: string;
    newPassword?: string;
};

export interface ResetPasswordSubmitResponseMessage extends HttpResponseMessage<{
    resetPasswordLog: ForgotPasswordConfirmOPTSubmit;
}> {};

export default new ForgotPasswordService();