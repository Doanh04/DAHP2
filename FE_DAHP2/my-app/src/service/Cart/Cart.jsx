import HttpClient from "../../util/HttpClient";

const CART_URL = "user/cart";

// Get Cart Items
const GetCartItem = async ()=>{
    try{
        const response = await HttpClient.get(`${CART_URL}/getCartItem`);
        const data = response.data;

        if(data.code === 200){
            const dataCart = data.result;
            return dataCart;
        }
        else{
            throw new Error("Không tải được giỏ hàng")
        }
    }
    catch(error){
        throw error.response? error.response.data.code : 500;
    }
}
// Add to Cart
const AddToCart = async (productId, quantity)=>{
    try{
        const response = await HttpClient.post(`${CART_URL}/add`, {
            productId: productId,
            quantity: quantity
        });
        const data = response.data;
        if(data.code === 200){
            return data;
        }
        else{
            throw new Error("Thêm vào giỏ hàng không thành công")
        }
    }
    catch(error){
        throw error.response? error.response.data.code : 500;
    }
}
export {GetCartItem, AddToCart}