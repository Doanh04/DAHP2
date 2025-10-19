import { Children } from "react";
import UserLayout from "../Layouts/userLayout";
import AdminLayout from "../Layouts/adminLayout";
import Home from "../pages/admin/Home";
import OrderStatus from "../pages/admin/OrderStatus";
import User from "../pages/admin/User";
import ProductGroup from "../pages/admin/ProductGroup";
import Product from "../pages/admin/Product";
import OrderList from "../pages/admin/OrderList";
import Login from "../pages/Auth/Login";
import Signin from "../pages/Auth/Signin";
import AdminRoute from "../components/Authenticate/Authenticate";

export const Routers = [
  // Link điều hướng admin
  {
    path: "/admin",
    element: <AdminRoute />,
    children: [
      {
        element:<AdminLayout/>,
        children: [
          {
            index: true,
            element: <Home />,
          },
          {
            path: "users",
            element: <User />,
          },
          {
            path: "productgroup",
            element: <ProductGroup />,
          },
          {
            path: "products",
            element: <Product />,
          },
          {
            path: "orders/list",
            element: <OrderList />,
          },
          {
            path: "orders/status",
            element: <OrderStatus />,
          },
        ],
      },
    ],
  },
  // Link điều hướng auth
  {
    path: "/auth/token",
    element: <Login />,
  },
  {
    path: "/auth/createuser",
    element: <Signin />,
  },
  ,
  // Link điều hướng user
  {
    path: "/",
    element: <UserLayout />,
    children: [],
  },
];
