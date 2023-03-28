import {createRouter, createWebHistory} from 'vue-router'
import InstructionView from '../views/InstructionView.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'instruction',
            component: InstructionView
        },
        {
            path: '/home',
            name: 'home',
            component: () => import('../views/HomeView.vue')
        },
        {
            path: '/test/:testId',
            name: 'test',
            component: () => import('../views/TestView.vue')
        }
    ]
})

export default router
