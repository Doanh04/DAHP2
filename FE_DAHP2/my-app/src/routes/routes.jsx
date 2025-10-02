import { Children } from "react";
import UserLayout from "../Layouts/userLayout";
import AdminLayout from "../Layouts/adminLayout";
import Home from "../pages/admin/Home";
import OrderStatus from "../pages/admin/OrderStatus";
import User from "../pages/admin/User";
import ProductGroup from "../pages/admin/ProductGroup";
import Product from "../pages/admin/Product";
import OrderList from "../pages/admin/OrderList";

export const Routers = [
 {
  path: "/admin",
  element: <AdminLayout />,
  children: [
    {
      index: true,
      element: <Home />,
    },
    {
      path: "dashboard/users",      // KHÔNG thêm /
      element: <User />,
    },
    {
      path: "dashboard/productgroup",
      element: <ProductGroup />,
    },
    {
      path: "dashboard/products",
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
}
,
  {
    path: "/",
    element: <UserLayout />,
    children: [],
  },
];
