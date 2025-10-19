import HttpClient from "../../util/HttpClient.jsx";

const AUTH_BASE_URL = '/auth';

// Hàm đăng nhập
const AuthServiceLogin = async (username, password) => { 
    try{
        const response = await HttpClient.post(`${AUTH_BASE_URL}/token`,{
            username,
            password
        });
        //nhân token từ response trả về dưới dạng json
        const data = response.data;
        
        if(data.code === 1000 && data.result?.token){
            const token = data.result.token;
            localStorage.setItem("token", token);
            localStorage.setItem("username", JSON.stringify(response.data.result.username));
            localStorage.setItem("name", JSON.stringify(response.data.result.fullName));
            return response.data;
        }
        else{
           throw new Error("Sai tài khoản hoặc mật khẩu");
        }
    }
    catch(error){
        return error.response ? error.response.data.code : 500;
    }
};
//Hàm đăng ký
const AuthServiceCreateUser = async (username, name, email, password, phone, address) => {
    try {
    const response = await HttpClient.post(`${AUTH_BASE_URL}/createuser`, {
      username,
      name,
      email,
      password,
      phone,
      address
    });
    return response.data;
  } catch (error) {
    console.error("Lỗi đăng ký:", error);
        
    const customError = new Error(error.response?.data?.message || "Lỗi hệ thống");
        customError.code = error.response?.data?.code || 500;
        throw customError;
  }
}
// Hàm đăng xuất
const AuthServiceLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    window.location.href = "/auth/token";
}

export {AuthServiceLogin, AuthServiceLogout, AuthServiceCreateUser};