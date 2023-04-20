import {createApp} from 'vue'
import {createPinia} from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import 'dayjs/locale/zh-cn'
import App from './App.vue'
import router from './router'

import '@/assets/global.css'

const app = createApp(App)
    .use(createPinia())
    .use(router)
    .use(ElementPlus, {locale: zhCn})

app.mount('#app')