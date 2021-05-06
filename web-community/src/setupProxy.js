const {createProxyMiddleware} = require('http-proxy-middleware');

module.exports = function (app) {
    app.use(
        createProxyMiddleware('/board/comment', {
            target: 'http://localhost:8080',
            changeOrigin: true,
        }),
        createProxyMiddleware('/user/signup', {
            target: 'http://localhost:8080',
            changeOrigin: true,
        }),
        createProxyMiddleware('/user/login', {
            target: 'http://localhost:8080',
            changeOrigin: true,
        })
        ,
        createProxyMiddleware('/like/press', {
            target: 'http://localhost:8080',
            changeOrigin: true,
        })
    )
};
