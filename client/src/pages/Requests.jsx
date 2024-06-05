import { RequestsList } from "../components"
import "./styles/requests.scss"
import { vacationService } from "../service"

export const Requests = () => {
    const getVacations = async () => {
        return await vacationService.getAll({
            employee: "me"
        })
    }

    const getOptions = (id, status) => {
        const options = []
        if (status === "REQUIRES_ACTION") options.push({ name: "Start", action: () => vacationService.start(id) })
        if (status !== "IN_PROGRESS" || status !== "CANCELLED" || status !== "CLOSED")
            options.push({ name: "Cancel", action: () => vacationService.cancel(id) })
        return options
    }

    return <div id="requests">
        <RequestsList header="Your Vacation Requests"
                      getVacations={getVacations}
                      getOptions={getOptions} />
    </div>
}