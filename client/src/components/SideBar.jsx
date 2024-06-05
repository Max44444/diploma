import { useNavigate } from "react-router-dom"

export const SideBar = ({showSidebar, toggleSidebar, items}) => {
    const navigate = useNavigate()

    const onNavigate = path => () => {
        navigate(path)
        toggleSidebar()
    }

    return <div id="sidebar" className={showSidebar ? "" : "closed"}>
        <div className="shade"></div>
        <div className="content">
            {
                (items || []).map(({name, path}) => (
                    <div key={name} className="content-tem" onClick={onNavigate(path)}>{name}</div>
                ))
            }
        </div>
    </div>
}