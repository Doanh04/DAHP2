import {
  DeleteOutlined,
  EditOutlined,
  UploadOutlined,
} from "@ant-design/icons";
import {
  Button,
  Form,
  Input,
  InputNumber,
  message,
  Modal,
  Select,
  Tooltip,
  Upload,
  Table, 
  Pagination
} from "antd";
import { useEffect, useState } from "react";
import Swal from "sweetalert2";
import { handleUploadChange } from "../../../util/ConvertToBase64";

function ProductAdmin({
  listProduct,
  loading,
  handleReload,
  error,
  showModal,
  handleCancel,
  handleOk,
  isModalOpen,
  categoryId,
  handleAddProduct,
  handlePutProduct,
  selectedProduct,
  base64Image,
  setBase64Image,
  excuteDeleteProduct,
  pagination
}) {
  const API_BASE_URL = "http://localhost:8080";
  const [form] = Form.useForm();

  // Khi chọn sản phẩm để sửa thì set dữ liệu vào form
  useEffect(() => {
    if (selectedProduct) {
      form.setFieldsValue(selectedProduct);
    } else {
      form.resetFields();
      setBase64Image("");
    }
  }, [selectedProduct, form]);

  const columns = [
    { title: 'ID', dataIndex: 'productId', key: 'productId', width: 60 },
    { title: 'Tên sản phẩm', dataIndex: 'productName', key: 'productName', width: 150 },
    { title: 'Thương hiệu', dataIndex: 'brand', key: 'brand', width: 100 },
    { title: 'Số lượng', dataIndex: 'quantity', key: 'quantity', width: 80 },
    { 
        title: 'Mô tả', dataIndex: 'description', key: 'description', width: 150,
        render: (text) => (
             <Tooltip title={text}>
                {text.length > 50 ? text.substring(0, 50) + "..." : text}
            </Tooltip>
        ),
    },
    {
        title: 'Hình ảnh', dataIndex: 'imageUrl', key: 'imageUrl', width: 100,
        render: (imageUrl, record) => (
            <img
                src={`${API_BASE_URL}${imageUrl}`}
                alt={record.productName}
                style={{ width: "60px", height: "60px", objectFit: "cover" }}
            />
        ),
    },
    { title: 'Ngày tạo', dataIndex: 'createdAt', key: 'createdAt', width: 100 },
    { title: 'Danh mục', dataIndex: 'categoryName', key: 'categoryName', width: 100 },
    {
        title: 'Trạng thái', dataIndex: 'isActive', key: 'isActive', width: 100,
        render: (isActive) => (
            <span style={{ color: isActive ? "green" : "red" }}>
                {isActive ? "Hoạt động" : "Tạm khóa"}
            </span>
        ),
    },
    { title: 'Giá', dataIndex: 'price', key: 'price', width: 120, render: (price) => `${price?.toLocaleString("vi-VN")}đ` },
    {
        title: 'Chức năng', key: 'action', width: 120,
        render: (record) => (
            <>
                <Button onClick={() => showModal(record)} icon={<EditOutlined style={{ color: "#dbdb38ff" }} />} />
                <Button danger onClick={() => DelProduct(record.productId)} icon={<DeleteOutlined />} />
            </>
        ),
    },
  ];

  const onFinish = async (values) => {
    const hasImage = base64Image || selectedProduct?.imageUrl;
    const isEditing = !!values.productId;
    const apiCall = isEditing ? handlePutProduct : handleAddProduct;
    const title = isEditing
      ? "Xác nhận cập nhật sản phẩm"
      : "Xác nhận thêm sản phẩm";

    if (!hasImage) {
      message.error("Vui lòng tải lên ảnh sản phẩm.");
      return;
    }

    const { image, ...rest } = values;
    const payload = {
      ...rest,
      image: base64Image || null,
      isActive:
        values.isActive === true ||
        values.isActive === "true" ||
        values.isActive === 1,
    };
    if (isEditing && !base64Image) {
      delete payload.image;
    } else if (isEditing && !base64Image && selectedProduct?.imageUrl) {
      payload.image = selectedProduct.imageUrl;
    }
    const result = await Swal.fire({
      title,
      text: "Bạn có muốn lưu thay đổi?",
      showCancelButton: true,
      confirmButtonText: "Lưu",
      cancelButtonText: "Hủy",
      icon: "question",
    });

    if (result.isConfirmed) {
      try {
        const apiResult = await apiCall(payload);
        if (apiResult.success) {
          Swal.fire("Thành công", apiResult.message, "success");
          if (!isEditing) {
            form.resetFields(); 
            setBase64Image(null); 
          }
          handleReload();
          handleOk();
        } else {
          Swal.fire("Lỗi", apiResult.message, "error");
        }
      } catch (error) {
        Swal.fire("Lỗi!", "Đã xảy ra lỗi không xác định.", "error");
      }
    }
  };

  const DelProduct = async (productId) => {
    const result = await Swal.fire({
      title: "Xác nhận xóa sản phẩm?",
      text: "Bạn có muốn xóa sản phẩm này không?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "Xóa",
    });
    if (result.isConfirmed) {
      try {
        const ApiResult = await excuteDeleteProduct(productId);

        if (ApiResult.success) {
          Swal.fire("Đã xóa!", ApiResult.message, "success");

          handleReload();
        } else {
          Swal.fire(
            "Lỗi!",
            ApiResult.message || "Không xóa được sản phẩm",
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

  const modalTitle = selectedProduct ? "Cập nhật sản phẩm" : "Thêm sản phẩm";
  const handleModalOK = () => form.submit();

  return (
    <div className="product">
      <div className="Product__top">
        <div className="Product__top--left">
          <h1>Quản lý sản phẩm</h1>
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
              // value={searchTerm}
              // onChange={handleInputChange}
              // onPressEnter={handleSearch}
              style={{ width: 300 }}
            />
            <Button type="primary">Tìm kiếm</Button>
            <Button
              // onClick={() => {
              //   handleInputChange({ target: { value: "" } });
              //   handleSearch();
              // }}
              icon={<DeleteOutlined />}
              danger
            >
              Xóa
            </Button>
          </div>
        </div>
        <Button type="primary" onClick={() => showModal()}>
          Thêm sản phẩm
        </Button>
      </div>

      {/* Bảng sản phẩm */}
      <Table
        columns={columns}
        dataSource={listProduct}
        rowKey="productId"
        loading={loading}
        scroll={{ x: 'max-content' }}
        pagination={{
            current: pagination.current,
            pageSize: pagination.pageSize,
            total: pagination.total,
            showSizeChanger: true,
            showTotal: (total, range) => `${range[0]}-${range[1]} / ${total} mục`,
        }}
        // Ant Design gọi onChange, sau đó gọi hàm xử lý của Container Component
        onChange={(p) => pagination.onChange(p.current, p.pageSize)}
      />

      {/* Modal thêm / sửa */}
      <Modal
        title={modalTitle}
        open={isModalOpen}
        onOk={handleModalOK}
        onCancel={handleCancel}
        className="modalProduct"
      >
        <Form
          form={form}
          name="addProduct"
          labelCol={{ span: 10 }}
          wrapperCol={{ span: 14 }}
          onFinish={onFinish}
          autoComplete="off"
        >
          <Form.Item name="productId" hidden>
            <Input type="hidden" />
          </Form.Item>

          <Form.Item
            label="Tên sản phẩm"
            name="productName"
            rules={[{ required: true, message: "Vui lòng nhập tên sản phẩm" }]}
          >
            <Input style={{ width: "250px" }} />
          </Form.Item>

          <Form.Item
            label="Thương hiệu"
            name="brand"
          >
            <Input style={{ width: "250px" }} />
          </Form.Item>

          <Form.Item
            label="Số lượng"
            name="quantity"
            rules={[
              { required: true, message: "Nhập số lượng sản phẩm" },
              { type: "number", min: 1, message: "Số lượng phải > 0" },
            ]}
          >
            <InputNumber min={1} style={{ width: "250px" }} />
          </Form.Item>

          <Form.Item
            label="Mô tả"
            name="description"
            rules={[{ required: true, message: "Nhập mô tả sản phẩm" }]}
          >
            <Input.TextArea rows={3} showCount style={{ width: "250px" }} />
          </Form.Item>

          <Form.Item
            label="Giá"
            name="price"
            rules={[
              { required: true, message: "Nhập giá sản phẩm" },
              { type: "number", min: 1, message: "Giá phải > 0" },
            ]}
          >
            <InputNumber min={1} style={{ width: "250px" }} />
          </Form.Item>

          <Form.Item
            label="Trạng thái"
            name="isActive"
            rules={[{ required: true, message: "Chọn trạng thái" }]}
          >
            <Select placeholder="Chọn trạng thái" style={{ width: "250px" }}>
              <Select.Option value={true}>Hoạt động</Select.Option>
              <Select.Option value={false}>Tạm khóa</Select.Option>
            </Select>
          </Form.Item>

          <Form.Item
            label="Ảnh sản phẩm"
            name="image"
            rules={[
              { required: !selectedProduct, message: "Tải lên ảnh sản phẩm" },
            ]}
            valuePropName="fileList"
            getValueFromEvent={(e) => (Array.isArray(e) ? e : e?.fileList)}
          >
            <Upload
              listType="picture"
              beforeUpload={() => false}
              accept="image/*"
              maxCount={1}
              onChange={(info) => handleUploadChange(info, setBase64Image)}
              defaultFileList={
                selectedProduct?.imageUrl
                  ? [
                      {
                        uid: selectedProduct.productId || "-1",
                        name: selectedProduct.productName,
                        status: "done",
                        url: `${API_BASE_URL}${selectedProduct.imageUrl}`,
                      },
                    ]
                  : []
              }
            >
              <Button icon={<UploadOutlined />}>Chọn ảnh</Button>
            </Upload>
          </Form.Item>

          <Form.Item
            label="Danh mục"
            name="categoryId"
            rules={[{ required: true, message: "Chọn danh mục sản phẩm" }]}
          >
            <Select placeholder="Chọn danh mục" style={{ width: "250px" }}>
              {categoryId.map((item) => (
                <Select.Option key={item.categoryId} value={item.categoryId}>
                  {item.categoryName}
                </Select.Option>
              ))}
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
}

export default ProductAdmin;
