// @ts-nocheck
import { createApp } from 'vue';
import App from '@/App.vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import router from '@/utils/router';
import logger from '@/plugins/logger';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import '@/assets/styles.scss';

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

const app = createApp(App);
app.use(router);
app.use(createPinia());
app.use(logger);
app.mount('#app');
