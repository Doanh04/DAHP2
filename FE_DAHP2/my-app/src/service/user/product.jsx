import HttpClient from "../../util/HttpClient";

const PRODUCT_BASE_URL = "/dashboard/product";

const GetAllProduct = async (page = 0, size = 20) => {
  try {
    const response = await HttpClient.get(`${PRODUCT_BASE_URL}/productall?page=${page}&size=${size}`);

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


export {GetAllProduct}