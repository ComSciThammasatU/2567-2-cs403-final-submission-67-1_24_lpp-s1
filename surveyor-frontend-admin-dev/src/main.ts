import { createApp } from 'vue';
import { createPinia } from 'pinia';
import { autoAnimatePlugin } from '@formkit/auto-animate/vue';
import App from '@/app/App.vue';
import BootstrapHookMissionControl from '@/app/hook/bootstrap/bootstrap-hook-mission-control';
import appRouter from '@/app/router/app.router';
import ResourceController from '@/app/util/resource.controller';
import EzWidgets from '@/app/plugins/EzWidgets';
import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

async function bootstrap() {
    console.info("### Bootstraping Survey Admin Application ###");

    ResourceController.loadStylesheets();
    await ResourceController.loadScripts();

    const data = JSON.parse(sessionStorage.getItem("data") || "{}");
    console.log("data => ", data);
    
    const app = createApp(App);

    const pinia = createPinia();
    pinia.install(app);

    await appRouter.setup();
    appRouter.getRouter().install(app);

    app.use(autoAnimatePlugin);
    app.use(EzWidgets);
    app.component('Datepicker', Datepicker);

    app.mount('#app');

    BootstrapHookMissionControl.launchBootstrapHookMission();
}

bootstrap();