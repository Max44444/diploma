import React from 'react'
import ReactDOM from 'react-dom/client'
import { Provider } from 'react-redux'
import { RouterProvider } from 'react-router-dom'
import './index.css'
import { router } from "./router"
import { store } from "./state"
import { AuthProvider } from "./provider/authProvider"

const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
    <React.StrictMode>
        <AuthProvider>
            <Provider store={store}>
                <RouterProvider router={router}/>
            </Provider>
        </AuthProvider>
    </React.StrictMode>
);
