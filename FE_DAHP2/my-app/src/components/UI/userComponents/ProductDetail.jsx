import { Button, Card, Col, InputNumber, Row, Tag, Divider, Skeleton } from "antd";
import { ShoppingCartOutlined, CheckCircleOutlined, CloseCircleOutlined } from "@ant-design/icons";
import { useState } from "react";
// import "./ProductDetail.scss";

const formatCurrency = (price) => {
  return price?.toLocaleString('vi-VN') + 'đ';
};

function ProductDetail({ product, loading, error }) {
  const [quantity, setQuantity] = useState(1);
  const API_BASE_URL = "http://localhost:8080";

  // Xử lý thay đổi số lượng
  const handleQuantityChange = (value) => {
    if (value >= 1 && value <= product.quantity) {
      setQuantity(value);
    }
  };

  // Xử lý thêm vào giỏ hàng
  const handleAddToCart = () => {
    console.log("Thêm vào giỏ hàng:", {
      productId: product.productId,
      quantity: quantity
    });
  };

  if (loading) {
    return (
      <div className="ProductDetail">
        <Skeleton active paragraph={{ rows: 10 }} />
      </div>
    );
  }

  if (error) {
    return (
      <div className="ProductDetail ProductDetail--error">
        <h2>Lỗi: {error}</h2>
      </div>
    );
  }

  if (!product) {
    return (
      <div className="ProductDetail ProductDetail--empty">
        <h2>Không tìm thấy sản phẩm</h2>
      </div>
    );
  }

  const isInStock = product.quantity > 0;

  return (
    <div className="ProductDetail">
      <Row gutter={[40, 40]}>
        {/* Cột Ảnh Sản Phẩm */}
        <Col xs={24} md={10}>
          <Card bordered={false} className="ProductDetail__image-card">
            <img
              src={`${API_BASE_URL}${product.imageUrl}`}
              alt={product.productName}
              className="ProductDetail__image"
            />
          </Card>
        </Col>

        {/* Cột Thông Tin Sản Phẩm */}
        <Col xs={24} md={14}>
          <div className="ProductDetail__info">
            {/* Tên sản phẩm */}
            <h1 className="ProductDetail__title">{product.productName}</h1>

            {/* Thương hiệu và Danh mục */}
            <div className="ProductDetail__tags">
              <Tag color="blue" className="ProductDetail__tag">
                Thương hiệu: {product.brand}
              </Tag>
              <Tag color="green" className="ProductDetail__tag">
                {product.categoryName}
              </Tag>
            </div>

            <Divider />

            {/* Giá sản phẩm */}
            <div className="ProductDetail__price-box">
              <div className="ProductDetail__price-label">Giá bán:</div>
              <div className="ProductDetail__price">{formatCurrency(product.price)}</div>
            </div>

            {/* Trạng thái kho */}
            <Card
              bordered={false}
              className={`ProductDetail__stock ${isInStock ? 'ProductDetail__stock--in' : 'ProductDetail__stock--out'}`}
            >
              <Row align="middle">
                <Col flex="none">
                  {isInStock ? (
                    <CheckCircleOutlined className="ProductDetail__stock-icon ProductDetail__stock-icon--in" />
                  ) : (
                    <CloseCircleOutlined className="ProductDetail__stock-icon ProductDetail__stock-icon--out" />
                  )}
                </Col>
                <Col flex="auto">
                  <div className="ProductDetail__stock-text">
                    {isInStock ? (
                      <>
                        <span className="ProductDetail__stock-status ProductDetail__stock-status--in">
                          Còn hàng
                        </span>
                        <span className="ProductDetail__stock-count">
                          (Còn {product.quantity} sản phẩm)
                        </span>
                      </>
                    ) : (
                      <span className="ProductDetail__stock-status ProductDetail__stock-status--out">
                        Hết hàng
                      </span>
                    )}
                  </div>
                </Col>
              </Row>
            </Card>

            {/* Chọn số lượng */}
            {isInStock && (
              <div className="ProductDetail__quantity">
                <div className="ProductDetail__quantity-label">Số lượng:</div>
                <InputNumber
                  min={1}
                  max={product.quantity}
                  value={quantity}
                  onChange={handleQuantityChange}
                  size="large"
                  className="ProductDetail__quantity-input"
                />
                <span className="ProductDetail__quantity-max">
                  Tối đa {product.quantity} sản phẩm
                </span>
              </div>
            )}

            {/* Nút Thêm Vào Giỏ Hàng */}
            <Row gutter={[16, 16]} className="ProductDetail__actions">
              <Col xs={24} sm={12}>
                <Button
                  type="primary"
                  size="large"
                  icon={<ShoppingCartOutlined />}
                  onClick={handleAddToCart}
                  disabled={!isInStock}
                  block
                  className={`ProductDetail__btn ProductDetail__btn--cart ${!isInStock ? 'ProductDetail__btn--disabled' : ''}`}
                >
                  {isInStock ? 'Thêm vào giỏ hàng' : 'Hết hàng'}
                </Button>
              </Col>
              <Col xs={24} sm={12}>
                <Button
                  size="large"
                  disabled={!isInStock}
                  block
                  className={`ProductDetail__btn ProductDetail__btn--buy ${!isInStock ? 'ProductDetail__btn--disabled' : ''}`}
                >
                  Mua ngay
                </Button>
              </Col>
            </Row>

            {/* Thông tin bổ sung */}
            <Card
              title="Thông tin bổ sung"
              bordered={false}
              className="ProductDetail__extra-info"
            >
              <Row gutter={[16, 12]}>
                <Col span={12}>
                  <div className="ProductDetail__extra-label">Mã sản phẩm:</div>
                  <div className="ProductDetail__extra-value">SP-{product.productId}</div>
                </Col>
                <Col span={12}>
                  <div className="ProductDetail__extra-label">Thương hiệu:</div>
                  <div className="ProductDetail__extra-value">{product.brand}</div>
                </Col>
                <Col span={12}>
                  <div className="ProductDetail__extra-label">Danh mục:</div>
                  <div className="ProductDetail__extra-value">{product.categoryName}</div>
                </Col>
                <Col span={12}>
                  <div className="ProductDetail__extra-label">Trạng thái:</div>
                  <div className="ProductDetail__extra-value">
                    {product.isActive ? (
                      <Tag color="success">Đang bán</Tag>
                    ) : (
                      <Tag color="error">Ngừng bán</Tag>
                    )}
                  </div>
                </Col>
              </Row>
            </Card>
          </div>
        </Col>
      </Row>

      {/* Mô tả sản phẩm */}
      <Row className="ProductDetail__description-row">
        <Col span={24}>
          <Card
            title={<h2 className="ProductDetail__description-title">Mô tả sản phẩm</h2>}
            bordered={false}
            className="ProductDetail__description-card"
          >
            <div className="ProductDetail__description-content">
              {product.description}
            </div>
          </Card>
        </Col>
      </Row>
    </div>
  );
}

export default ProductDetail;