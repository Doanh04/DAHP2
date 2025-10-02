import { Layout } from "antd";
import { Button, Menu } from "antd";
import { MenuUnfoldOutlined, MenuFoldOutlined } from "@ant-design/icons";
import { useState } from "react";
import { items } from "../util/DataMenu";
import { Link, Outlet } from "react-router-dom";
const { Header, Footer, Sider, Content } = Layout;
import Logo from "../imgs/logo.png";
import logofold from "../imgs/logofold.png";
import "../style/adminSCSS/adminLayout.scss";

function AdminLayout() {
  const [collapsed, setCollapsed] = useState(false);
  const handleClick = () => {
    setCollapsed(!collapsed);
  };

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
          <div></div>
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
