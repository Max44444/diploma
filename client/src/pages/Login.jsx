import { useAuth } from "../provider/authProvider"
import { useNavigate } from "react-router-dom"
import { Header, LoginForm } from "../components"
import "./styles/login.scss"
import { userService } from "../service"

export const Login = () => {
    const { setToken } = useAuth()
    const navigate = useNavigate()

    const handleLogin = async (email, password) => {
        const token = await userService.login(email, password)
        if (token) {
            setToken(token)
            navigate("/", { replace: true })
        }
    }

    return <div id="login">
        <div className="bg-img"></div>
        <Header />
        <LoginForm onLogin={handleLogin} />
    </div>
}