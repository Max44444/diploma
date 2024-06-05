import { createSlice } from "@reduxjs/toolkit"

const vacationRequestsSlice = createSlice({
    name: "vacationRequests",
    initialState: [],
    reducers: {
        addRequest: (requests, request) => requests.push(request),
        updateRequest: (requests, request) => requests.forEach((item, idx) => {
            if (item.id === request.id) requests[idx] = request
        })
   }
})

export const { addRequest, updateRequest } = vacationRequestsSlice.actions
export const vacationRequestsReducer = vacationRequestsSlice.reducer