import { useMemo } from "react"
import { Chart } from "react-charts"
import moment from "moment"

export const Line = ({ data, type }) => {
	const primaryAxis = useMemo(() => ({getValue: datum => datum.primary, elementType: "linear"}), []);
	const secondaryAxes = useMemo(() => [{getValue: datum => datum.secondary, elementType: type}],[]);

	return (
		<>
			<br />
			<br />
			<Chart
				options={{
					data,
					primaryAxis,
					secondaryAxes,
				}}
			/>
		</>
	);
}