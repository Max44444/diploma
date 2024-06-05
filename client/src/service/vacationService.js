import { axiosInstance } from "./axiosConfig"

const vacationRoute = process.env.REACT_APP_VACATION_ROUTE_NAME

const getOrDefault = async (callback, value) => {
    try {
        const resp = await callback()
        if (resp.status === 200) return resp.data
    } catch (e) {
        console.error(e)
    }
    return value
}

export const vacationService = {
    approve: async id => {
        try {
            const resp = await axiosInstance.patch(`${vacationRoute}/approve/${id}`)
            if (resp.status === 200) window.location.reload()
        } catch (e) {
            console.error(e)
        }
    },

    cancel: async id => {
        try {
            const resp = await axiosInstance.patch(`${vacationRoute}/cancel/${id}`)
            if (resp.status === 200) window.location.reload()
        } catch (e) {
            console.error(e)
        }
    },

    deny: async id => {
        try {
            const resp = await axiosInstance.patch(`${vacationRoute}/deny/${id}`)
            if (resp.status === 200) window.location.reload()
        } catch (e) {
            console.error(e)
        }
    },

    start: async id => {
        try {
            const resp = await axiosInstance.patch(`${vacationRoute}/start/${id}`)
            if (resp.status === 200) window.location.reload()
        } catch (e) {
            console.error(e)
        }
    },

    create: async data => {
        const resp = await axiosInstance.post(vacationRoute, data)
        if (resp.status === 201) window.location.reload()
    },

    getAll: filters => getOrDefault(
        () => axiosInstance.get(`${vacationRoute}/all`, {params: filters, paramsSerializer: { indexes: null }}),
        []
    ),

    getRemainingDays: () => getOrDefault(
        () => axiosInstance.get(`${vacationRoute}/remaining`),
        []
    ),

    forecast: () => getOrDefault(
        () => axiosInstance.get(`calculation/forecast`),
        {}
    ),

    current: () => getOrDefault(
        () => axiosInstance.get(`calculation/current`),
        {}
    ),

    statistics: () => getOrDefault(
        () => axiosInstance.get(`calculation/statistics`),
        {}
    ),

    getById: id => axiosInstance.get(`${vacationRoute}/${id}`)
}