import { Navigate, Outlet, useNavigate } from "react-router-dom";
import {jwtDecode} from "jwt-decode";
import { decodeTokenAndCheckRole } from "../../util/AuthUltil";

const AdminRoute = () =>{
    const navigate = useNavigate();
    try{
        const token = localStorage.getItem("token");
        const decode = decodeTokenAndCheckRole(token);

        if(!decode){
             return <Navigate to="/auth/token" replace />;
        }

        if(decode.isAdmin){
            return <Outlet />;
        }
    }
    catch(error){
        return <Navigate to="/auth/token" replace />;
    }
}

export default AdminRoute