import ResourceLoader from "@/app/util/resource-loader";

class ResourceController
{
    public static loadStylesheets()
    {
        ResourceLoader.loadStylesheet("https://fonts.googleapis.com/css2?family=Mitr:wght@200;300;400&display=swap");
        ResourceLoader.loadStylesheet("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css");
        ResourceLoader.loadStylesheet("https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.3.0/css/flag-icon.min.css");
        ResourceLoader.loadStylesheet("https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css");
        ResourceLoader.loadStylesheet("https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css");
        ResourceLoader.loadStylesheet("https://adminlte.io/themes/v3/plugins/icheck-bootstrap/icheck-bootstrap.min.css");
        ResourceLoader.loadStylesheet("https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/css/select2.min.css");
        ResourceLoader.loadStylesheet("https://adminlte.io/themes/v3/plugins/select2-bootstrap4-theme/select2-bootstrap4.min.css");
        ResourceLoader.loadStylesheet("https://adminlte.io/themes/v3/plugins/bs-stepper/css/bs-stepper.min.css");
        ResourceLoader.loadStylesheet("https://adminlte.io/themes/v3/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css");
        ResourceLoader.loadStylesheet("https://cdnjs.cloudflare.com/ajax/libs/overlayscrollbars/1.13.1/css/OverlayScrollbars.min.css");
        ResourceLoader.loadStylesheet("https://adminlte.io/themes/v3/dist/css/adminlte.min.css?v=3.2.0");
    }

    public static async loadScripts()
    {
        await ResourceLoader.loadScriptAsync("https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js");
        await ResourceLoader.loadScriptAsync("https://adminlte.io/themes/v3/plugins/jquery-ui/jquery-ui.min.js");
        await ResourceLoader.loadScriptAsync("https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.bundle.min.js");
        await ResourceLoader.loadScriptAsync("https://adminlte.io/themes/v3/plugins/moment/moment.min.js");
        await ResourceLoader.loadScriptAsync("https://adminlte.io/themes/v3/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js");
        await ResourceLoader.loadScriptAsync("https://adminlte.io/themes/v3/plugins/inputmask/jquery.inputmask.min.js");
        await ResourceLoader.loadScriptAsync("https://adminlte.io/themes/v3/plugins/bootstrap-switch/js/bootstrap-switch.min.js");
        await ResourceLoader.loadScriptAsync("https://adminlte.io/themes/v3/plugins/bs-stepper/js/bs-stepper.min.js");
        await ResourceLoader.loadScriptAsync("https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/js/select2.full.min.js");
        await ResourceLoader.loadScriptAsync("https://cdnjs.cloudflare.com/ajax/libs/overlayscrollbars/1.13.1/js/jquery.overlayScrollbars.min.js");
        await ResourceLoader.loadScriptAsync("//cdn.jsdelivr.net/npm/sweetalert2@11");
        await ResourceLoader.loadScriptAsync("https://adminlte.io/themes/v3/dist/js/adminlte.js?v=3.2.0");
    }
}

export default ResourceController;