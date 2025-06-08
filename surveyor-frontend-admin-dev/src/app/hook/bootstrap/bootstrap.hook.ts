abstract class AbstractBootstrapHook
{
    public abstract launchHookOperation(): Promise<void>;
}

export default AbstractBootstrapHook;