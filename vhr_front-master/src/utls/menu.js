import {getRequest} from './api'
import da from "element-ui/src/locale/lang/da";

export const initMenu = (router, store) => {
    //先判断，store的state中的数值是否有值
    if (store.state.routes.length > 0) {
        return;
    }
    //如果没有值，从后台中获取数据
    getRequest("/system/config/menu").then(data => {
        if (data) {
            let initRequest = formatRouter(data);
            router.addRoutes(initRequest);
            store.commit('initRoutes', initRequest)
        }
    })
}

// 格式化菜单
    export const formatRouter = (data) => {
        let fmRouters = [];
        //将菜单
        data.forEach(router => {
            //定义路由对象的字段
            let {
                path,
                component,
                name,
                meta,
                iconCls,
                children
            } = router;

            //判断是否存在子节点菜单，如果存在将其遍历
            if (children && children instanceof Array) {
                children = formatRouter(children);
            }

            let fmRouter = {
                path: path,
                name: name,
                meta: meta,
                iconCls: iconCls,
                children: children,
                component(resolve) {
                    if (component.startsWith('Home')) {
                        require(['../views/' + component + '.vue'], resolve);
                    }else if (component.startsWith('Emp')) {
                        require(['../views/emp/' + component + '.vue'], resolve);
                    }else if(component.startsWith('Per')){
                        require(['../views/per/' + component + '.vue'], resolve);
                    }else if(component.startsWith('Sal')){
                        require(['../views/sal/' + component + '.vue'], resolve);
                    }else if(component.startsWith('Sta')){
                        require(['../views/sta/' + component + '.vue'], resolve);
                    }else if(component.startsWith('Sys')){
                        require(['../views/sys/' + component + '.vue'], resolve);
                    }
                }
            };
            fmRouters.push(fmRouter);
        });

        return fmRouters;
    }



