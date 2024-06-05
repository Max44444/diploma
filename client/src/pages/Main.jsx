import { Navigate, Outlet } from "react-router-dom"
import { AccountMenu, CreateVacationForm, Header, SideBar } from "../components"
import "react-date-range/dist/styles.css"
import "react-date-range/dist/theme/default.css"
import "./styles/main.scss"
import { useState } from "react"
import { useAuth } from "../provider/authProvider"
import { userService, vacationService } from "../service"


export const Main = () => {
    const [isSidebarVisible, setIsVisible] = useState(false)
    const [isAccountMenuVisible, setIsMenuVisible] = useState(false)
    const [isFormVisible, setIsFormVisible] = useState(false)

    const toggleSidebar = () => setIsVisible(!isSidebarVisible)
    const toggleMenu = () => setIsMenuVisible(!isAccountMenuVisible)
    const toggleForm = () => setIsFormVisible(!isFormVisible)

    const { token, setToken } = useAuth()

    if (!token) return <Navigate to="/login" />

    const logout = () => setToken("")

    const menuItems = [
        {name: "Main Page", path: "/"},
        {name: "Dashboard", path: "/dashboard"},
        {name: "My Requests", path: "/requests"},
        {name: "Employees Requests", path: "/employee-requests"},
        {name: "Users", path: "/users"}
    ]

    const onVacationCreate = data => {
        vacationService.create(data)
        toggleForm()
    }

    return <div id="main">
        <Header onMenu={toggleSidebar} onAccountMenu={toggleMenu} onForm={toggleForm}/>
        <SideBar showSidebar={isSidebarVisible} toggleSidebar={toggleSidebar} items={menuItems}/>
        <AccountMenu isVisible={isAccountMenuVisible} onLogout={logout} />
        <CreateVacationForm toggleForm={toggleForm}
                            showForm={isFormVisible}
                            getApprovers={userService.getManagers}
                            getTypes={vacationService.getRemainingDays}
                            onCreate={(onVacationCreate)} />
        <Outlet/>
    </div>
}
