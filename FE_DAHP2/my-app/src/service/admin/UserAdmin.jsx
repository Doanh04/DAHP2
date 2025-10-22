import HttpClient from "../../util/HttpClient";

const USER_BASE_URL = "/admin/user";

// Hàm getall user

const GetAllUser = async()=>{
    try{
        const response = await HttpClient.get(`${USER_BASE_URL}/getalluser`)

        const data = response.data
        
        if(data.code === 1000){
            const dataAccout = data.result
            return dataAccout;
        }
        else{
            throw new Error("Không tải được danh sách tài khoản")
        }
    }
    catch(error){
        throw error.response? error.response.data.code : 500;
    }
}

// PUT USER
const UpdateUser = async (values) => {
    const iduser = values.userId; 
    if (!iduser) {
        throw new Error("Missing userId in payload. Cannot form API URL.");
    }
    
    try {
        const response = await HttpClient.put(`${USER_BASE_URL}/updateuser/${iduser}`, {
            name: values.name,
            email: values.email,
            phone: values.phone,
            address: values.address,
            roles: values.roles // gửi đi dạng chuỗi

        });

        const data = response.data;

        if (data.code === 1000) {
            return { success: true, data: response.data };
        } else {
            throw new Error(data.message || "Cập nhật tài khoản không thành công");
        }
    } catch (error) {
        throw new Error(error.response ? error.response.data.message : "Lỗi mạng hoặc server không phản hồi");
    }
};
// Delete USER
const DeleteUser = async (idUser)=>{
    try{
        const response = await HttpClient.delete(`${USER_BASE_URL}/deleteUser/${idUser}`);

        const result = response.data;

        if(result.code === 200){
            return result
        }
        else {
            throw new Error(data.message || "Xóa tài khoản không thành công");
        }
    }
    catch(error){
        throw new Error(error.success ? error.response.data.success : "Lỗi mạng hoặc server không phản hồi");
    }
}
// Search UserName
const FindByUserName = async(userName) =>{
    try{
        const response = await HttpClient.get(`${USER_BASE_URL}/findByUserName/${userName}`);
        const result = response.data
        if(result.code = 1000){
            return result.result
        }
        else {
            throw new Error(data.error || "không tìm thấy tài khoản tương ứng");
        }
    }
    catch(error){
        throw new Error(error.success ? error.response.data.error : "Lỗi mạng hoặc server không phản hồi");
    }
}

export {GetAllUser, UpdateUser, DeleteUser, FindByUserName};