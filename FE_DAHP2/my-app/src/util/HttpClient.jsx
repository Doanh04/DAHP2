import axios from 'axios';

//http mặc định
 const HttpClient = axios.create({
    baseURL: 'http://localhost:8080',
});

//Gắn token vào cho request và localstorage
HttpClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");
        if(token){
            config.headers["Authorization"] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
)

// Xử lý lỗi response
HttpClient.interceptors.response.use(
    (response) => response,
    (error) => {
        // xử lý token hết hạn hoặc không hợp lệ
        if(error.response && error.response.status === 401){
            localStorage.removeItem("token");
            window.location.href = "/auth/token";
        }
        return Promise.reject(error);
    }
);
export default HttpClient;