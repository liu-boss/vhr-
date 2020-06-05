import axios from 'axios'
import {Message} from 'element-ui'
import router  from "../router";

axios.interceptors.response.use(
    success => {
       if(success.status && success.status==200 && success.data.status==500){
           Message.error(success.data.msg);
           return;
       }
       if(success.data.msg){
           //alert(success.data.msg);
           Message.success(success.data.msg);
       }
       return success.data;
},error => {

        if (error.response.status==404 || error.response.status==504){
            Message.error("服务器被吃啦!🤣");
        }else if(error.response.status==403){
            Message.error("权限不足，请联系管理员!");
        }else if(error.response.status==401){
            Message.error("未登入，请重新登入!");
            //跳转至登入界面
            router.replace('/');
        }else {
            if (error.response.data.msg) {
                Message.error( error.response.data.msg);
            }else {
                Message.error("未知错误!");
            }
        }
        return;
})


let base='';
export const postKeyValueRequest= (url,params) =>{
    return axios({
        method:'post',
        //url:'${base}${url}',
        url:url,
        data:params,
        transformRequest:[
            function (data) {
                let ret='';
                for (let i in data){
                    ret+=encodeURIComponent(i)+'='+encodeURIComponent(data[i])+'&';
                }
                 console.log("ret"+ret);
                return ret;
            }
        ],
        headers:{
            'Content-Type':'application/x-www-form-urlencoded'
        }

    })
}

//post请求
export const postRequest=(url,params)=>{
     return axios({
         method: 'post',
         url:url,
         data:params
     })
}


//put请求
export const putRequest=(url,params)=>{
    return axios({
        method: 'put',
        url:url,
        data:params
    })
}


//delete请求
export const deleteRequest=(url,params)=>{
    return axios({
        method: 'delete',
        url:url,
        data:params
    })
}

//get请求
export const getRequest=(url,params)=>{
    return axios({
        method: 'get',
        url:url,
        data:params
    })
}