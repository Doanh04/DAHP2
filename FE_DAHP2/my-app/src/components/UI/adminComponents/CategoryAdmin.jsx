import { Button, Modal, Form, Input, Table } from "antd";
import {
  DeleteOutlined,
  EditOutlined,
  UserAddOutlined,
} from "@ant-design/icons";
import { useEffect } from "react";
import Swal from "sweetalert2";
import { AddCategory, DeleteCategory } from "../../../service/admin/Category";

function CategoryAdmin({
  listCategory,
  handleReload,
  loading,
  error,
  isModalOpen,
  showModal,
  handleCancel,
  handleOk,
  // onFinish,
  selectedCategory, //null là thêm
  handleAddCategory,
  hanlePutCategory,
  excuteDeleteCategory,
}) {
  const [form] = Form.useForm();

  // chạy khi sellected thay đổi
  useEffect(() => {
    // chế độ sửa form.setFieldValue cho dữ liệu cũ vào input
    if (selectedCategory) {
      form.setFieldsValue(selectedCategory);
    } else {
      form.resetFields();
    }
  }, [selectedCategory, form]);

  const colums = [
    { title: "ID", dataIndex: "categoryId", key: "categoryId", width: 80 },
    {
      title: "Tên danh mục",
      dataIndex: "categoryName",
      key: "categoryName",
      width: 200,
    },
    { title: "Mô tả", dataIndex: "description", key: "description", width: 200 },
    {
      title: "Chức năng",
      key: "action",
      width: 150,
      render: (record) => (
        <>
          <Button onClick={() => showModal(record)}>
            <EditOutlined style={{ color: "#dbdb38ff" }} />
          </Button>
          <Button>
            <DeleteOutlined
              onClick={() => {
                handleDeleteCategory(record.categoryId);
              }}
              style={{ color: "#cd2b2bff" }}
            />
          </Button>
        </>
      ),
    },
  ];

  const onFinish = async (values) => {
    const isEditing = !!values.categoryId; // tạo biến dạng bool
    const apiCall = isEditing ? hanlePutCategory : handleAddCategory;
    const title = isEditing
      ? "Xác nhận cập nhật danh mục"
      : "Xác nhận thêm danh mục";

    const result = await Swal.fire({
      title: title,
      text: "Bạn có muốn lưu thay đổi",
      showCancelButton: true,
      confirmButtonText: "Lưu",
      denyButtonText: `Không lưu`,
      icon: "question",
    });
    if (result.isConfirmed) {
      try {
        const apiResult = await apiCall(values);

        if (apiResult.success) {
          Swal.fire("Đã lưu", apiResult.message, "success");
          handleOk();
          handleReload();
          form.resetFields();
        } else {
          0 - 0;
          Swal.fire("Lỗi !", apiResult.message, "error");
        }
      } catch (error) {
        Swal.fire("Lỗi!", "Đã xảy ra lỗi không xác định.", "error");
      }
    } else if (result.isDenied || result.dismiss) {
      // Xử lý khi chọn Không lưu, Hủy hoặc đóng Swal
      Swal.fire("Đã Hủy", "Các thay đổi không được lưu.", "info");
      handleOk();
    }
  };

  const handleDeleteCategory = async (categoryId) => {
    const result = await Swal.fire({
      title: "Xác nhận xóa danh mục?",
      text: "Bạn có muốn xóa danh mục này không?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "Xóa",
    });
    if (result.isConfirmed) {
      try {
        const ApiResult = await excuteDeleteCategory(categoryId);

        if (ApiResult.success) {
          Swal.fire("Đã xóa!", ApiResult.message, "success");

          handleReload();
        } else {
          Swal.fire(
            "Lỗi!",
            ApiResult.message || "Không xóa được danh mục",
            "error"
          );
        }
      } catch (error) {
        Swal.fire(
          "Lỗi!",
          error.message || "Lỗi không xác định từ server.",
          "error"
        );
      }
    }
  };

  const modalTitle = selectedCategory ? "Chính sửa Danh mục" : "Thêm danh mục";

  const handleModalOK = () => {
    form.submit();
  };
  return (
    <>
      <div className="p-4">
        <div className="header">
          <h2 className="header__textTitle">Quản lý Danh mục</h2>
          <Button
            className="addCategory"
            type="primary"
            onClick={() => showModal()}
          >
            Thêm danh mục
          </Button>
        </div>
        <Table
          columns={colums}
          dataSource={listCategory}
          rowKey="categoryId"
          loading={loading}
          scroll={{ x: 'max-content' }}
        />
        <Modal
          title={modalTitle}
          closable={{ "aria-label": "Custom Close Button" }}
          open={isModalOpen}
          onOk={handleModalOK}
          onCancel={handleCancel}
          className="modalAddCategory"
        >
          <Form
            form={form}
            name="addCategory"
            labelCol={{ span: 8 }}
            wrapperCol={{ span: 16 }}
            onFinish={onFinish}
            autoComplete="off"
            className="addForm"
          >
            <Form.Item name="categoryId" hidden>
                            <Input type="hidden" /> 
            </Form.Item>
            <Form.Item
              label="Tên danh mục"
              className="addForm__input"
              name="categoryName"
              rules={[
                {
                  required: true,
                  message: "Vui lòng nhập tên danh mục",
                },
              ]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label="Mô tả danh mục"
              className="addForm__input"
              name="description"
            >
              <Input />
            </Form.Item>
          </Form>
        </Modal>
      </div>
    </>
  );
}
export default CategoryAdmin;
