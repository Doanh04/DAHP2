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
        const response =await HttpClient.post(`${CATEGORY_BASE_URL}/createcategory`,{
            categoryName:values.categoryName,
            description:values.description,
        })
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
//  Sửa danh mục sản phẩm
const PutCategory = async (values) =>{
    try{
        const response = await HttpClient.put(`${CATEGORY_BASE_URL}/${values.categoryId}`,{
            categoryName:values.categoryName,
            description:values.description,
        })
        const data = response.data;

        if(data.code === 1000){
            const result = data.result;
            return result;
        }
        else{
            throw new Error("Không thể cập nhật danh mục");
        }
    }
    catch(error){
        throw new Error(error.response?error.response.data.error:"Server không thể phản hồi");
    }
}
// Xóa danh mục sản phẩm
const DeleteCategory = async(categoryId)=>{
    try{
        const response = await HttpClient.delete(`${CATEGORY_BASE_URL}/${categoryId}`)
        const data = response.data;
        if(data.code === 1000){
            const result = data.result;
            return result;
        }
        else{
            throw new Error("Không thể xóa danh mục");
        }
    }
    catch(error){
        throw new Error(error.response?error.response.data.error:"Server không phải hồi")
        
    }
}
export {GetAllCategory, AddCategory, PutCategory, DeleteCategory}