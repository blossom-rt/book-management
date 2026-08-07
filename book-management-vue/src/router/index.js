import {createRouter, createWebHistory} from 'vue-router'
 
const router=createRouter({
  history: createWebHistory(),
  routes:[
    { path:'/',  redirect:'/manage/home'  },
    { path:'/manage',  component:()=>import('../views/Manage.vue'),  children:[
      { path:'home',  name:'home',  component:()=>import('../views/Home.vue')  },
      { path:'test',  name:'test',  component:()=>import('../views/Test.vue')  },
      { path:'book',  name:'book',  component:()=>import('../views/Book.vue')  },
    ]  },
    { path:'/404',  name:'NotFound',  component:()=>import('../views/404.vue')  },
    { path: '/:pathMatch(.*)', redirect: '/404' }

    
  ]
})
 
export  default router