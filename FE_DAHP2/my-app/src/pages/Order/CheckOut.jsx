import { useEffect, useState } from "react";
import CheckOutUI from "../../components/UI/Order/CheckoutUI";
import { GetCartItem } from "../../service/Cart/Cart";
import "../../style/OrderSCSS/Checkout.scss"

function CheckOutManager() {
  const [dataCart, setDataCart] = useState([]);
  const [dataContry, setDataContry] = useState([]);
  useEffect(() => {
    const fetchDataCart = async () => {
      try {
        const data = await GetCartItem();
        setDataCart(data);
      } catch (error) {
        console.error("Lỗi khi tải giỏ hàng:", error);
      }
    };
    fetchDataCart();
  }, []);


const orderItem = dataCart && dataCart.items 
    ? dataCart.items.map(item => ({
        productId: item.productId,
        quantity: item.quantity,
        unitPrice: item.unitPrice
      }))
    : [];
const requestBody = {
    shippingAddress: "",
    paymentMethod: "",
    Items: orderItem,
}
  const formProps = {
    dataCart,
  };
  return (
    <>
      <CheckOutUI {...formProps} />
    </>
  );
}
export default CheckOutManager;
