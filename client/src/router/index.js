import { createBrowserRouter } from "react-router-dom"
import { Dashboard, EmployeesRequests, Login, Main, NotFound, Requests, VacationDetails } from "../pages"
import { Users } from "../pages/Users"

const router = createBrowserRouter([
    {
        path: "/",
        element: <Main/>,
        errorElement: <NotFound/>,
        children: [
            {
                path: "/",
                element: <VacationDetails/>
            },
            {
                path: "/requests",
                element: <Requests />
            },
            {
                path: "/employee-requests",
                element: <EmployeesRequests />
            },
            {
                path: "/dashboard",
                element: <Dashboard />
            },
            {
                path: "/users",
                element: <Users />
            }
        ]
    },
    {
        path: "/login",
        element: <Login />
    }
])

export { router }