import { useEffect, useState } from "react"

export const RemainingVacations = ({getRemainingDays}) => {
    const [remainingDays, setRemainingDays] = useState([])

    useEffect(() => {
        getRemainingDays()
            .then(setRemainingDays)
    }, [getRemainingDays]);

    return <div className="remaining-vacations">
        {
            remainingDays.map(({id, template, days, remainingDays}) => (
                <div className="remaining-vac" key={id}>
                    <h2>{template.name}</h2>
                    <div className="counter">
                        <div>{remainingDays}/{days}</div>
                    </div>
                    <p>Remaining Vacation Days</p>
                </div>
            ))
        }
    </div>
}