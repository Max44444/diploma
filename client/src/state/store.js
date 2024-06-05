import { configureStore } from '@reduxjs/toolkit'
import { vacationRequestsReducer } from "./vacationRequestsSlice"
import { showSidebarReducer } from "./showSidebarSlice"

const store =  configureStore({
    reducer: {
        vacationRequests: vacationRequestsReducer,
        showSidebar: showSidebarReducer
    }
})

export { store }