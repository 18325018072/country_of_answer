import {createApp} from 'vue'
import {createPinia} from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router from './router'

import './assets/base.css'

const app = createApp(App)
    .use(createPinia())
    .use(router)
    .use(ElementPlus)

app.mount('#app')