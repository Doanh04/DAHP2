import { Children } from "react";
import UserLayout from "../Layouts/userLayout";
import AdminLayout from "../Layouts/adminLayout";
import Home from "../pages/admin/Home";
import OrderStatus from "../pages/admin/OrderStatus";
import User from "../pages/admin/User";
import ProductGroup from "../pages/admin/Category";
import Product from "../pages/admin/Product";
import OrderList from "../pages/admin/OrderList";
import Login from "../pages/Auth/Login";
import Signin from "../pages/Auth/Signin";
import AdminRoute from "../components/Authenticate/Authenticate";
import Category from "../pages/admin/Category";
import HomeUser from "../components/UI/userComponents/HomeUser";
import HomeUserManager from "../pages/user/HomeUser";
import ProductByCategory from "../components/UI/userComponents/ProductByCategory";
import ProdductDetail from "../components/UI/userComponents/ProductDetail";
import ProductDetailManager from "../pages/user/ProductDetail";
import ProductByCategoryManager from "../pages/user/ProductByCategory";
import ProductSearchItem from "../components/UI/userComponents/ProductSearchItem";
import ProductSearchItemManager from "../pages/user/ProductSearch";
import CartManager from "../pages/Cart/Cart";

export const Routers = [
  // Link điều hướng admin
  {
    path: "/admin",
    element: <AdminRoute />,
    children: [
      {
        element: <AdminLayout />,
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
            path: "category",
            element: <Category />,
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
    children: [
      {
        index: true,
        element: <HomeUserManager />,
      },
      {
        path: "products/category/:categoryId",
        element: <ProductByCategoryManager/>,
      },
      {
        path:"products/:productId",
        element:<ProductDetailManager/>
      },
      {
        path: "products/filterProduct",
        element: <ProductSearchItemManager/>,
      },
      {
        path: "cart",
        element: <CartManager/>
      }
    ],
  },
];
