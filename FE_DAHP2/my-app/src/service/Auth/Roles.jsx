import HttpClient from "../../util/HttpClient";

const ROLE_BASE_URL = `/admin/roles`;

// Get ROLE

const GetRole = async ()=>{
      try{
        const response = await HttpClient.get(`${ROLE_BASE_URL}/getroles`)

        const data = response.data
        
        if(data.code === 200){
            const dataRole = data.result
            return dataRole;
        }
        else{
            throw new Error("Không tải được ROLE")
        }
    }
    catch(error){
        throw error.response? error.response.data.code : 500;
    }
}

export {GetRole};