import "./styles/dashboard.scss"
import { Line } from "../components/Line"
import moment from "moment/moment"
import { useEffect, useState } from "react"
import { vacationService } from "../service"

export const Dashboard = () => {
    const [forecast, setForecast] = useState([])
    const [current, setCurrent] = useState([])
    const [statistics, setStatistics] = useState([])

    const months = {
        JANUARY: 1,
        FEBRUARY: 2,
        MARCH: 3,
        APRIL: 4,
        MAY: 5,
        JUNE: 6,
        JULY: 7,
        AUGUST: 8,
        SEPTEMBER: 9,
        OCTOBER: 10,
        NOVEMBER: 11,
        DECEMBER: 12
    }

    const toArray = obj => {
        const arr = Object.keys(obj).map(key => ({ primary: key, secondary: obj[key] }))
        arr.sort((a, b) => months[a.primary] - months[b.primary])
        return arr
    }

    const convertStatistics = statistics => {
        return Object.keys(statistics)
            .map(key => ({
                label: key,
                data: statistics[key].map(({country, days}) => ({primary: country, secondary: days}))
            }))
    }

    useEffect(() => {
        vacationService.forecast()
            .then(toArray)
            .then(setForecast)
        vacationService.current()
            .then(toArray)
            .then(setCurrent)
        vacationService.statistics()
            .then(convertStatistics)
            .then(setStatistics)
    }, []);

    const data = [
        {
            label: "Current",
            data: current
        },
        {
            label: "Forecast",
            data: forecast
        }
    ]

    return <div id="dashboard">
        <h2>Vacations Forecast</h2>
        <div className="line-chart">
            { forecast.length && current.length && <Line data={data} type="line" /> }
        </div>
        <h2>Taken Vacations For Countries</h2>
        <div className="line-chart">
            { statistics.length && <Line data={statistics}/> }
        </div>
    </div>
}