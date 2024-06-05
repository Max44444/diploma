import { useId, useState } from "react"

export const LoginForm = ({onLogin}) => {
    const emailInputId = useId()
    const passwordInputId = useId()
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    return <div className="login-box">
        <h2>Login</h2>
        <div className="form">
            <div className="user-box">
                <input id={emailInputId}
                       type="email"
                       name="email"
                       value={email}
                       onChange={e => setEmail(e.target.value)}
                       required={true}/>
                <label htmlFor={emailInputId}>Email</label>
            </div>
            <div className="user-box">
                <input id={passwordInputId}
                       type="password"
                       name="password"
                       value={password}
                       onChange={e => setPassword(e.target.value)}
                       required={true}/>
                <label htmlFor={passwordInputId}>Password</label>
            </div>
            <button className="submit-btn" onClick={() => onLogin(email, password)}>Log In</button>
        </div>
    </div>
}