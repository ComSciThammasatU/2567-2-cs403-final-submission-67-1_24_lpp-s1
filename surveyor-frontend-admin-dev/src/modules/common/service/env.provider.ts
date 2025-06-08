import { EnvConst } from '../const/env.const';
import { RunningModeConst } from './../const/running-mode.const';

class EnvironmentProvider
{
    protected static getServerConfig(key: string)
    {
        const data = JSON.parse(sessionStorage.getItem("data") || "{}");
        return data[key];
    }

    public static getAppName(): string
    {
        let appName = EnvironmentProvider.getServerConfig('appName');
        if(! appName) {
            appName = import.meta.env.VITE_APP_NAME;
        }
        return appName;
    }

    public static getAppVersion(): string
    {
        let appVersion = EnvironmentProvider.getServerConfig('appVersion');
        if(! appVersion) {
            appVersion = import.meta.env.VITE_APP_VERSION;
        }
        return appVersion;
    }

    public static getMode(): string
    {
        return import.meta.env.MODE;
    }

    public static getRunningMode(): RunningModeConst
    {
        return import.meta.env.VITE_RUNNING_MODE;
    }

    public static getEnv(): EnvConst
    {
        let env = EnvironmentProvider.getServerConfig('appEnv');
        if(! env) {
            env = import.meta.env.VITE_ENV;
        }
        return env;
    }

    public static getBaseURL(): string
    {
        return import.meta.env.BASE_URL;
    }

    public static getServerEndpoint(): string
    {
        let serverEndpoint = EnvironmentProvider.getServerConfig('serverEndpoint');
        if(! serverEndpoint) {
            serverEndpoint = import.meta.env.VITE_SERVER_ENDPOINT;
        }
        return serverEndpoint;
    }
}

export default EnvironmentProvider;