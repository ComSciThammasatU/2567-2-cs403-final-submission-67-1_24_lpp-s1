class EnvironmentProvider
{
    public static getAppName(): string
    {
        return import.meta.env.VITE_APP_NAME;
    }

    public static getAppVersion(): string
    {
        return import.meta.env.VITE_APP_VERSION;
    }

    public static getMode(): string
    {
        return import.meta.env.MODE;
    }

    // public static getRunningMode(): RunningModeConst
    // {
    //     return import.meta.env.VITE_RUNNING_MODE;
    // }

    // public static getEnv(): EnvConst
    // {
    //     return import.meta.env.VITE_ENV;
    // }

    public static getBaseURL(): string
    {
        return import.meta.env.BASE_URL;
    }

    public static getServerEndpoint(): string
    {
        return import.meta.env.VITE_SERVER_ENDPOINT;
    }

    public static getLiffId(): string
    {
        return import.meta.env.VITE_LIFF_ID;
    }
}

export default EnvironmentProvider;