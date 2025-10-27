import { useEffect, useState } from "react";
import CategoryAdmin from "../../components/UI/adminComponents/CategoryAdmin";
import "../../style/adminSCSS/Category.scss";
import {
  AddCategory,
  DeleteCategory,
  GetAllCategory,
  PutCategory,
} from "../../service/admin/Category";
import Swal from "sweetalert2";
import { message } from "antd";

function Category() {
  const [listCategory, setListCategory] = useState([]);
  const [refresh, setRefresh] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);

  useEffect(() => {
    const getCategory = async () => {
      setLoading(true)
      try {
        const data = await GetAllCategory();
        setListCategory(data);
        setLoading(true);
      } catch (error) {
        setError("Lỗi không tải được dữ liệu, liên hệ với dev");
      }
      finally {
      setLoading(false); // Tải xong
    }
    };
    getCategory();
  }, [refresh]);

  const showModal = (category = null) => {
    setSelectedCategory(category); //Set dữ liệu ở chế độ put
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

  const executeAddCategory = async (values) => {
    const payload = {
      categoryName: values.categoryName,
      description: values.description,
    };
    try {
      await AddCategory(payload);

      return { success: true, message: "Thêm danh mục thành công!" }; 
    } catch (error) {
      const errorMessage = error.message || "Lỗi không thể thêm danh mục.";

      return { success: false, message: errorMessage };
    }
  };

  const executePutCategory = async (values) => {
   const payload = {
      categoryId: values.categoryId, // Đảm bảo ID được truyền từ Form
      categoryName: values.categoryName,
      description: values.description,
    };
    try {
      await PutCategory(payload);
      return { success: true, message: "Cập nhật danh mục thành công!" }; 
    } catch (error) {
      const errorMessage = error.message || "Lỗi không thể cập nhật danh mục.";

      return { success: false, message: errorMessage }; 
    }
  };
  
const excuteDeleteCategory = async (categoryId) => {
    try {
        await DeleteCategory(categoryId); 
        return { success: true, message: "Xóa danh mục thành công!" };
    } catch (error) {
        const errorMessage = error.message || "Lỗi không thể xóa danh mục.";
        return { success: false, message: errorMessage };
    }
}

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
    handleAddCategory: executeAddCategory,
    hanlePutCategory:executePutCategory,
    excuteDeleteCategory,
  };
  return (
    <>
      <CategoryAdmin {...formProp} />
    </>
  );
}
export default Category;
