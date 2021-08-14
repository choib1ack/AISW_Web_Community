import axios from 'axios';

const axiosApi = () => {
    const defaultOptions = {
        baseURL: 'http://localhost:8080/',
        headers: {
            'Content-Type': 'application/json',
        },
    };

    let instance = axios.create(defaultOptions);

    // Access Token 설정
    instance.interceptors.request.use(function (config) {
        const token = window.localStorage.getItem('ACCESS_TOKEN');
        config.headers.Authorization = token ? token : null;
        return config;
    });

    instance.interceptors.response.use(
        (res) => {
            return res;
        },
        async (err) => {
            const originalConfig = err.config;

            // Refresh Token 만료되었을 때
            if (err.config.url === '/auth/refresh' || err.response.data.error === 'RefreshJwtTokenExpired') {
                window.localStorage.clear();

                if (!alert('로그인 기간이 만료되었습니다!')) {
                    document.location.href = '/';
                }

                return new Promise((resolve, reject) => {
                    reject(err);
                });
            }

            // Access Token 만료되었을 때
            if (err.response.status === 400 && !originalConfig._retry) {
                if (err.response.data.error === 'JwtTokenExpired') {
                    originalConfig._retry = true;

                    try {
                        const response = await instance.get("/auth/refresh", {
                            headers: {
                                'Refresh_Token': window.localStorage.getItem('REFRESH_TOKEN')
                            },
                        });

                        window.localStorage.setItem('ACCESS_TOKEN', response.headers['Authorization']);
                        return instance(originalConfig);
                    } catch (_error) {
                        return Promise.reject(_error);
                    }
                }
            }

            // 접근권한 없을 때
            if (err.response.status === 403) {
                if (!alert('접근 권한이 없습니다!')) {
                    window.history.back();
                }
            }

            return Promise.reject(err);
        }
    );

    return instance;
};

export default axiosApi();
