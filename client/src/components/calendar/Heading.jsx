export const Heading = ({date, changeMonth, resetDate}) => {
    const onChange = () => changeMonth(date.month() - 1)

    return <nav className="calendar--nav">
        <a onClick={onChange}>&#8249;</a>
        <h1 onClick={resetDate}>
            {date.format("MMMM")} <small>{date.format("YYYY")}</small>
        </h1>
        <a onClick={() => changeMonth(date.month() + 1)}>&#8250;</a>
    </nav>
}