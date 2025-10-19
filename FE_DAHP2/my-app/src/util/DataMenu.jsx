import {
  HomeOutlined,
  DashboardOutlined,
  UserOutlined,
  ShoppingCartOutlined,
  UnorderedListOutlined,
  DatabaseOutlined,
  PlayCircleOutlined,
  PoweroffOutlined
} from '@ant-design/icons';
import { Link } from "react-router-dom";
import { Children } from 'react';

//Link điều hướng admin menu
export const items = [
  {
    label: <Link to="/admin">Trang chủ</Link>,
    key: "home",
    icon: <HomeOutlined />
  },
  {
    label:<p>Chức năng chính</p>,
    key: "dashboard",
    icon:<DashboardOutlined />,
    children:[
      {
        label:<Link to="/admin/users">Quản lý người dùng</Link>,
        key: "users",
        icon:<UserOutlined />
      },
      {
        label:<Link to="/admin/dashboard/productgroup">Quản lý nhóm sản phẩm</Link>,
        key: "productsgroup",
        icon:<ShoppingCartOutlined />
      },
      {
        label:<Link to="/admin/dashboard/products">Quản lý sản phẩm</Link>,
        key: "products",
        icon:<UnorderedListOutlined />
      }
    ]
  },
  {
    label:<p>Quản lý đơn hàng</p>,
    key: "orders",
    icon:<PlayCircleOutlined />,
    children:[
      {
        label:<Link to="/admin/orders/list">Danh sách đơn hàng</Link>,
        key: "order-list",
        icon:<DatabaseOutlined />
      },
      {
        label:<Link to="/admin/orders/status">Trạng thái đơn hàng</Link>,
        key: "order-status",
        icon:<PoweroffOutlined />
      },      
    ]
  }
  // Link điều hướng đăng nhập đăng xuất
  
];