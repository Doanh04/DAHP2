import { Button, Modal, Form, Input } from "antd";
import {
  DeleteOutlined,
  EditOutlined,
  UserAddOutlined,
} from "@ant-design/icons";
import { useEffect } from "react";
import Swal from "sweetalert2";
import { AddCategory } from "../../../service/admin/Category";

function CategoryAdmin({
  listCategory,
  handleReload,
  loading,
  error,
  isModalOpen,
  showModal,
  handleCancel,
  handleOk,
  onFinish,
  selectedCategory
}) {
  const [form] = Form.useForm();

  // const onFinish = async (values) => {
  //   const result = await Swal.fire({
  //     title: "Xác nhận thêm danh mục",
  //     text: "Bạn có muốn lưu các thay đổi",
  //     showCancelButton: true,
  //     confirmButtonText: "Lưu",
  //     denyButtonText: `Không lưu`,
  //     icon: "question",
  //   });

  //   if (result.isConfirmed) {
  //     const apiResult = await AddCategory(values);

  //     if (apiResult) {
  //       Swal.fire("Đã lưu!", apiResult.message, "success");
  //       handleOk();
  //       handleReload();
  //       form.resetFields();
  //     } else {
  //       Swal.fire("Lỗi!", apiResult.message, "error");
  //     }
  //   } else if (result.isDenied) {
  //     Swal.fire("Đã Hủy", "Các thay đổi không được lưu.", "info");
  //     handleOk();
  //   }
  // };

  const handleModalOK = () => {
    form.submit();
  };
  return (
    <>
      <div className="p-4">
        <div className="header">
          <h2 className="header__textTitle">Quản lý Danh mục</h2>
        <Button className="addCategory" type="primary" onClick={() => showModal()}>
          Thêm danh mục
        </Button>
        </div>
        <table className="category__table">
          <thead>
            <tr>
              <td>ID</td>
              <td>Tên danh mục</td>
              <td>Mô tả</td>
              <td>Chức năng</td>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              listCategory.map((item) => (
                <tr key={item.categoryId}>
                  <td>{item.categoryId}</td>
                  <td>{item.categoryName}</td>
                  <td>{item.description}</td>
                  <td>
                    <Button>
                      <EditOutlined style={{ color: "#dbdb38ff" }} />
                    </Button>
                    <Button>
                      <DeleteOutlined style={{ color: "cd2b2bff" }} />
                    </Button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td
                  colSpan="4"
                  style={{ textAlign: "center", padding: "16px" }}
                >
                  <div className="spiner"></div>
                </td>
              </tr>
            )}
          </tbody>
        </table>
        <Modal
          title="Thêm danh mục sản phẩm"
          closable={{ "aria-label": "Custom Close Button" }}
          open={isModalOpen}
          onOk={handleModalOK}
          onCancel={handleCancel}
          className="modalAddCategory"
        >
          <Form
            form={form} 
            title="Thêm danh mục sản phẩm"
            closable={{ "aria-label": "Custom Close Button" }}
            name="addCategory"
            labelCol={{ span: 8 }}
            wrapperCol={{ span: 16 }}
            onFinish={onFinish}
            autoComplete="off"
            className="addForm"
          >
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
