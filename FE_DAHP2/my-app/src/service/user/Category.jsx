import HttpClient from "../../util/HttpClient";

const CATEGORY_USER_URL = "/dasboard/category" 

// Get All Category
const GetAllCategory = async()=>{
    try{
        const response = await HttpClient.get(`${CATEGORY_USER_URL}/getcategory`)

        const data = response.data
        
        if(data.code === 1000){
            const dataCategory = data.result
            return dataCategory;
        }
        else{
            throw new Error("Không tải được danh sách danh mục")
        }
    }
    catch(error){
        throw error.response? error.response.data.code : 500;
    }
}
// GET TOP10 PRODUCT 
const GetTop10 = async()=>{
    try{
        const response = await HttpClient.get(`${CATEGORY_USER_URL}/top-products`)
        const data = response.data

        if(data.code === 1000){
            const dataCategory = data.result
            return dataCategory;
        }
        else{
            throw new Error("Không tải được các sản phẩm trong danh mục")
        }
    }
        catch(error){
        throw error.response? error.response.data.code : 500;
    }
}
export {GetAllCategory, GetTop10}