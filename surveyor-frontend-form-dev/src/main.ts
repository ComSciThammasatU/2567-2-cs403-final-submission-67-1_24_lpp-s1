import { createApp } from 'vue';
// import './style.css';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';

const app = createApp(App);
const pinia = createPinia();

app.use(router); // ติดตั้ง Vue Router
app.use(pinia); // ติดตั้ง Pinia
app.mount('#app');
