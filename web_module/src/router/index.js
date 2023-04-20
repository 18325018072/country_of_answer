import {createRouter, createWebHistory} from 'vue-router'
import HomeView from "@/views/HomeView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'instruction',
            component: () => import('../views/InstructionView.vue')
        },
        {
            path: '/home',
            name: 'home',
            component: HomeView
        },
        {
            path: '/test/:testId/summary',
            name: 'test-summary',
            component: () => import('../views/TestSummaryView.vue')
        },
        {
            path: '/test/:testId/testing',
            name: 'testing',
            component: () => import('../views/TestingView.vue')
        },
        {
            path: '/vue-test',
            name: 'vue-test',
            component: () => import('../views/VueTestView.vue')
        }
    ]
})

export default router
