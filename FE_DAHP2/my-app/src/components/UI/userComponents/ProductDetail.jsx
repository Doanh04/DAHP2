import { Button, Card, Col, InputNumber, Row, Tag, Divider, Skeleton, Carousel, notification } from "antd";
import { ShoppingCartOutlined, CheckCircleOutlined, CloseCircleOutlined } from "@ant-design/icons";
import { useState } from "react";
import { Link } from "react-router-dom";
import { AddToCart } from "../../../service/Cart/Cart";
// import { formatCurrency } from "./HomeUser";
// import "./ProductDetail.scss";

// Component con ProductCard
const ProductCard = ({ product }) => {
    const API_BASE_URL = "http://localhost:8080"; 
    return (
        <div className="product-carousel-item" style={{ textAlign: 'center', padding: '10px 5px' }}>
            <Link to={`/products/${product.productId}`}>
                <Card
                    hoverable
                    cover={
                        <img 
                            alt={product.productName} 
                            src={`${API_BASE_URL}${product.imageUrl}`} 
                            style={{ height: 160, objectFit: 'contain' }}
                        />
                    }
                    bodyStyle={{ padding: '10px' }}
                >
                    <Card.Meta 
                        title={<span style={{ fontSize: '13px', whiteSpace: 'normal' }}>{product.productName}</span>} 
                        description={<strong style={{ color: '#cf1322' }}>{formatCurrency(product.price)}</strong>}
                    />
                </Card>
            </Link>
        </div>
    );
};

const formatCurrency = (price) => {
  return price?.toLocaleString('vi-VN') + 'đ';
};

// Setting Carousel
const carouselSettings = {
    autoplay: true,
    autoplaySpeed: 3500,
    arrows: true,
    draggable: true,
    dots: false,
    responsive: [
        { breakpoint: 1200, settings: { slidesToShow: 4, slidesToScroll: 4 } },
        { breakpoint: 992, settings: { slidesToShow: 3, slidesToScroll: 3 } },
        { breakpoint: 768, settings: { slidesToShow: 2, slidesToScroll: 2 } },
        { breakpoint: 480, settings: { slidesToShow: 1, slidesToScroll: 1 } },
        { breakpoint: 2000, settings: { slidesToShow: 5, slidesToScroll: 5 } }
    ]
};


function ProductDetail({ product, loading, error, top10Product }) {
  const [quantity, setQuantity] = useState(1);
  const [api, contextHolder] = notification.useNotification();
  const API_BASE_URL = "http://localhost:8080";

  // Xử lý thay đổi số lượng
  const handleQuantityChange = (value) => {
    if (value >= 1 && value <= product.quantity) {
      setQuantity(value);
    }
  };

  // Xử lý notifycation
  const openNotification =(type, title, description)=>{
    api[type]({
      message:title,
      description:description,
      placement:"topRight"
    })
  }

  // Xử lý thêm vào giỏ hàng
  const handleAddToCart = async () => {
    try{
      await AddToCart(product.productId, quantity);
      setTimeout(() => {
        openNotification(
        "success",
        "Thành công",
        `Đã thêm ${quantity} sản phẩm vào giỏ hàng.`
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
      {contextHolder}
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
      <div className="Contend2" style={{ padding: '20px 50px' }}>
         <div className="product-highlight-header">
            <div className="ribbon-container">
                <div className="ribbon-label">
                   Danh sách các sản phẩm mới
                </div>
                <div className="ribbon-base"></div>
            </div>
        </div>
        
        {Array.isArray(top10Product) ? (
            top10Product.map((category) => (
                category.topProducts && category.topProducts.length > 0 && (
                    <Card
                        key={category.categoryId}
                        title={
                            <Link to={`/products/category/${category.categoryId}`} className="product-link">
                                {category.categoryName} ({category.topProducts.length} sản phẩm)
                            </Link>
                        }
                        bordered={false}
                        className="product-top"
                    >
                        <Carousel {...carouselSettings}>
                            {category.topProducts.map((product) => (
                                <ProductCard key={product.productId} product={product} />
                            ))}
                        </Carousel>
                    </Card>
                )
            ))
        ) : (
            <div>Đang tải sản phẩm nổi bật...</div>
        )}
      </div>
    </div>
  );
}

export default ProductDetail;