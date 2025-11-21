import HttpClient from "../../utils/HttpClient";

const ORDER_URL = "/api/order"

// Checkout

const CheckOut = async (requestBody) =>{
    try{
        const response = await HttpClient.post(`${ORDER_URL}/checkout`,requestBody);
        const data = response.data;
        if(data.code === 1000){
            return data;
        }
        else{
            throw new Error("Đặt hàng không thành công")
        }
    }
    catch(error){
        throw error.response? error.response.data.code : 500;
    }
}
export default {CheckOut};