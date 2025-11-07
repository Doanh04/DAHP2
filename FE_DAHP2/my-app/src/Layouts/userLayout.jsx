import { Button, Col, Dropdown, Input, Layout, Row, Select } from "antd";
import { Content, Footer, Header } from "antd/es/layout/layout";
import "../style/UserScss/UserLayout.scss";
import { Link, Outlet, useNavigate } from "react-router-dom";
import {
  DashboardOutlined,
  DownOutlined,
  LogoutOutlined,
  SearchOutlined,
  ShoppingCartOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { useEffect, useState } from "react";
import { GetAllCategory } from "../service/user/Category";
import { decodeTokenAndCheckRole } from "../util/AuthUltil";
import { GetCartItem } from "../service/Cart/Cart";

function UserLayout() {
  const [category, setCategory] = useState([]);
  const [user, setUser] = useState(null);
  const [searchItem, setSearchItem] = useState("");
  const [cartItems, setCartItems] = useState([]);
  const navigate = useNavigate();

  const fetchCartItems = async () => {
    try {
      const data = await GetCartItem();
      setCartItems(data);
    } catch (error) {
      console.error("Lỗi", error);
    }
  };

  useEffect(() => {
    // const name = localStorage.getItem("name");
    try {
      const token = localStorage.getItem("token");
      const decode = decodeTokenAndCheckRole(token);

      if (decode) {
        setUser({
          name: decode.name,
          isAdmin: decode.isAdmin,
        });
      }
    } catch (error) {
      setUser(null);
    }
    fetchCartItems();
  }, []);
  // Xử lý tìm kiếm
  const handleSearch = () => {
    const trimedvalue = searchItem.trim();
    if (trimedvalue) {
      setSearchItem("");
      navigate(
        `/products/filterProduct/?productName=${encodeURIComponent(
          trimedvalue
        )}`
      );
    } else {
      navigate(`/`);
    }
  };
  // Xử lý khi nhấn Enter trong ô tìm kiếm
  const handleKeyDown = (event) => {
    if (event.key === "Enter") {
      event.preventDefault();
      handleSearch(searchItem);
    }
  };
  // Đăng xuất
  const handleLogout = () => {
    localStorage.removeItem("name");
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    setUser(null);
    navigate("/auth/token");
  };

  const menuItems = [
    {
      key: "profile",
      label: (
        <Link
          to="/profile"
          style={{ display: "flex", alignItems: "center", gap: "8px" }}
        >
          <UserOutlined /> Thông tin tài khoản
        </Link>
      ),
    },
    ...(user?.isAdmin
      ? [
          {
            key: "admin",
            label: (
              <Link
                to="/admin"
                style={{ display: "flex", alignItems: "center", gap: "8px" }}
              >
                <DashboardOutlined /> Trang Admin
              </Link>
            ),
          },
        ]
      : []),
    {
      type: "divider",
    },
    {
      key: "logout",
      label: (
        <div
          onClick={handleLogout}
          style={{
            display: "flex",
            alignItems: "center",
            gap: "8px",
            color: "#d93a3a",
          }}
        >
          <LogoutOutlined /> Đăng xuất
        </div>
      ),
      danger: true,
    },
  ];

  useEffect(() => {
    const fetchCategory = async () => {
      try {
        const data = await GetAllCategory();
        setCategory(data);
      } catch (error) {
        throw new Error();
      }
    };
    fetchCategory();
  }, []);
  return (
    <>
      <Layout>
        <header style={{ color: "white" }} className="Header">
          <Row
            justify="space-between"
            align="middle"
            style={{ width: "100%" }}
            className="Header__row"
          >
            <Col flex="auto" style={{ overflow: "hidden" }} span={24}>
              <div className="Header__row--text">
                <div className="marquee-wrapper">
                  <span className="marquee-text">
                    Chào mừng đến với website • Khuyến mãi 50% • Miễn phí vận
                    chuyển • Thu cũ giá ngon - lên đời • Sản phẩm chính hãng -
                    Xuất VAT cho hóa đơn 300k
                  </span>
                  <span className="marquee-text">
                    Chào mừng đến với website • Khuyến mãi 50% • Miễn phí vận
                    chuyển • Thu cũ giá ngon - lên đời • Sản phẩm chính hãng -
                    Xuất VAT cho hóa đơn 300k
                  </span>
                </div>
              </div>
            </Col>
          </Row>
          <Row justify="space-between" align="middle" style={{ width: "100%" }}>
            <div className="Header__ultil">
              <div className="Header__ultil--left">
                <div>
                  <Link to={`/`}>
                    <h1>ENDLINK</h1>
                  </Link>
                  <div>
                    <Select
                      placeholder="Chọn danh mục"
                      style={{ width: "250px" }}
                    >
                      {category.map((item) => (
                        <Select.Option
                          key={item.categoryId}
                          value={item.categoryId}
                        >
                          {item.categoryName}
                        </Select.Option>
                      ))}
                    </Select>
                  </div>
                </div>
              </div>
              <div className="Header__ultil--center">
                <Input
                  type="text"
                  placeholder="Tìm kiếm theo tên"
                  value={searchItem}
                  onChange={(e) => setSearchItem(e.target.value)}
                  onKeyDown={handleKeyDown}
                />
              </div>
              <div className="Header__ultil--right">
                <div>
                  <Button>
                    <Link to={"/cart"}>
                      Giỏ hàng <ShoppingCartOutlined />
                      <p>{cartItems?.items?.length || 0}</p>
                    </Link>
                  </Button>
                </div>
                <div>
                  {user ? (
                    // Hiển thị Dropdown khi đã đăng nhập
                    <Dropdown
                      menu={{ items: menuItems }}
                      trigger={["click"]}
                      placement="bottomRight"
                    >
                      <Button
                        style={{
                          display: "flex",
                          alignItems: "center",
                          gap: "8px",
                        }}
                      >
                        <UserOutlined />
                        {user.name}
                        <DownOutlined style={{ fontSize: "10px" }} />
                      </Button>
                    </Dropdown>
                  ) : (
                    // Hiển thị nút Đăng nhập khi chưa đăng nhập
                    <Link to="/auth/token">
                      <Button>
                        Đăng nhập <UserOutlined />
                      </Button>
                    </Link>
                  )}
                </div>
              </div>
            </div>
          </Row>
        </header>
        <Content>
          <Outlet />
        </Content>
        <Footer>
          <div className="ft">
            <div className="ft__1">
              <div>
                <h3>Tổng đài hỗ trợ miễn phí</h3>
                <p>
                  Mua hàng - bảo hành <strong>0865 393 278</strong> (7h30 -
                  22h00)
                </p>
                <p>Khiếu nại 1800.2063 (0865 393 278)</p>
              </div>
              <div>
                <h3>ĐĂNG KÝ NHẬN TIN KHUYẾN MÃI</h3>
                <p>
                  <strong>Nhận ngay voucher 10% </strong> <br /> Voucher sẽ được
                  gửi sau 24h, chỉ áp dụng cho khách hàng mới
                </p>
                <Input placeholder="Nhập email của bạn" />
                <Input placeholder="Nhập số điện thoại của bạn" />
                <Button>Đăng ký ngay</Button>
              </div>
            </div>

            <div className="ft__2">
              <h3>Thông tin về chính sách</h3>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Mua hàng và thanh toán Online
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Mua hàng trả góp Online
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Mua hàng trả góp bằng thẻ tín dụng
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Chính sách giao hàng
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Chính sách đổi trả
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Tra điểm Smember
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Xem ưu đãi Smember
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Tra thông tin bảo hành
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Tra cứu hoá đơn điện tử
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Thông tin hoá đơn mua hàng
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Trung tâm bảo hành chính hãng
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Quy định về việc sao lưu dữ liệu
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Chính sách khui hộp sản phẩm Apple
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                VAT Refund
              </a>
            </div>

            <div className="ft_3">
              <h3>Dịch vụ và thông tin khác</h3>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Khách hàng doanh nghiệp (B2B)
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Ưu đãi thanh toán
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Quy chế hoạt động
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Chính sách bảo mật thông tin cá nhân
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Chính sách Bảo hành
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Liên hệ hợp tác kinh doanh
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Tuyển dụng
              </a>
              <a href="https://cellphones.com.vn/chinh-sach-giao-hang">
                Dịch vụ bảo hành mở rộng
              </a>
            </div>
          </div>
        </Footer>
      </Layout>
    </>
  );
}
export default UserLayout;
