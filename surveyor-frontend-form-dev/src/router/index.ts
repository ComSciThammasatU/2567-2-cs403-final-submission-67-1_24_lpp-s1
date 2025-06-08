import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '../views/LoginView.vue';
import RegisterView from '../views/RegisterView.vue';
import WorkSpaceView from '../views/WorkSpaceView.vue';
import HomeView from '../views/HomeView.vue';
import DocumentToDoList from '../views/survey/DocumentToDoList.vue';
import DocumentHistory from '../views/survey/DocumentHistory.vue';
import DocumentInfo from '../views/survey/DocumentInfo.vue';
import DocumentForm from '../views/survey/DocumentForm.vue';

const router = createRouter({
    history: createWebHistory(),

    routes: [
        {
            path: '/',
            name: 'workspace',
            component: WorkSpaceView,
            meta: { bodyClass: 'hold-transition sidebar-mini layout-fixed sidebar-collapse tex-sm' },

            children: [
                {
                    path: '/',
                    name: 'home',
                    component: HomeView,
                },

                {
                    path: '/survey/to-do-list',
                    name: 'survey.to_do_list',
                    component: DocumentToDoList,
                },
        
                {
                    path: '/survey/history',
                    name: 'survey.history',
                    component: DocumentHistory,
                },
        
                {
                    path: '/survey/info',
                    name: 'survey.info',
                    component: DocumentInfo,
                },
        
                {
                    path: '/survey/form',
                    name: 'survey.form',
                    component: DocumentForm,
                }
            ]
        },

        {
            path: '/register',
            name: 'register',
            component: RegisterView,
            meta: { bodyClass: 'hold-transition register-page' }
        },

        {
            path: '/login',
            name: 'login',
            component: LoginView,
            meta: { bodyClass: 'hold-transition login-page' }
        }
    ],
});

router.beforeEach((to, from, next) => {
    document.body.style = "";
    
    if (to.meta.bodyClass) {
        document.body.className = to.meta.bodyClass as string;
    } else {
        document.body.className = ''; // ล้าง class อื่นๆ ถ้าไม่มีกำหนด
    }
    next();
});

export default router;