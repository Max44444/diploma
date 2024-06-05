import { axiosInstance } from "./axiosConfig"

const userRoute = process.env.REACT_APP_USER_ROUTE_NAME

export const userService = {
    login: async (email, password) => {
        try {
            const resp = await axiosInstance.post(`${userRoute}/login`, {email, password})
            if (resp.status === 200) return resp.data.token
        } catch (e) {
            console.log(e)
        }
    },

    getManagers: async () => {
        try {
            const resp = await axiosInstance.get(`${userRoute}/managers`)
            if (resp.status === 200) return resp.data
        } catch (e) {
            console.log(e)
        }
        return []
    },
    getInfo: () => axiosInstance.get(userRoute)
}