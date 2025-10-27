import { Button, Col, Input, Layout, Row, Select } from "antd";
import { Content, Footer, Header } from "antd/es/layout/layout";
import "../style/UserScss/UserLayout.scss";
import { Link, Outlet } from "react-router-dom";
import {
  SearchOutlined,
  ShoppingCartOutlined,
  UserOutlined,
} from "@ant-design/icons";

function UserLayout() {
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
                    Xuất VAT cho đỡ 300k
                  </span>
                  <span className="marquee-text">
                    Chào mừng đến với website • Khuyến mãi 50% • Miễn phí vận
                    chuyển • Thu cũ giá ngon - lên đời • Sản phẩm chính hãng -
                    Xuất VAT cho đỡ 300k
                  </span>
                </div>
              </div>
            </Col>
          </Row>
          <Row justify="space-between" align="middle" style={{ width: "100%" }}>
            <div className="Header__ultil">
              <div className="Header__ultil--left">
                <div>
                  <h1>ENDLINK</h1>
                  <div>
                    <Select placeholder="Danh mục">
                      <Select.Option key={"1"} value="danhmuc1">
                        Danh mục 1
                      </Select.Option>
                      <Select.Option key={"2"} value="danhmuc2">
                        Danh mục 2
                      </Select.Option>
                      <Select.Option key={"3"} value="danhmuc3">
                        Danh mục{" "}
                      </Select.Option>
                    </Select>
                  </div>
                </div>
              </div>
              <div className="Header__ultil--center">
                <Input placeholder="Tìm kiếm theo tên" />
              </div>
              <div className="Header__ultil--right">
                <div>
                  <Button>
                    Giỏ hàng <ShoppingCartOutlined />
                  </Button>
                </div>
                <div>
                  <Button>
                    Đăng nhập <UserOutlined />
                  </Button>
                </div>
              </div>
            </div>
          </Row>
        </header>
        <Content><Outlet/></Content>
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
                <Input placeholder="Nhập email của bạn"/>
                <Input placeholder="Nhập số điện thoại của bạn"/>
                <Button>Đăng ký ngay</Button>
              </div>
            </div>

            <div className="ft__2">
                    <h3>Thông tin về chính sách</h3>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Mua hàng và thanh toán Online</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Mua hàng trả góp Online</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Mua hàng trả góp bằng thẻ tín dụng</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Chính sách giao hàng</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Chính sách đổi trả</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Tra điểm Smember</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Xem ưu đãi Smember</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Tra thông tin bảo hành</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Tra cứu hoá đơn điện tử</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Thông tin hoá đơn mua hàng</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Trung tâm bảo hành chính hãng</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Quy định về việc sao lưu dữ liệu</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Chính sách khui hộp sản phẩm Apple</a>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">VAT Refund</a>    
            </div>

            <div className="ft_3">
                    <h3>Dịch vụ và thông tin khác</h3>
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Khách hàng doanh nghiệp (B2B)</a>    
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Ưu đãi thanh toán</a>    
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Quy chế hoạt động</a>    
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Chính sách bảo mật thông tin cá nhân</a>    
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Chính sách Bảo hành</a>    
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Liên hệ hợp tác kinh doanh</a>    
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Tuyển dụng</a>    
                    <a href="https://cellphones.com.vn/chinh-sach-giao-hang">Dịch vụ bảo hành mở rộng</a>     
            </div>
          </div>
        </Footer>
      </Layout>
    </>
  );
}
export default UserLayout;
