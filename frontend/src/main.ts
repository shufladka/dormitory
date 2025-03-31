import { createApp } from 'vue';
import App from '@/App.vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import router from '@/utils/router';
import logger from '@/plugins/logger';
import '@/scss/styles.scss';
import * as bootstrap from 'bootstrap';

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

const app = createApp(App);
app.use(router);
app.use(createPinia());
app.use(logger);
app.mount('#app');
