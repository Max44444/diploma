import { RemainingVacations, RequestsList } from "../components"
import "./styles/vacation-details.scss"
import { vacationService } from "../service"

export const VacationDetails = () => {
    const getVacations = async () => {
        return await vacationService.getAll({
            employee: "me",
            statuses: ["CREATED", "REQUIRES_APPROVAL", "APPROVED", "REQUIRES_ACTION", "IN_PROGRESS"]
        })
    }

    const getRemainingDays = async () => {
        return await vacationService.getRemainingDays()
    }

    const getOptions = (id, status) => {
        const options = []
        if (status === "REQUIRES_ACTION") options.push({ name: "Start", action: () => vacationService.start(id) })
        if (status !== "IN_PROGRESS" || status !== "CANCELLED" || status !== "CLOSED")
            options.push({ name: "Cancel", action: () => vacationService.cancel(id) })
        return options
    }

    return <div id="vacation-details">
        <RemainingVacations getRemainingDays={getRemainingDays} />
        <RequestsList header="Open Requests"
                      getVacations={getVacations}
                      getOptions={getOptions} />
    </div>
}