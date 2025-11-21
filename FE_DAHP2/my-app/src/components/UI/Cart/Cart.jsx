  import { Row, Col, Card, Button, InputNumber, Empty, Skeleton, Divider, Table, Image } from "antd";
  import { DeleteOutlined, ShoppingCartOutlined, MinusOutlined, PlusOutlined } from "@ant-design/icons";
  import { useEffect, useState } from "react";
  import { Link } from "react-router-dom";
  import { DeleteCartItem } from "../../../service/Cart/Cart";

  const formatCurrency = (price) => {
    return price?.toLocaleString('vi-VN') + 'đ';
  };

  function Cart({ cartItems, loading,updateCartItem }) {
    const API_BASE_URL = "http://localhost:8080";
    const [localItems, setLocalItems] = useState(cartItems?.items || []);

    useEffect(() => {
    if (cartItems?.items) {
      setLocalItems(cartItems.items);
    }
  }, [cartItems]);

    const handleQuantityChange = (cartItemId, newQuantity) => {
      if (newQuantity < 1) return;
      
      setLocalItems(prev =>
        prev.map(item =>
          item.cartItemId === cartItemId
            ? { ...item, quantity: newQuantity, totalPrice: item.unitPrice * newQuantity }
            : item
        )
      );
      updateCartItem(cartItemId, newQuantity);
      
    };

    const handleRemoveItem = (cartItemId) => {
      setLocalItems(prev => prev.filter(item => item.cartItemId !== cartItemId));
      
      DeleteCartItem(cartItemId);
    };

    const calculateSubTotal = () => {
      return localItems.reduce((sum, item) => sum + item.totalPrice, 0);
    };

    const columns = [
      {
        title: 'Sản phẩm',
        dataIndex: 'productName',
        key: 'productName',
        width: '40%',
        render: (text, record) => (
          <div className="Cart__product">
            <Image
              src={`${API_BASE_URL}${record.imageUrl}`}
              alt={record.productName}
              width={80}
              height={80}
              style={{ objectFit: 'contain' }}
              preview={false}
            />
            <div className="Cart__product-info">
              <Link to={`/products/${record.productId}`} className="Cart__product-name">
                {record.productName}
              </Link>
            </div>
          </div>
        ),
      },
      {
        title: 'Đơn giá',
        dataIndex: 'unitPrice',
        key: 'unitPrice',
        width: '15%',
        align: 'right',
        render: (price) => (
          <span className="Cart__price">{formatCurrency(price)}</span>
        ),
      },
      {
        title: 'Số lượng',
        dataIndex: 'quantity',
        key: 'quantity',
        width: '20%',
        align: 'center',
        render: (quantity, record) => (
          <div className="Cart__quantity">
            <Button
              icon={<MinusOutlined />}
              size="small"
              onClick={() => handleQuantityChange(record.cartItemId, quantity - 1)}
              disabled={quantity <= 1}
            />
            <InputNumber
              min={1}
              value={quantity}
              onChange={(value) => handleQuantityChange(record.cartItemId, value)}
              className="Cart__quantity-input"
            />
            <Button
              icon={<PlusOutlined />}
              size="small"
              onClick={() => handleQuantityChange(record.cartItemId, quantity + 1)}
            />
          </div>
        ),
      },
      {
        title: 'Thành tiền',
        dataIndex: 'totalPrice',
        key: 'totalPrice',
        width: '15%',
        align: 'right',
        render: (price) => (
          <span className="Cart__total-price">{formatCurrency(price)}</span>
        ),
      },
      {
        title: '',
        key: 'action',
        width: '10%',
        align: 'center',
        render: (_, record) => (
          <Button
            type="text"
            danger
            icon={<DeleteOutlined />}
            onClick={() => handleRemoveItem(record.cartItemId)}
            className="Cart__delete-btn"
          >
            Xóa
          </Button>
        ),
      },
    ];

    if (loading) {
      return (
        <div className="Cart">
          <div className="Cart__header">
            <h1 className="Cart__title">Giỏ hàng của bạn</h1>
          </div>
          <Card className="Cart__content">
            <Skeleton active paragraph={{ rows: 5 }} />
          </Card>
        </div>
      );
    }

    if (!cartItems || !localItems || localItems.length === 0) {
      return (
        <div className="Cart Cart--empty">
          <Card>
            <Empty
              image={<ShoppingCartOutlined style={{ fontSize: 80, color: '#d9d9d9' }} />}
              description={
                <div>
                  <h2>Giỏ hàng trống</h2>
                  <p>Bạn chưa có sản phẩm nào trong giỏ hàng</p>
                </div>
              }
            >
              <Link to="/">
                <Button type="primary" size="large">
                  Tiếp tục mua sắm
                </Button>
              </Link>
            </Empty>
          </Card>
        </div>
      );
    }

    return (
      <div className="Cart">
        {/* Header */}
        <div className="Cart__header">
          <h1 className="Cart__title">
            <ShoppingCartOutlined /> Giỏ hàng của bạn
          </h1>
          <p className="Cart__subtitle">
            Có {localItems.length} sản phẩm trong giỏ hàng
          </p>
        </div>

        <Row gutter={[24, 24]}>
          {/* Danh sách sản phẩm */}
          <Col xs={24} lg={16}>
            {/* Desktop - Table */}
            <Card className="Cart__items-card Cart__items-card--desktop">
              <Table
                dataSource={localItems}
                columns={columns}
                rowKey="cartItemId"
                pagination={false}
                className="Cart__table"
              />
            </Card>

            {/* Mobile - Card list */}
            <div className="Cart__items-card Cart__items-card--mobile">
              {localItems.map(item => (
                <Card key={item.cartItemId} className="Cart__mobile-item">
                  <Row gutter={[12, 12]}>
                    <Col span={8}>
                      <Image
                        src={`${API_BASE_URL}${item.imageUrl}`}
                        alt={item.productName}
                        style={{ width: '100%', objectFit: 'contain' }}
                        preview={false}
                      />
                    </Col>
                    <Col span={16}>
                      <Link to={`/products/${item.productId}`} className="Cart__mobile-name">
                        {item.productName}
                      </Link>
                      <div className="Cart__mobile-price">
                        {formatCurrency(item.unitPrice)}
                      </div>
                      <div className="Cart__mobile-quantity">
                        <Button
                          icon={<MinusOutlined />}
                          size="small"
                          onClick={() => handleQuantityChange(item.cartItemId, item.quantity - 1)}
                          disabled={item.quantity <= 1}
                        />
                        <span className="Cart__mobile-quantity-value">{item.quantity}</span>
                        <Button
                          icon={<PlusOutlined />}
                          size="small"
                          onClick={() => handleQuantityChange(item.cartItemId, item.quantity + 1)}
                        />
                      </div>
                    </Col>
                  </Row>
                  <Divider style={{ margin: '12px 0' }} />
                  <Row justify="space-between" align="middle">
                    <Col>
                      <span className="Cart__mobile-total">
                        Tổng: <strong>{formatCurrency(item.totalPrice)}</strong>
                      </span>
                    </Col>
                    <Col>
                      <Button
                        type="text"
                        danger
                        icon={<DeleteOutlined />}
                        onClick={() => handleRemoveItem(item.cartItemId)}
                      >
                        Xóa
                      </Button>
                    </Col>
                  </Row>
                </Card>
              ))}
            </div>
          </Col>

          {/* Tổng kết đơn hàng */}
          <Col xs={24} lg={8}>
            <Card className="Cart__summary" title="Tổng kết đơn hàng">
              <div className="Cart__summary-row">
                <span>Tạm tính ({localItems.length} sản phẩm):</span>
                <span className="Cart__summary-value">
                  {formatCurrency(calculateSubTotal())}
                </span>
              </div>

              <div className="Cart__summary-row">
                <span>Phí vận chuyển:</span>
                <span className="Cart__summary-value Cart__summary-value--free">
                  Miễn phí
                </span>
              </div>

              <Divider />

              <div className="Cart__summary-row Cart__summary-row--total">
                <span>Tổng cộng:</span>
                <span className="Cart__summary-total">
                  {formatCurrency(calculateSubTotal())}
                </span>
              </div>

              <Link to="/checkout">
              <Button
                type="primary"
                size="large"
                block
                className="Cart__checkout-btn"
              >
                Tiến hành thanh toán
              </Button>
              </Link>

              <Link to="/">
                <Button block size="large" className="Cart__continue-btn">
                  Tiếp tục mua sắm
                </Button>
              </Link>

              {/* Chính sách */}
              <div className="Cart__policies">
                <div className="Cart__policy-item">
                  ✓ Miễn phí vận chuyển cho đơn hàng trên 500.000đ
                </div>
                <div className="Cart__policy-item">
                  ✓ Đổi trả trong vòng 7 ngày
                </div>
                <div className="Cart__policy-item">
                  ✓ Bảo hành chính hãng
                </div>
              </div>
            </Card>
          </Col>
        </Row>
      </div>
    );
  }

  export default Cart;