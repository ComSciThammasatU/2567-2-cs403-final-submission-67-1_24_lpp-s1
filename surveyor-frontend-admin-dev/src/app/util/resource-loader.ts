class ResourceLoader
{
    public static loadStylesheet(href: string)
    {
        const stylesheet = document.createElement("link");
        stylesheet.rel = "stylesheet";
        stylesheet.type = "text/css";
        stylesheet.href = href;
        document.head.appendChild(stylesheet);
    }

    public static loadStylesheetAsync(href: string)
    {
        return new Promise((resolve, reject) => {
            const stylesheet = document.createElement("link");
            stylesheet.rel = "stylesheet";
            stylesheet.type = "text/css";
            stylesheet.href = href;
            stylesheet.onload = resolve;
            stylesheet.onerror = reject;
            document.head.appendChild(stylesheet);
        });
    }

    public static loadScript(src: string)
    {
        const script = document.createElement('script');
        script.src = src;
        document.head.appendChild(script);
    }

    public static loadScriptAsync(src: string)
    {
        return new Promise((resolve, reject) => {
            const script = document.createElement('script');
            script.src = src;
            script.onload = resolve;
            script.onerror = reject;
            document.head.appendChild(script);
        });
    }
}

export default ResourceLoader;