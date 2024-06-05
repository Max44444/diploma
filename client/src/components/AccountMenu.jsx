
export const AccountMenu = ({isVisible, onLogout}) =>
    <div className={`account-menu ${!isVisible && "closed"}`}>
        <div className="menu-btn">User Info</div>
        <div className="leave menu-btn" onClick={onLogout}>Log Out</div>
    </div>