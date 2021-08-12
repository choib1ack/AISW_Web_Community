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
        }),
        createProxyMiddleware('/like/press', {
            target: 'http://localhost:8080',
            changeOrigin: true,
        }),
        createProxyMiddleware('/site', {
            target: 'http://localhost:8080',
            changeOrigin: true,
        }),
        createProxyMiddleware('/file/download', {
            target: 'http://localhost:8080',
            changeOrigin: true,
        }),
        createProxyMiddleware('/auth-admin/notice/university', {
            target: 'http://localhost:8080',
            changeOrigin: true,
        }),
        createProxyMiddleware('/auth/free/comment', {
            target: 'http://localhost:8080',
            changeOrigin: true,
        }),
    )
};
