import { SecuredComponent } from "../provider/authProvider"

export const Header = ({ onMenu, onAccountMenu, onForm }) => <div id="header">
    <div>Vacation Portal</div>
    <SecuredComponent>
        <div className="btn-block">
            <div className="material-symbols-outlined btn" onClick={onForm}>add</div>
            <div className="material-symbols-outlined btn" onClick={onAccountMenu}>account_circle</div>
            <div className="material-symbols-outlined btn" onClick={onMenu}>view_comfy_alt</div>
        </div>
    </SecuredComponent>
</div>