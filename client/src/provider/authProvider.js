import { createContext, useContext, useEffect, useMemo, useState, } from "react";

const AuthContext = createContext()

export const AuthProvider = ({children}) => {
    const [token, setToken] = useState(localStorage.getItem("token"))

    useEffect(() => {
        if (token) {
            localStorage.setItem('token', token);
        } else {
            localStorage.removeItem('token')
        }
    }, [token])

    const contextValue = useMemo(
        () => ({
            token,
            setToken,
        }),
        [token]
    );

    return (
        <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>
    )
}

export const useAuth = () => {
    return useContext(AuthContext);
}

export const SecuredComponent = ({children}) => {
    const { token } = useAuth()

    return token ? children : <></>
}
