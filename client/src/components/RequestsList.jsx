import { useEffect, useState } from "react"
import "./styles/vacationList.scss"
import { Request } from "./Request"

export const RequestsList = ({header, getVacations, showEmployee = false, getOptions}) => {

    const [requests, setRequests] = useState([])

    useEffect(() => {
        getVacations()
            .then(requests => requests.toSorted((a, b) => (Date.parse(b.startDate) - Date.parse(a.startDate))))
            .then(setRequests)
    }, [getVacations]);

    return <div className="vacation-list">
        <h2>{header}</h2>
        {
            requests.length ? <>
                    <div className="list-header">
                        <span className="field-name">{showEmployee ? "employee" : "approver"}</span>
                        <span className="field-name">type</span>
                        <span className="field-name">date</span>
                        <span className="field-name">status</span>
                    </div>
                    {
                        requests.map(({id, approver, employee, startDate, endDate, status, template}) => (
                            <Request key={id} id={id}
                                     name={showEmployee ? employee?.name : approver?.name}
                                     template={template}
                                     startDate={startDate}
                                     endDate={endDate}
                                     status={status}
                                     options={getOptions(id, status)} />
                        ))
                    }
                </>
                : <div className="no-req"><p>There are no requests yet...</p></div>
        }
    </div>
}