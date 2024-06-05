import axios from "axios";

const host = process.env.REACT_APP_SERVER_HOST

const axiosInstance = axios.create({
    baseURL: host,
    timeout: 2000,
    headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
    }
})

axiosInstance.interceptors.request.use(req => {
    req.headers.Authorization = `Bearer ${localStorage.getItem("token")}`
    return req
})

export { axiosInstance }