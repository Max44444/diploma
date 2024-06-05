import { vacationService } from "../service"
import { RequestsList } from "../components"

export const EmployeesRequests = () => {
    const getVacations = async () => {
        return await vacationService.getAll({
            approver: "me",
            statuses: ["REQUIRES_APPROVAL"]
        })
    }

    const getOptions = (id, status) => {
        const options = []
        if (status === "REQUIRES_APPROVAL") {
            options.push({name: "Approve", action: () => vacationService.approve(id)})
            options.push({name: "Deny", action: () => vacationService.deny(id)})
        }
        return options
    }

    return <div id="requests">
        <RequestsList header="Employee Requests"
                      getVacations={getVacations}
                      getOptions={getOptions}
                      showEmployee={true} />
    </div>
}