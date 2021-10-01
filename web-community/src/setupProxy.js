const {createProxyMiddleware} = require('http-proxy-middleware');

module.exports = function (app) {
    app.use(
        createProxyMiddleware('/board/comment', {
            target: 'http://13.125.104.47',
            changeOrigin: true,
        }),
        createProxyMiddleware('/user/signup', {
            target: 'http://13.125.104.47',
            changeOrigin: true,
        }),
        createProxyMiddleware('/user/login', {
            target: 'http://13.125.104.47',
            changeOrigin: true,
        }),
        createProxyMiddleware('/like/press', {
            target: 'http://13.125.104.47',
            changeOrigin: true,
        }),
        createProxyMiddleware('/site', {
            target: 'http://13.125.104.47',
            changeOrigin: true,
        }),
        createProxyMiddleware('/file/download', {
            target: 'http://13.125.104.47',
            changeOrigin: true,
        }),
        createProxyMiddleware('/auth-admin/notice/university', {
            target: 'http://13.125.104.47',
            changeOrigin: true,
        }),
        createProxyMiddleware('/auth/free/comment', {
            target: 'http://13.125.104.47',
            changeOrigin: true,
        }),
        createProxyMiddleware('/home', {
            target: 'http://13.125.104.47',
            changeOrigin: true,
        }),
    )
};
