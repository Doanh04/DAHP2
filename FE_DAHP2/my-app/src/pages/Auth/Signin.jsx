import { message } from "antd";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import SigninLayout from "../../components/UI/AuthComponent/SigninLayout";
import "../../style/AuthScss/Signin.scss";
import { AuthServiceCreateUser } from "../../service/Auth/AuthService";
import { Navigate } from "react-router-dom";

function Signin() {
  const [loading, setLoading] = useState(false);
  const [formValue, setFormValue] = useState({});
  const [messageError, contextHolder] = message.useMessage();
  const info = () => {
    messageError.error("Tài khoản đã được sử dụng");
  };
  const navigate = useNavigate();

  // nhận dữ liệu khi form có thay đổi
  const handleChange = (changeValues, allValues) => {
    setFormValue(allValues);
  };
  //Submit form
  const handleSubmit = async (values) => {
    const { username, name, email, password, phone, address } = values;
    setLoading(true);
    try {
      const response = await AuthServiceCreateUser(
        username,
        name,
        email,
        password,
        phone,
        address
      );
      if (response.code === 1000) {
        messageError.success("Đăng ký thành công!", 2).then(() => {
          navigate("/auth/token");
        });
      } else {
        messageError.error(response.message || "Đăng ký thất bại");
      }
      setLoading(false);
    } catch (error) {
      setLoading(false);
      let errorMessageToDisplay = "Lỗi đăng ký vui lòng liên hệ với Admin";
      if (error.code === 1004 || error.code === 400) {
        // Sử dụng thông báo chi tiết từ Server
        errorMessageToDisplay = error.message;
      } else if (error.message) {
        // Lỗi kết nối mạng
        errorMessageToDisplay = error.message;
      }
      messageError.error(errorMessageToDisplay);
    }
  };
  //reset
  const handleReset = () => {
    setFormValue({});
  };
  const formProp = {
    loading,
    formValue,
    contextHolder,
    onFinish: handleSubmit,
    onchange: handleChange,
    onreset: handleReset,
  };

  return (
    <>
      <SigninLayout {...formProp} />
    </>
  );
}
export default Signin;
