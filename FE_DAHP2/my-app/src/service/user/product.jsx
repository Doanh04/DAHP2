import HttpClient from "../../util/HttpClient";

const PRODUCT_BASE_URL = "/dashboard/product";
// Get all product
const GetAllProduct = async (page = 0, size = 20) => {
  try {
    const response = await HttpClient.get(
      `${PRODUCT_BASE_URL}/productall?page=${page}&size=${size}`
    );

    const data = response.data;
    if (data.code === 1000) {
      const dataProduct = data.result;
      return dataProduct;
    } else {
      throw new Error("Không tải được các sản phẩm");
    }
  } catch (error) {
    throw error.response ? error.response.data.code : 500;
  }
};

// Get product by categoryId

const GetProductByCategory = async (categoryId, page = 0, size = 15) => {
  try {
    const response = await HttpClient.get(
      `${PRODUCT_BASE_URL}/categoryid-product/${categoryId}?page=${page}&size=${size}`
    );

    const data = response.data;
    if (data.code === 1000) {
      const productByCategory = data.result;
      return productByCategory;
    }
  } catch (error) {
    throw error.response
      ? error.response.data
      : { code: 500, message: "Network Error" };
  }
};

// Get ProductByName
const FilterProduct = async (filters) => {
  let url = `${PRODUCT_BASE_URL}/filterProduct?`;
  if (filters.productName) {
    url += `productName=${encodeURIComponent(filters.productName)}&`;
  }
  if( filters.minPrice) {
    url += `minPrice=${encodeURIComponent(filters.minPrice)}&`;
  }
  if( filters.maxPrice) {
    url += `maxPrice=${encodeURIComponent(filters.maxPrice)}&`;
  }
  if(filters.categoryId) {
    url += `categoryId=${encodeURIComponent(filters.categoryId)}&`;
  }
  if(filters.brand){
    url += `Brand=${encodeURIComponent(filters.brand)}&`;
  }
  if (url.endsWith('&')) {
    url = url.slice(0, -1);
  }
  try {
    const response = await HttpClient.get(url); 
    const data = response.data;
    
    if (data.code === 1000) {
      const productResults = data.result;
      return productResults;
    } else {
      const errorMessage = data.message || `Lỗi API (Code: ${data.code})`;
      throw new Error(errorMessage);
    }
  } catch (error) {
    const errorCode = error.response ? error.response.data.code : 500;
    const errorMessage =
      error.message || "Lỗi mạng hoặc không kết nối được đến máy chủ";
    throw new Error(errorMessage + ` (Code: ${errorCode})`);
  }
};
export { GetAllProduct, GetProductByCategory, FilterProduct };
