// ProductByCategory.jsx
import React from 'react';
import { Card, Row, Col, Button, Tag, Pagination, Spin, Empty, Alert, notification } from 'antd';
import { ShoppingCartOutlined, CheckCircleOutlined, CloseCircleOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { AddToCart } from '../../../service/Cart/Cart';
// import './ProductByCategory.scss';

const formatCurrency = (price) => {
  return price?.toLocaleString('vi-VN') + 'đ';
};

function ProductByCategory({
  products,
  loading,
  error,
  currentPage,
  pageSize,
  totalItems,
  categoryId,
  handlePageChange,
}) {
  const API_BASE_URL = "http://localhost:8080";

  const [api, contextHolder] = notification.useNotification();

    // Xử lý notifycation
  const openNotification =(type, title, description)=>{
    api[type]({
      message:title,
      description:description,
      placement:"topRight"
    })
  }

  // Xử lý thêm vào giỏ hàng
  const handleAddToCart = async (product) => {
    try{
      await AddToCart(product.productId, 1);
      setTimeout(() => {
        openNotification(
        "success",
        "Thành công",
        `Đã thêm ${1} sản phẩm vào giỏ hàng.`
      )
      }, 0)
    }
    catch(error){
      const errorMessage = error.message || "Lỗi không thể thêm vào giỏ hàng.";
      setTimeout(() => {
        openNotification(
        "error",
        "Thất bại",
        errorMessage
      )
      }, 0);
    }
  };

  // Loading state
  if (loading) {
    return (
      <div className="ProductByCategory ProductByCategory--loading">
        <div className="ProductByCategory__spinner">
          <Spin size="large" tip="Đang tải sản phẩm..." />
        </div>
      </div>
    );
  }

  // Error state
  if (error) {
    return (
      <div className="ProductByCategory ProductByCategory--error">
        <Alert
          message="Có lỗi xảy ra"
          description={error}
          type="error"
          showIcon
          action={
            <Button onClick={() => window.location.reload()} type="primary" danger>
              Thử lại
            </Button>
          }
        />
      </div>
    );
  }

  // Empty state
  if (!products || products.length === 0) {
    return (
      <div className="ProductByCategory ProductByCategory--empty">
        <Empty
          description="Không có sản phẩm nào trong danh mục này"
          image={Empty.PRESENTED_IMAGE_SIMPLE}
        />
      </div>
    );
  }

  return (
    <div className="ProductByCategory">
      {contextHolder}
      {/* Header */}
      <div className="ProductByCategory__header">
        <div className="ribbon-container">
          <div className="ribbon-label">
            {products[0]?.categoryName || 'Danh mục sản phẩm'}
          </div>
          <div className="ribbon-base"></div>
        </div>
        <div className="ProductByCategory__count">
          Tìm thấy <strong>{totalItems}</strong> sản phẩm
        </div>
      </div>

      {/* Product Grid */}
      <Row gutter={[24, 24]} className="ProductByCategory__grid">
        {products.map((product) => {
          const isInStock = product.quantity > 0;
          const isLowStock = product.quantity > 0 && product.quantity < 10;

          return (
            <Col xs={24} sm={12} md={8} lg={6} key={product.productId}>
              <Card
                hoverable
                className="ProductByCategory__card"
                cover={
                  <Link to={`/products/${product.productId}`}>
                    <div className="ProductByCategory__image-wrapper">
                      <img
                        alt={product.productName}
                        src={`${API_BASE_URL}${product.imageUrl}`}
                        className="ProductByCategory__image"
                      />
                      {/* Badge trạng thái */}
                      {!isInStock && (
                        <div className="ProductByCategory__badge ProductByCategory__badge--out">
                          Hết hàng
                        </div>
                      )}
                      {isLowStock && (
                        <div className="ProductByCategory__badge ProductByCategory__badge--low">
                          Sắp hết
                        </div>
                      )}
                    </div>
                  </Link>
                }
              >
                {/* Product Info */}
                <div className="ProductByCategory__content">
                  <Link to={`/products/${product.productId}`}>
                    <h3 className="ProductByCategory__title">
                      {product.productName}
                    </h3>
                  </Link>

                  <div className="ProductByCategory__brand">
                    <Tag color="blue">{product.brand}</Tag>
                  </div>

                  {/* Price */}
                  <div className="ProductByCategory__price">
                    {formatCurrency(product.price)}
                  </div>

                  {/* Stock Status */}
                  <div className={`ProductByCategory__stock ${isInStock ? 'ProductByCategory__stock--in' : 'ProductByCategory__stock--out'}`}>
                    {isInStock ? (
                      <>
                        <CheckCircleOutlined className="ProductByCategory__stock-icon" />
                        <span>Còn {product.quantity} sản phẩm</span>
                      </>
                    ) : (
                      <>
                        <CloseCircleOutlined className="ProductByCategory__stock-icon" />
                        <span>Hết hàng</span>
                      </>
                    )}
                  </div>

                  {/* Add to Cart Button */}
                  <Button
                    type="primary"
                    icon={<ShoppingCartOutlined />}
                    block
                    size="large"
                    disabled={!isInStock}
                    onClick={() => handleAddToCart(product)}
                    className={`ProductByCategory__btn ${!isInStock ? 'ProductByCategory__btn--disabled' : ''}`}
                  >
                    {isInStock ? 'Thêm vào giỏ' : 'Hết hàng'}
                  </Button>
                </div>
              </Card>
            </Col>
          );
        })}
      </Row>

      {/* Pagination */}
      {totalItems > pageSize && (
        <div className="ProductByCategory__pagination">
          <Pagination
            current={currentPage}
            pageSize={pageSize}
            total={totalItems}
            onChange={(page) => handlePageChange(page, pageSize)}
            showSizeChanger={false}
            showTotal={(total, range) => `${range[0]}-${range[1]} của ${total} sản phẩm`}
          />
        </div>
      )}
    </div>
  );
}

export default ProductByCategory;