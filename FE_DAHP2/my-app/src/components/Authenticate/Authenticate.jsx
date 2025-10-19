import { Navigate, Outlet, useNavigate } from "react-router-dom";
import {jwtDecode} from "jwt-decode";

const AdminRoute = () =>{
    const navigate = useNavigate();
    try{
        const token = localStorage.getItem("token");
        if(!token){
            return<Navigate to="/auth/token" replace />;
        }
        //  giải mã phần thân ($\text{payload}$) của $\text{token}$ mà không cần signikey
        const decoded = jwtDecode(token);
        // trích xuất thông tin về quyền hạn của người dùng từ đối tượng đã giải mã (decoded).
        const roles = decoded?.roles || decoded?.scope || []
        // includes() để kiểm tra xem mảng đó có chứa chuỗi "ROLE_ADMIN" hay không.
        const isAdmin = Array.isArray(roles)? roles.includes("ROLE_ADMIN"): roles === "ROLE_ADMIN" || roles.includes("ROLE_ADMIN");

        return isAdmin ? <Outlet/> : <Navigate to="/auth/token" replace />;
    }
    catch(error){
        return <Navigate to="/auth/token" replace />;
    }
}

export default AdminRoute