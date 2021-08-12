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
        const token = window.localStorage.getItem('ACCESS_TOKEN');
        config.headers.Authorization = token ? token : null;
        console.log(config.headers);
        return config;
    });

    return instance;
};

export const setRefreshToken = async (path, data) => {
    const refreshToken = window.localStorage.getItem('REFRESH_TOKEN');

    await axiosApi.post("/board/" + path,
        {data: data},
        {
            headers: {
                'Refresh_Token': refreshToken
            }
        }
    )
}

export default axiosApi();
