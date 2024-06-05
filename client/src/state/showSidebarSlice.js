import { createSlice } from "@reduxjs/toolkit"

const showSidebarSlice = createSlice({
    name: "showSidebar",
    initialState: { isVisible: false },
    reducers: {
        toggleSidebar: state => state.isVisible = !state.isVisible
    }
})

export const { toggleSidebar } = showSidebarSlice.actions
export const showSidebarReducer = showSidebarSlice.reducer