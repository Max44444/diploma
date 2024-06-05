import { useState } from "react"

export const Request = ({id, name, template, startDate, endDate, status, options}) => {
    const [showOptions, setShowOptions] = useState(false)

    const toggleOptions = () => {
        setShowOptions(!showOptions)
    }

    return <div className="vacation-request" key={id}>
        <div className="request-field">
            {name || "-"}
        </div>
        <div className="request-field">
            {template.name}
        </div>
        <div className="request-field">
            {startDate.replaceAll("-", "/")} - {endDate.replaceAll("-", "/")}
        </div>
        <div className="request-field">
            <div className="status-name">{status}</div>
            <div className="material-symbols-outlined menu" onClick={toggleOptions}>more_vert</div>
        </div>
        <div className={`options ${showOptions ? "" : "closed"}`}>
            {
                (options || []).map(({name, action}) => (
                    <div className="option" key={name} onClick={action}>{name}</div>
                ))
            }
        </div>
    </div>
}
