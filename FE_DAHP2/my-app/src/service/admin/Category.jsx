import HttpClient from "../../util/HttpClient";

const CATEGORY_BASE_URL = "/admin/category"

// Get All Category
const GetAllCategory = async()=>{
    try{
        const response = await HttpClient.get(`${CATEGORY_BASE_URL}/getcategory`)

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
// Add danh mục sản phẩm
const AddCategory = async(values)=>{
    try{
        const response =await HttpClient.post(`${CATEGORY_BASE_URL}/createcategory`)
        const data = response.data

        if(data.code === 1000){
            const result = data.result
            return result;
        }
        else{
             throw new Error("Không tải được danh sách danh mục")
        }
    }
    catch(error){
        throw error.response? error.response.data.code : 500;
    }
}

export {GetAllCategory, AddCategory}