import { Form, message } from "antd";
import { Input } from "antd";
import { Button } from "antd";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthServiceLogin } from "../../service/Auth/AuthService";
import banner from "../../imgs/banner.png"
import "../../style/AuthScss/Login.scss";
function Login() {
  const navigate = useNavigate();
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      navigate("/");
    }
  }, [navigate]);

  const [loading, setLoading] = useState(false);
  const [loginError, setLoginError] = useState(null);

  const onFinish = async (values) => {
    const { username, password } = values;
    setLoading(true);

    try {
      const response = await AuthServiceLogin(username, password);
      if (response.code === 1000) {
        navigate("/");
      } else {
        setLoginError("Tài khoản hoặc mật khẩu không chính xác");
      }
    } catch (error) {
      setLoginError("Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.");
    } finally {
      setLoading(false);
    }

    // console.log(Link)
  };

  return (
    <>
      <div className="loginContainer">
        <div className="loginContainer__banner">
          <img src={banner} />
        </div>
        <Form
          name="loginContainer__loginForm"
          labelCol={{ span: 8 }}
          wrapperCol={{ span: 16 }}
          style={{ maxWidth: 600 }}
          onFinish={onFinish}
          autoComplete="off"
          className="loginForm"
        >
          <div className="loginContainer__loginForm--title">
            <h1>
              Đăng nhập <strong>ENLINK</strong>
            </h1>
          </div>
          <Form.Item
            label="Tên tài khoản: "
            name="username"
            rules={[{ required: true, message: "Nhập tên tài khoản của bạn!" }]}
            className="loginContainer__loginForm--username"
          >
            <Input placeholder="Nhập tên tài khoản" />
          </Form.Item>
          <Form.Item
            label="Mật khẩu: "
            name="password"
            rules={[{ required: true, message: "Nhập mật khẩu của bạn!" }]}
            className="loginContainer__loginForm--password"
          >
            <Input.Password placeholder="Nhập mật khẩu" />
          </Form.Item>
          <div>
            {loginError && <p style={{ color: "red" }}>{loginError}</p>}
          </div>
          <Form.Item label={null} className="loginContainer__loginForm--button">
            <Button type="primary" htmlType="submit" loading={loading}>
              Đăng nhập
            </Button>
          </Form.Item>
          <Link to="/auth/createuser" className="loginContainer__registerLink">
            Bạn chưa có tài khoản? Đăng ký ngay
          </Link>
        </Form>
      </div>
    </>
  );
}
export default Login;
