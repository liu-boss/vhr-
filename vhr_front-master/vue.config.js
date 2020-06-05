let proxyObj = {};

//websocket代理
proxyObj['/ws'] = {
    ws:true,
    target: "ws://localhost:9091"
};

proxyObj['/'] = {
    ws: false,
    target: 'http://localhost:9091',
    changeOrigin: true,
    pathRewrite: {
        //不修改拦截路径
        '^/': ''
    }
};

module.exports = {
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: proxyObj
    }
};

