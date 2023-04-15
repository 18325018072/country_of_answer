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
