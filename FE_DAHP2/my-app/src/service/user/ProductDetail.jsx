import HttpClient from "../../util/HttpClient";

const PRODUCT_DETAIL = "/dashboard/product";

const GetById = async (productId) => {
  const response = await HttpClient.get(`${PRODUCT_DETAIL}/${productId}`);

  const data = response.data;
  try {
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

export {GetById}