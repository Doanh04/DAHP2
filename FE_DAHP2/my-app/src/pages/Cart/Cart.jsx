import { useEffect, useState } from "react";
import Cart from "../../components/UI/Cart/Cart";
import { useNavigate } from "react-router-dom";
import { GetCartItem } from "../../service/Cart/Cart";
import "../../style/Cart/Cart.scss"

function CartManager() {
  const [cartItems, setCartItems] = useState([]);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const fetchCartItems = async () => {
    setLoading(true);
    try {
      const data = await GetCartItem();
      setCartItems(data);
    } catch (error) {
      console.error("Lỗi khi tải giỏ hàng:", error);
    }
    finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        navigate("/auth/token");
      } else if (token) {
        setUser(token);
      }
    } catch (error) {
      navigate("/auth/token");
    }
    fetchCartItems();
  }, [navigate]);

  const formProps = {
    cartItems,
    loading
  };
  return (
    <>
      <Cart {...formProps}/>
    </>
  );
}
export default CartManager;
