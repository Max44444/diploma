import "./styles/users.scss"
import { useEffect, useState } from "react"
import { userdata } from "./data/userData"

export const Users = () => {

	const [users, setUsers] = useState(userdata.map(data => ({
		...data,
		manager: userdata.find(item => item.id === data.managerId)?.name || ""
	})));

	return <div id="userDataPage">
		<div className="userDataList">
			<div className="user-data-header">
				<h2>User data</h2>
				<button className="create-user-btn">+ Create User</button>
			</div>
			{
				userdata.length ? <>
					<div className="list-header">
						<span className="field-name">ID</span>
						<span className="field-name">Name</span>
						<span className="field-name">Email</span>
						<span className="field-name">Manager</span>
						<span className="field-name">Role</span>
					</div>
					{
						users.map(({id, name, email, manager, role}) => (
							<div className="userData">
								<div className="id">{ id }</div>
								<div className="name">{ name }</div>
								<div className="email">{ email }</div>
								<div className="manager">{ manager }</div>
								<div className="role">{ role }</div>
							</div>
						))
					}
				</> : <div className="no-req"><p>There is no user data...</p></div>
			}
		</div>
	</div>
}