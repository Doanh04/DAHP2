import {
  DeleteOutlined,
  EditOutlined,
  UserAddOutlined,
} from "@ant-design/icons";
import { Button, Modal, Form, Input, Select } from "antd";
import { useEffect } from "react";
import Swal from "sweetalert2";
const { Option } = Select;

function UserAdmin({
  listAccout,
  listRole,
  loading,
  error,
  handleReload,
  handleCancel,
  handleOk,
  showModal,
  isModalOpen,
  selectedAccount,
  handleUpdateAccount,
  DeleteAccout,
  searchTerm,
  handleSearch,
  handleInputChange,
}) {
  const [form] = Form.useForm();
  useEffect(() => {
    if (selectedAccount) {
      form.setFieldsValue(selectedAccount);
    }
  }, [selectedAccount, form, handleReload]);

  const onFinish = async (values) => {
    const result = await Swal.fire({
      title: "Xác nhận cập nhật?",
      text: "Bạn có muốn lưu các thay đổi này không?",
      showDenyButton: true,
      showCancelButton: true,
      confirmButtonText: "Lưu",
      denyButtonText: `Không lưu`,
      icon: "question",
    });

    if (result.isConfirmed) {
      const apiResult = await handleUpdateAccount(values);

      if (apiResult.success) {
        Swal.fire("Đã Lưu!", apiResult.message, "success");
      } else {
        Swal.fire("Lỗi!", apiResult.message, "error");
      }
    } else if (result.isDenied) {
      Swal.fire("Đã Hủy", "Các thay đổi không được lưu.", "info");
      handleOk(); // Đóng modal và reset state
    }
  };

  const handleDelte = async (userId) => {
    const result = await Swal.fire({
      title: "Xác nhận xóa tài khoản?",
      text: "Bạn có muốn xóa tài khoản này không?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "Xóa",
    });
    if (result.isConfirmed) {
      try {
        const ApiResult = await DeleteAccout(userId);

        if (ApiResult.success) {
          Swal.fire("Đã xóa!", ApiResult.message, "success");

          handleReload();
        } else {
          Swal.fire(
            "Lỗi!",
            ApiResult.message || "Không xóa được tài khoản",
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

  const handleModalOk = () => {
    form.submit();
  };

  return (
    <>
      <div className="textTitle"><h1>Quản lý tài khoản</h1></div>
      <div
        style={{
          display: "flex",
          gap: "8px",
          marginBottom: "16px",
          alignItems: "center",
        }}
      >
        <Input
          placeholder="Tìm kiếm theo họ tên..."
          value={searchTerm}
          onChange={handleInputChange} 
          onPressEnter={handleSearch} 
          style={{ width: 300 }}
        />
        <Button type="primary" onClick={handleSearch}>
          Tìm kiếm
        </Button>
        {searchTerm && ( 
          <Button
            onClick={() => {
              handleInputChange({ target: { value: "" } });
              handleSearch(); 
            }}
            icon={<DeleteOutlined />}
            danger
          >
            Xóa
          </Button>
        )}
      </div>
      <table className="user__table">
        <thead>
          <tr>
            <td>ID</td>
            <td>Tên tài khoản</td>
            <td>Họ tên</td>
            <td>Email</td>
            <td>Số điện thoại</td>
            <td>Địa chỉ</td>
            <td>Quyền</td>
            <td>Chức năng</td>
          </tr>
        </thead>
        <tbody>
          {loading ? (
            listAccout.map((item) => (
              <tr key={item.userId}>
                <td>{item.userId}</td>
                <td>{item.username}</td>
                <td>{item.name}</td>
                <td>{item.email}</td>
                <td>{item.phone}</td>
                <td>{item.address}</td>
                <td>{item.roles[0]?.roleName || "Không có role"}</td>
                <td>
                  <Button onClick={() => showModal(item)}>
                    <EditOutlined style={{ color: "#dbdb38ff" }} />
                  </Button>
                  <Button onClick={() => handleDelte(item.userId)}>
                    <DeleteOutlined style={{ color: "#cd2b2bff" }} />
                  </Button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="8" style={{ textAlign: "center", padding: "16px" }}>
                <div className="spiner"></div>
              </td>
            </tr>
          )}
        </tbody>
      </table>
      <Modal
        title="Cập nhật tài khoản"
        closable={{ "aria-label": "Custom Close Button" }}
        open={isModalOpen}
        onOk={handleModalOk}
        onCancel={handleCancel}
        className="modalPutAccout"
      >
        <Form
          form={form}
          name="putAccout"
          labelCol={{ span: 6 }}
          wrapperCol={{ span: 18 }}
          onFinish={onFinish}
          // onValuesChange={onchange}
          autoComplete="off"
          className="editForm"
        >
          <Form.Item name="userId" hidden>
            <Input type="hidden" />
          </Form.Item>
          <Form.Item name="password" hidden>
            <Input type="hidden" />
          </Form.Item>
          <Form.Item label="Họ tên: " name="name" className="editForm__input">
            <Input />
          </Form.Item>
          <Form.Item label="Email: " name="email" className="editForm__input">
            <Input />
          </Form.Item>

          <Form.Item
            label="Số điện thoại: "
            name="phone"
            className="editForm__input"
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Địa chỉ: "
            name="address"
            className="editForm__input"
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Quyền tài khoản: "
            name="roles"
            className="editForm__input"
            rules={[
              {
                required: true,
                message: "Vui lòng chọn ít nhất một quyền cho tài khoản!",
              },
            ]}
          >
            <Select
              mode="multiple"
              placeholder="Vui lòng chọn quyền của tài khoản"
            >
              {listRole.map((item, index) => (
                <Select.Option value={item.roleName} key={index}>
                  {item.roleName}
                </Select.Option>
              ))}
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
}
export default UserAdmin;
