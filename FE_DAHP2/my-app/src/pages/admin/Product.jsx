import ProducAdmin from "../../components/UI/adminComponents/ProductAdmin";
import { GetAllCategory } from "../../service/admin/Category";
import {
  AddProduct,
  DeleteProduct,
  GetAllProduct,
  PutProduct,
} from "../../service/admin/ProducAdmin";
import "../../style/adminSCSS/ProductAdmin.scss";
import { useEffect, useState } from "react";

function Product() {
  // const [listProduct, setlistProduct] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  // const [refresh, setRefresh] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [categoryId, setCategoryId] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [base64Image, setBase64Image] = useState(null);
  const [productData, setProductData] = useState({
    content: [], // Dữ liệu sản phẩm của trang hiện tại
    totalElements: 0, // Tổng số mục
    number: 0, // Trang hiện tại
    size: 10, // Số mục trên mỗi trang
  });
  const [paginationParams, setPaginationParams] = useState({
    page: 0, // Spring Boot 0-based
    size: 10,
  });

  const fetchProducts = async (page = 0, size = 10) => {
    setLoading(true);
    try {
      // THAY ĐỔI: Gọi API với page và size
      const response = await GetAllProduct(page, size);
      const categoryData = await GetAllCategory();

      setCategoryId(categoryData);

      // THAY ĐỔI: Xử lý response phân trang
      const {
        content,
        totalElements,
        number,
        size: pageSize,
      } = response.result;

      setProductData({
        content,
        totalElements,
        number,
        size: pageSize,
      });
    } catch (error) {
      setError("Lỗi không tải được dữ liệu, liên hệ với dev");
    } finally {
      setLoading(false);
    }
  };
  // HÀM XỬ LÝ SỰ KIỆN PHÂN TRANG TỪ UI (1-based)
  const handlePageChange = (page, size) => {
    const serverPage = page - 1; // Chuyển sang 0-based cho Back-end
    setPaginationParams({ page: serverPage, size });
    fetchProducts(serverPage, size);
  };

  useEffect(() => {
    fetchProducts(paginationParams.page, paginationParams.size);
  }, []);

  const handleReload = () => {
    fetchProducts(paginationParams.page, paginationParams.size);
  };

  const showModal = (product = null) => {
    setSelectedProduct(product);
    setIsModalOpen(true);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
    setSelectedProduct(null);
  };
  const handleOk = () => {
    setIsModalOpen(false);
    setSelectedProduct(null);
  };

  const excuteAddProduct = async (values) => {
    const payload = {
      productName: values.productName,
      brand: values.brand,
      quantity: values.quantity,
      description: values.description,
      price: values.price,
      isActive: values.isActive,
      image: values.image,
      categoryId: values.categoryId,
    };
    try {
      await AddProduct(payload);
      return { success: true, message: "Thêm sản phẩm thành công!" };
    } catch (error) {
      const errorMessage = error.message || "Lỗi không thể thêm sản phẩm.";

      return { success: false, message: errorMessage };
    }
  };

  const excutePutProduct = async (values) => {
    const payload = {
      productId: values.productId,
      productName: values.productName,
      brand: values.brand,
      quantity: values.quantity,
      description: values.description,
      price: values.price,
      isActive: values.isActive,
      image: values.image,
      categoryId: values.categoryId,
    };
    try {
      await PutProduct(payload);
      return { success: true, message: "Cập nhật sản phẩm thành công!" };
    } catch (error) {
      const errorMessage = error.message || "Lỗi không thể cập nhật sản phẩm.";

      return { success: false, message: errorMessage };
    }
  };

  const excuteDeleteProduct = async (productId) => {
    try {
      await DeleteProduct(productId);
      return { success: true, message: "Xóa sản phẩm thành công!" };
    } catch (error) {
      const errorMessage = error.message || "Lỗi không thể xóa sản phẩm.";

      return { success: false, message: errorMessage };
    }
  };

  const formProp = {
    listProduct: productData.content,
    loading,
    error,
    isModalOpen,
    categoryId,
    selectedProduct,
    base64Image,
    setBase64Image,
    handleReload,
    showModal,
    handleCancel,
    handleOk,
    handleAddProduct: excuteAddProduct,
    handlePutProduct: excutePutProduct,
    excuteDeleteProduct,
    // THÊM: Truyền thông tin phân trang xuống component con
    pagination: {
      current: productData.number + 1, // 1-based cho Ant Design
      pageSize: productData.size,
      total: productData.totalElements,
      onChange: handlePageChange, // Hàm xử lý khi click
    },
  };
  return (
    <>
      <ProducAdmin {...formProp} />
    </>
  );
}
export default Product;
