import { Row, Col, Card, Skeleton, Empty, Button, Select, Slider, Input, Tag } from "antd";
import { Link } from "react-router-dom";
import { FilterOutlined, CloseCircleOutlined } from "@ant-design/icons";
import { useState } from "react";
// import "./ProductSearchItem.scss";

const { Option } = Select;

const formatCurrency = (price) => {
  return price?.toLocaleString('vi-VN') + 'đ';
};

// Component Product Card
const ProductCard = ({ product }) => {
  const API_BASE_URL = "http://localhost:8080";
  
  return (
    <Col xs={12} sm={12} md={8} lg={6} xl={6} className="product-search-item">
      <Link to={`/products/${product.productId}`}>
        <Card
          hoverable
          cover={
            <img
              alt={product.productName}
              src={`${API_BASE_URL}${product.imageUrl}`}
              className="product-search-item__image"
            />
          }
          className="product-search-item__card"
        >
          <Card.Meta
            title={
              <div className="product-search-item__title">
                {product.productName}
              </div>
            }
            description={
              <div className="product-search-item__info">
                <div className="product-search-item__price">
                  {formatCurrency(product.price)}
                </div>
                <div className="product-search-item__meta">
                  <Tag color="blue">{product.brand}</Tag>
                  <Tag color="green">{product.categoryName}</Tag>
                </div>
                <div className="product-search-item__stock">
                  {product.quantity > 0 ? (
                    <span className="product-search-item__stock--in">
                      Còn {product.quantity} sản phẩm
                    </span>
                  ) : (
                    <span className="product-search-item__stock--out">
                      Hết hàng
                    </span>
                  )}
                </div>
              </div>
            }
          />
        </Card>
      </Link>
    </Col>
  );
};

