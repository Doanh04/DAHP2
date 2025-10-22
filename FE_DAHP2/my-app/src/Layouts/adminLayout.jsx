import { Layout } from "antd";
import { Button, Menu } from "antd";
import { MenuUnfoldOutlined, MenuFoldOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import { items } from "../util/DataMenu";
import { Link, Outlet, useNavigate } from "react-router-dom";
const { Header, Footer, Sider, Content } = Layout;
import Logo from "../imgs/logo.png";
import logofold from "../imgs/logofold.png";
import "../style/adminSCSS/adminLayout.scss";
import { Navigate } from "react-router-dom";

function AdminLayout() {
  const [collapsed, setCollapsed] = useState(false);
  const [userData, setUserData] = useState(null);
  const navigate =useNavigate();
  const handleClick = () => {
    setCollapsed(!collapsed);
  };
  useEffect(() =>{
    async function fetchUserDataFromStorage() {

        const nameStorage = localStorage.getItem('name'); 

        if (nameStorage) {
            try {

                const parsedName = JSON.parse(nameStorage); 
                setUserData(parsedName); 


            } catch (error) {
                console.warn("Dữ liệu 'name' không phải JSON, sử dụng giá trị thô.", error);
                setUserData(nameStorage);
            }
        } else {
             console.log("Không tìm thấy key 'name' trong Local Storage.");
             setUserData(null);
        }
    }
    
    fetchUserDataFromStorage(); 
  },[])

  const handleLogout = ()=>{
    localStorage.removeItem('name');
    localStorage.removeItem('token');
    localStorage.removeItem('username');

    navigate('/auth/token'); 
  }

  return (
    <>
      <Layout>
        <header>
          <div className="header__right">
            <Link to="/admin">
              <img
                className={collapsed ? "header__right--imgfold colapse" : "header__right--img colapse"}
                src={collapsed ? logofold : Logo}
                alt="Logo"
              />
            </Link>
            <Button
              onClick={handleClick}
              className={collapsed ? "header__right--btnfold colapse" : "header__right--btn colapse"}
              icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
            />
          </div>
          <div className="text">
            <div>
              <strong>Xin chào:</strong> {userData}
            </div>
            <div>
              <Button type="primary" onClick={handleLogout}>
                  Đăng xuất
              </Button>
            </div>
          </div>
        </header>
        <Layout className="layout">
          <Sider collapsed={collapsed} className="layout__Sider" theme="light">
            <Menu
              theme="light"
              mode="inline"
              items={items}
              defaultSelectedKeys={["home"]}
              defaultOpenKeys={["dashboard"]}
            />
          </Sider>
          <Content className="layout__content">
            <Outlet />
          </Content>
        </Layout>
      </Layout>
    </>
  );
}
export default AdminLayout;
