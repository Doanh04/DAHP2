import { useEffect, useState } from "react";
import CategoryAdmin from "../../components/UI/adminComponents/CategoryAdmin";
import "../../style/adminSCSS/Category.scss";
import {
  AddCategory,
  GetAllCategory,
  PutCategory,
} from "../../service/admin/Category";
import Swal from "sweetalert2";

function Category() {
  const [listCategory, setListCategory] = useState([]);
  const [refresh, setRefresh] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);

  useEffect(() => {
    const getCategory = async () => {
      try {
        const data = await GetAllCategory();
        setListCategory(data);
        setLoading(true);
      } catch (error) {
        setError("Lỗi không tải được dữ liệu, liên hệ với dev");
      }
    };
    getCategory();
  }, [refresh]);

  const showModal = (category = null) => {
    setSelectedCategory(category);//Set dữ liệu ở chế độ put
    setIsModalOpen(true);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
    setSelectedCategory(null);
  };
  const handleOk = () => {
    setIsModalOpen(false);
    setSelectedCategory(null);
  };

  const handleReload = () => {
    setRefresh((prev) => !prev);
  };

  const handleAddCategory = async (values) => {
    const result = await Swal.fire({
          title: "Xác nhận thêm danh mục",
          text: "Bạn có muốn lưu các thay đổi",
          showCancelButton: true,
          confirmButtonText: "Lưu",
          denyButtonText: `Không lưu`,
          icon: "question",
        });
    
        if (result.isConfirmed) {
          const apiResult = await AddCategory(values);
    
          if (apiResult) {
            Swal.fire("Đã lưu!", apiResult.message, "success");
            handleOk();
            handleReload();
            form.resetFields();
          } else {
            Swal.fire("Lỗi!", apiResult.message, "error");
          }
        } else if (result.isDenied) {
          Swal.fire("Đã Hủy", "Các thay đổi không được lưu.", "info");
          handleOk();
        }
  };
  const hanlePutCategory = async (values) => {
    const payload = {
      categoryName: values.categoryName,
      description: values.description,
      
    };

    try {
      await PutCategory(payload);

      handleOk();
      handleReload();

      return { success: true, message: "Cập nhật danh mục thành công!" };
    } catch (error) {
      const errorMessage = error.message || "Lỗi không thể cập nhật danh mục.";

      return { success: false, message: errorMessage };
    }
  };
  const formProp = {
    listCategory,
    loading,
    error,
    isModalOpen,
    selectedCategory,
    handleReload,
    showModal,
    handleCancel,
    handleOk,
    onFinish:handleAddCategory,
    hanlePutCategory
  };
  return (
    <>
      <CategoryAdmin {...formProp} />
    </>
  );
}
export default Category;