function ProductSearchItem({ products, loading, error, filters, onFilterChange }) {
  const [showFilters, setShowFilters] = useState(false);
  const [priceRange, setPriceRange] = useState([0, 50000000]);

  // Xử lý thay đổi khoảng giá
  const handlePriceChange = (value) => {
    setPriceRange(value);
  };

  // Áp dụng bộ lọc giá
  const handleApplyPriceFilter = () => {
    onFilterChange({
      minPrice: priceRange[0],
      maxPrice: priceRange[1]
    });
  };

  // Xử lý thay đổi thương hiệu
  const handleBrandChange = (value) => {
    onFilterChange({ brand: value });
  };
//   Xử lý thay đổi category
  const handleCategoryChange = (value) => {
    onFilterChange({ categoryId: value });
  };

  // Xóa bộ lọc
  const handleClearFilters = () => {
    setPriceRange([0, 50000000]);
    onFilterChange({
      minPrice: null,
      maxPrice: null,
      brand: "",
      categoryId: ""
    });
  };

  // Lấy danh sách thương hiệu unique
  const uniqueBrands = [...new Set(products?.map(p => p.brand))].filter(Boolean);
// Lấy danh sách category và categoryId unique
const uniqueCategories = Array.from(
  new Map(
    products?.map(p => [p.categoryId, { categoryId: p.categoryId, categoryName: p.categoryName }])
  ).values()
);

  return (
    <div className="ProductSearchItem">
      {/* Header với tiêu đề và bộ lọc */}
      <div className="ProductSearchItem__header">
        <div className="ProductSearchItem__header-content">
          <h1 className="ProductSearchItem__title">
            {filters.productName 
              ? `Kết quả tìm kiếm cho "${filters.productName}"`
              : "Tìm kiếm sản phẩm"}
          </h1>
          <p className="ProductSearchItem__count">
            {loading ? "Đang tìm kiếm..." : `Tìm thấy ${products?.length || 0} sản phẩm`}
          </p>
        </div>
        
        <Button
          icon={<FilterOutlined />}
          onClick={() => setShowFilters(!showFilters)}
          className="ProductSearchItem__filter-toggle"
        >
          {showFilters ? "Ẩn bộ lọc" : "Hiện bộ lọc"}
        </Button>
      </div>

      {/* Bộ lọc */}
      {showFilters && (
        <Card className="ProductSearchItem__filters">
          <Row gutter={[16, 16]}>
            <Col xs={24} sm={12} md={8}>
              <div className="ProductSearchItem__filter-item">
                <label>Thương hiệu</label>
                <Select
                  placeholder="Chọn thương hiệu"
                  style={{ width: '100%' }}
                  onChange={handleBrandChange}
                  value={filters.brand || undefined}
                  allowClear
                >
                  {uniqueBrands.map(brand => (
                    <Option key={brand} value={brand}>{brand}</Option>
                  ))}
                </Select>
              </div>
              <div className="ProductSearchItem__filter-item">
                <label>Danh mục</label>
                <Select
                  placeholder="Chọn danh mục"
                  style={{ width: '100%' }}
                  onChange={handleCategoryChange}
                  value={filters.categoryId || undefined}
                  allowClear
                >
                  {uniqueCategories.map(cate => (
                    <Option key={cate.categoryId} value={cate.categoryId}>{cate.categoryName}</Option>
                  ))}
                </Select>
              </div>
            </Col>

            <Col xs={24} sm={12} md={12}>
              <div className="ProductSearchItem__filter-item">
                <label>Khoảng giá</label>
                <Slider
                  range
                  min={0}
                  max={50000000}
                  step={100000}
                  value={priceRange}
                  onChange={handlePriceChange}
                  tooltip={{
                    formatter: (value) => formatCurrency(value)
                  }}
                />
                <div className="ProductSearchItem__price-range">
                  <span>{formatCurrency(priceRange[0])}</span>
                  <span>-</span>
                  <span>{formatCurrency(priceRange[1])}</span>
                </div>
              </div>
            </Col>

            <Col xs={24} sm={24} md={4}>
              <div className="ProductSearchItem__filter-actions">
                <Button
                  type="primary"
                  onClick={handleApplyPriceFilter}
                  block
                  style={{ marginBottom: '8px' }}
                >
                  Áp dụng
                </Button>
                <Button
                  icon={<CloseCircleOutlined />}
                  onClick={handleClearFilters}
                  block
                >
                  Xóa bộ lọc
                </Button>
              </div>
            </Col>
          </Row>
        </Card>
      )}

      {/* Hiển thị bộ lọc đang áp dụng */}
      {(filters.brand || filters.minPrice || filters.maxPrice) && (
        <div className="ProductSearchItem__active-filters">
          <span>Bộ lọc đang áp dụng:</span>
          {filters.brand && (
            <Tag closable onClose={() => onFilterChange({ brand: "" })}>
              Thương hiệu: {filters.brand}
            </Tag>
          )}
          {filters.categoryId && (
            <Tag closable onClose={() => onFilterChange({ categoryId: null })}>
              Danh mục: {uniqueCategories.find(cate => cate.categoryId === filters.categoryId)?.categoryName || filters.categoryId}
            </Tag>
          )}
          {(filters.minPrice || filters.maxPrice) && (
            <Tag closable onClose={() => onFilterChange({ minPrice: null, maxPrice: null })}>
              Giá: {formatCurrency(filters.minPrice || 0)} - {formatCurrency(filters.maxPrice || 50000000)}
            </Tag>
          )}
        </div>
      )}

      {/* Nội dung */}
      <div className="ProductSearchItem__content">
        {loading ? (
          <Row gutter={[16, 16]}>
            {[1, 2, 3, 4, 5, 6, 7, 8].map(i => (
              <Col key={i} xs={12} sm={12} md={8} lg={6} xl={6}>
                <Card>
                  <Skeleton active avatar paragraph={{ rows: 3 }} />
                </Card>
              </Col>
            ))}
          </Row>
        ) : error ? (
          <div className="ProductSearchItem__error">
            <Empty
              description={
                <div>
                  <h3>Có lỗi xảy ra</h3>
                  <p>{error}</p>
                </div>
              }
            />
          </div>
        ) : products && products.length > 0 ? (
          <Row gutter={[16, 16]}>
            {products.map(product => (
              <ProductCard key={product.productId} product={product} />
            ))}
          </Row>
        ) : (
          <div className="ProductSearchItem__empty">
            <Empty
              description={
                <div>
                  <h3>Không tìm thấy sản phẩm</h3>
                  <p>
                    {filters.productName 
                      ? `Không có kết quả cho "${filters.productName}"`
                      : "Hãy thử tìm kiếm với từ khóa khác"}
                  </p>
                </div>
              }
            />
          </div>
        )}
      </div>
    </div>
  );
}

export default ProductSearchItem;