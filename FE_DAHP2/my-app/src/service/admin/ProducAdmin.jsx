import HttpClient from "../../util/HttpClient";

const PRODUCT_BASE_URL = "/admin/product";

// Get all product
const GetAllProduct = async (page = 0, size = 10) => {
  const url = `${PRODUCT_BASE_URL}/getproduct?page=${page}&size=${size}`;

  try {
    const response = await HttpClient.get(url); // Dùng URL mới
    const data = response.data;

    // data.result hiện là đối tượng Page (chứa content, totalElements, v.v.)
    if (data.code === 1000) {
      return data; // TRẢ VỀ TOÀN BỘ RESPONSE (hoặc data.result nếu bạn chỉ muốn data)
    } else {
      throw new Error("Không tải được dữ liệu");
    }
  } catch (error) {
    throw new Error(
      error.response
        ? error.response.data.message
        : "Lỗi mạng hoặc server không phản hồi"
    );
  }
};
// Add Product
const AddProduct = async (values) => {
  console.log("values", values);
  try {
    const response = await HttpClient.post(
      `${PRODUCT_BASE_URL}/createproduct`,
      {
        productName: values.productName,
        brand: values.brand,
        quantity: values.quantity,
        description: values.description,
        price: values.price,
        isActive: values.isActive,
        image: values.image,
        categoryId: values.categoryId,
      },
      { headers: { "Cache-Control": "no-cache" } }
    );

    const data = response.data;

    if (data.code === 1000) {
      return { success: true, data: response.data };
    } else {
      throw new Error(data.message || "Thêm sản phẩm không thành công");
    }
  } catch (error) {
    throw new Error(
      error.response
        ? error.response.data.message
        : "Lỗi mạng hoặc server không phản hồi"
    );
  }
};
// Put Product
const PutProduct = async (values) => {
  const productId = values.productId;
  try {
    const response = await HttpClient.put(
      `${PRODUCT_BASE_URL}/${productId}`,
      {
        productName: values.productName,
        brand: values.brand,
        quantity: values.quantity,
        description: values.description,
        price: values.price,
        isActive: values.isActive,
        image: values.image,
        categoryId: values.categoryId,
      },
      { headers: { "Cache-Control": "no-cache" } }
    );

    const data = response.data;
    if (data.code === 1000) {
      return { success: true, data: response.data };
    } else {
      throw new Error(data.message || "Cập nhật sản phẩm không thành công");
    }
  } catch (error) {
    throw new Error(
      error.response
        ? error.response.data.message
        : "Lỗi mạng hoặc server không phản hồi"
    );
  }
};
// Delete Product
const DeleteProduct = async (productId) => {
  const response = await HttpClient.delete(`${PRODUCT_BASE_URL}/${productId}`);

  const data = response.data;
  try {
    if (data.code === 1000) {
      return { success: true, data: response.data.sucsess };
    } else {
      throw new Error(data.message || "Xóa sản phẩm không thành công");
    }
  } catch (error) {
    return error.response
      ? error.response.data.message
      : "Lỗi mạng hoặc server không phản hồi";
  }
};

export { GetAllProduct, AddProduct, PutProduct, DeleteProduct };
