import { Button, Card, Carousel, Col, Row } from "antd";
import { Link } from "react-router-dom";
import { banners } from "../Banner/Banner";
import { useEffect, useState } from "react";
import { GiftOutlined } from "@ant-design/icons";

// Hàm trợ giúp định dạng tiền tệ (Định nghĩa ngoài component)
const formatCurrency = (price) => {
    return price?.toLocaleString('vi-VN') + 'đ';
};

// Component con ProductCard (Định nghĩa ngoài component)
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

// Định nghĩa setting Carousel (Định nghĩa ngoài component)
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


function HomeUser({ listCategory, top10Product }) {
  const [userData, setUserData] = useState(null);
  
  

  useEffect(() => {
    async function fetchUserDataFromStorage() {
      const nameStorage = localStorage.getItem("name");

      if (nameStorage) {
        try {
          const parsedName = JSON.parse(nameStorage);
          setUserData(parsedName);
        } catch (error) {
          console.warn(
            "Dữ liệu 'name' không phải JSON, sử dụng giá trị thô.",
            error
          );
          setUserData(nameStorage);
        }
      } else {
        console.log("Không tìm thấy key 'name' trong Local Storage.");
        setUserData(null);
      }
    }

    fetchUserDataFromStorage();
  }, []);


  const renderUserStatus = () => {
    // ... (logic renderUserStatus giữ nguyên)
    if (userData) {
      // Đã đăng nhập
      return (
        <Card title="Tài khoản của bạn" size="small">
          <p>Xin chào, <br /> <strong>{userData.replace(/"/g, '')}</strong></p>
          <Button type="primary" block size="small" className="btn__profile">
            <Link to="/profile">Xem hồ sơ</Link>
          </Button>
        </Card>
      );
    }
    // Chưa đăng nhập
    return (
      <Card title="Chào mừng!" size="small">
        <p>Vui lòng đăng nhập để nhận ưu đãi.</p>
        <Button block style={{ marginBottom: '8px' }}>
          <Link to="/auth/token">Đăng nhập</Link>
        </Button>
        <Button block type="default">
          <Link to="/auth/createuser">Đăng ký</Link>
        </Button>
      </Card>
    );
  };

  // Hàm Render Nội dung Cột 2 (Ưu đãi)
  const renderPromotions = () => {
    // ... (logic renderPromotions giữ nguyên)
    return (
      <Card 
        title={<><GiftOutlined /> Ưu đãi Giáo dục</>} 
        size="small"
        className="Top__right--title"
      >
        <p style={{ margin: '0 0 5px 0', fontWeight: 'bold' }}>
            Đăng ký&nbsp;nhận ưu đãi <br />
            Tựu trường lên cấp&nbsp;- Máy mới lên đời
        </p>
        <ul style={{ paddingLeft: '20px', margin: 0 }}>
            <li>Laptop&nbsp;giảm thêm đến 500K</li>
            <li>Thu cũ lên đời giá hời</li>
            <li>iPhone trợ giá&nbsp;đến 5 triệu</li>
            <li>Samsung trợ giá&nbsp;đến 4 triệu</li>
            <li>Laptop trợ giá&nbsp;đến 4 triệu</li>
        </ul>
      </Card>
    );
  };

  return (
    <>
      <div className="Top">
        <Row gutter={[20, 10]}>
          <Col span={6} className="Top__left">
            <div>
              {Array.isArray(listCategory) && listCategory.map((item) => (
                <Link
                  key={item.categoryId}
                  to={`/products/category/${item.categoryId}`}
                >
                  {item.categoryName}
                </Link>
              ))}
            </div>
          </Col>
          <Col span={12} className="Top__center">
            <Carousel
              autoplay
              autoplaySpeed={2000}
              dots={true}
              draggable={true}
              arrows={true}
            >
              {banners.map((banner) => (
                <div key={banner.id} className="Banner__slide">
                  <Link to={banner.link}>
                    <img src={banner.image} alt={banner.alt} />
                    {banner.title && (
                      <div className="Banner__title">
                        <h3>{banner.title}</h3>
                      </div>
                    )}
                  </Link>
                </div>
              ))}
            </Carousel>
          </Col>
          <Col span={6} className="Top__right">
              <Row gutter={[0, 16]}> 
                  <Col span={24}>
                      {renderUserStatus()} 
                  </Col>
                  <Col span={24}>
                      {renderPromotions()} 
                  </Col>
              </Row>
          </Col>
        </Row>
      </div>
      <div className="Contend" style={{ padding: '20px 50px' }}>
                <div className="product-highlight-header">
                    <div className="ribbon-container">
                        {/* Phần Nhãn dán đỏ nổi bật */}
                        <div className="ribbon-label">
                            Danh sách các sản phẩm nổi bật
                        </div>
                        {/* Phần nền dài phía sau (Màu hồng nhạt) */}
                        <div className="ribbon-base"></div>
                    </div>
                </div>
                
                {Array.isArray(top10Product) ? (
                    top10Product.map((category) => (
                        category.topProducts && category.topProducts.length > 0 && (
                            <Card
                                key={category.categoryId}
                                title={
                                    <Link to={`/products/category/${category.categoryId}`} style={{ fontSize: '18px', fontWeight: 'bold', color: '#1890ff' }}>
                                        {category.categoryName} ({category.topProducts.length} sản phẩm)
                                    </Link>
                                }
                                bordered={false}
                                style={{ marginBottom: 30, borderTop: '2px solid #1890ff' }}
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
                    // Hiển thị loading hoặc thông báo nếu dữ liệu chưa sẵn sàng
                    <div>Đang tải sản phẩm nổi bật...</div>
                )}
            </div>
    </>
  );
}
export default HomeUser;