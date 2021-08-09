import axios from 'axios'

const axiosApi = () => {
    const defaultOptions = {
        baseURL: 'http://localhost:8080/',
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // Create instance
    let instance = axios.create(defaultOptions);

    // Set the AUTH token for any request
    instance.interceptors.request.use(function (config) {
        let token = window.localStorage.getItem('ACCESS_TOKEN');
        config.headers.Authorization = token ? token : '';
        return config;
    });

    return instance;
};

export default axiosApi();
