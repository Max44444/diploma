import { DateRangePicker } from "react-date-range"
import Dropdown from "react-dropdown"
import { useEffect, useState } from "react"
import moment from "moment"

export const CreateVacationForm = ({showForm, toggleForm, getApprovers, getTypes, onCreate}) => {
    const [availableApprovers, setAvailableApprovers] = useState([])
    const [availableTypes, setAvailableTypes] = useState([])

    const [startDate, setStartDate] = useState(new Date())
    const [endDate, setEndDate] = useState(new Date())
    const [approver, setApprover] = useState(null)
    const [type, setType] = useState(null)

    useEffect(() => {
        getApprovers().then(setAvailableApprovers)
        getTypes().then(setAvailableTypes)
    }, [getApprovers, getTypes]);

    const onSelect = range => {
        setStartDate(range.selection.startDate)
        setEndDate(range.selection.endDate)
    }

    const onSubmit = () => {
        if (approver && type) {
            console.log(approver)
            console.log(availableApprovers)
            const {email} = availableApprovers.find(a => a.name === approver.value)
            const {template} = availableTypes.find(t => t.template.name === type.value)
            onCreate({
                approverEmail: email,
                templateId: template.id,
                startDate: moment(startDate).format("YYYY-MM-DD"),
                endDate: moment(endDate).format("YYYY-MM-DD")
            })
        }
    }

    const selectionRange = {
        startDate,
        endDate,
        key: 'selection'
    }

    return <div id="create-vac-form" className={showForm ? "" : "closed"}>
        <div className="shade"></div>
        <div className="form">
            <span className="material-symbols-outlined close-btn" onClick={toggleForm}>close</span>
            <h2>Vacation Dates</h2>
            <DateRangePicker ranges={[selectionRange]}
                             onChange={onSelect} months={2}
                             rangeColors={["#00bfff"]}
                             minDate={new Date()}
                             direction={"horizontal"} />

            <h2>Vacation Type</h2>
            <Dropdown options={availableTypes.map(t => t.template.name)}
                      placeholder="Select Vacation Type"
                      onChange={setType}
                      value={type} />

            <h2>Approver</h2>
            <Dropdown options={availableApprovers.map(a => a.name)}
                      placeholder="Select Approver"
                      onChange={setApprover}
                      value={approver} />

            <button className="submit-btn" onClick={onSubmit}>Create</button>
        </div>
    </div>
}