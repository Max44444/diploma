import moment from "moment"
import {Day} from "./Day"

const Days = ({ date, startDate, endDate, onClick }) => {
    const thisDate = moment(date);
    const daysInMonth = moment(date).daysInMonth();
    const firstDayDate = moment(date).startOf("month");
    const previousMonth = moment(date).subtract(1, "month");
    const previousMonthDays = previousMonth.daysInMonth();
    const nextMonth = moment(date).add(1, "month");
    let days = [];
    let labels = [];

    for (let i = 1; i <= 7; i++) {
        labels.push(<span className="label">{moment().day(i).format("ddd")}</span>);
    }

    for (let i = firstDayDate.day(); i > 1; i--) {
        previousMonth.date(previousMonthDays - i + 2);

        days.push(
            <Day
                key={moment(previousMonth).format("DD MM YYYY")}
                onClick={(date) => onClick(date)}
                currentDate={date}
                date={moment(previousMonth)}
                startDate={startDate}
                endDate={endDate}
            />
        );
    }

    for (let i = 1; i <= daysInMonth; i++) {
        thisDate.date(i);

        days.push(
            <Day
                key={moment(thisDate).format("DD MM YYYY")}
                onClick={(date) => onClick(date)}
                currentDate={date}
                date={moment(thisDate)}
                startDate={startDate}
                endDate={endDate}
            />
        );
    }

    const daysCount = days.length;
    for (let i = 1; i <= 42 - daysCount; i++) {
        nextMonth.date(i);
        days.push(
            <Day
                key={moment(nextMonth).format("DD MM YYYY")}
                onClick={(date) => onClick(date)}
                currentDate={date}
                date={moment(nextMonth)}
                startDate={startDate}
                endDate={endDate}
            />
        );
    }

    return (
        <nav className="calendar--days">
            {labels.concat()}
            {days.concat()}
        </nav>
    );
};