//来源网站https://zhuanlan.zhihu.com/p/336473310

module.exports = {
  devServer: {
    open: true,//打开默认浏览器
    host: 'localhost',
    port: 8080,
    https: false,//忽略https
    //以上的ip和端口是我们本机的;下面为需要跨域的
    proxy: {  //配置跨域
      '/api': {
        target: 'http://localhost:8080',  //这里后台的地址模拟的;应该填写你们真实的后台接口
        ws: true,//代理websocket
        secure: false,//忽略https
        changOrigin: true,  //允许跨域
        pathRewrite: {
          '^/api': ''  //请求的时候使用这个api就可以
        },
}
}
}
}
