import tinh_TP from "../../../Data/tinh_thanhpho.json";
import quan_Huyen from "../../../Data/quan_huyen.json";
import phuong_Xa from "../../../Data/xa_phuong.json";
import { useMemo, useState } from "react";
import { Button, Form, Radio } from "antd";
import COD from "../../../imgs/COD.png";
import VNP from "../../../imgs/VNP.png";

const BASE_URL = "http://localhost:8080";

const asArray = (raw) => {
  if (!raw) return [];
  if (Array.isArray(raw)) return raw;
  return Object.keys(raw).map((key) => {
    const value = raw[key];

    return {
      code: key,
      name: value.name || value.Name || value.full_name || "",
      parent_code: value.parent_code || value.ParentCode || "",
    };
  });
};
function CheckOutUI({ dataCart }) {
  const [tinh, setTinh] = useState([]);
  const [huyen, setHuyen] = useState([]);
  const [xa, setXa] = useState([]);

  // Chuyển dữ liệu từ JSON thành mảng
  const province = useMemo(() => asArray(tinh_TP), []);
  const district = useMemo(() => asArray(quan_Huyen), []);
  const ward = useMemo(() => asArray(phuong_Xa), []);

  // list filtered theo selection
  const filteredDistricts = useMemo(() => {
    if (!tinh) return [];
    return district.filter((d) => {
      return (
        String(d.parent_code) === String(tinh) ||
        String(d.parent) === String(tinh)
      );
    });
  }, [tinh, district]);

  // Khi thay tỉnh: clear huyện + xã
  const handleTinhChange = (e) => {
    const code = e.target.value;
    setTinh(code);
    setHuyen("");
    setXa("");
    // if (onChange) onChange({ province: code, district: "", ward: "" });
  };

  // Khi thay huyện: clear xã
  const handleHuyenChange = (e) => {
    const code = e.target.value;
    setHuyen(code);
    setXa("");
    // if (onChange) onChange({ province: tinh, district: code, ward: "" });
  };

  const handleXaChange = (e) => {
    const code = e.target.value;
    setXa(code);
    // if (onChange) onChange({ province: tinh, district: huyen, ward: code });
  };

  const filteredWards = useMemo(() => {
    if (!huyen) return [];
    return ward.filter((w) => {
      return (
        String(w.parent_code) === String(huyen) ||
        String(w.parent) === String(huyen)
      );
    });
  }, [huyen, ward]);
  console.log("dataCart in CheckoutUI:", dataCart);
  return (
    <>
      <div className="conntainer">
        <div className="container__left">
          <h2>Thông tin giao hàng</h2>
          <Form>
            <div
              style={{ display: "grid", gap: 8, maxWidth: 560, marginTop: 106 }}
            >
              <label>
                Tỉnh / Thành
                <select value={tinh} onChange={handleTinhChange}>
                  <option value="">-- Chọn tỉnh/thành --</option>
                  {province.map((p) => (
                    <option key={p.code} value={p.code}>
                      {p.name || p.Name}
                    </option>
                  ))}
                </select>
              </label>

              <label>
                Quận / Huyện
                <select
                  value={huyen}
                  onChange={handleHuyenChange}
                  disabled={!tinh}
                >
                  <option value="">-- Chọn quận/huyện --</option>
                  {filteredDistricts.map((d) => (
                    <option key={d.code} value={d.code}>
                      {d.name || d.Name}
                    </option>
                  ))}
                </select>
              </label>

              <label>
                Xã / Phường
                <select value={xa} onChange={handleXaChange} disabled={!huyen}>
                  <option value="">-- Chọn xã/phường --</option>
                  {filteredWards.map((w) => (
                    <option key={w.code} value={w.code}>
                      {w.name || w.Name}
                    </option>
                  ))}
                </select>
              </label>
            </div>
            <div>
              <Radio.Group>
                <Radio value="COD">
                  <img
                    src={COD}
                    alt="COD"
                    style={{ width: "50px", marginLeft: "10px" }}
                  />
                  Thanh toán khi nhận hàng
                </Radio>
                <Radio value="VNPAY">
                  <img
                    src={VNP}
                    alt="VNP"
                    style={{ width: "50px", marginLeft: "10px" }}
                  />
                  Thanh toán qua VNPay
                </Radio>
              </Radio.Group>
              <Button type="primary" htmlType="submit">
                Đặt hàng
              </Button>
            </div>
          </Form>
        </div>
        <div className="container__right">
          <h2>Đơn hàng của bạn</h2>
          <div className="container__right--list">
            {dataCart && dataCart.items &&
            dataCart.items.map((item)=>(
                <div className="container__right--item" key={item.cartItemId}>
                    <div className="item--img">
                        <img src={`${BASE_URL}${item.imageUrl}`} alt={item.productName} />
                    </div>
                    <div className="item--info">
                        <h3>{item.productName}</h3>
                        <p>Số lượng: {item.quantity}</p>
                        <p>Đơn giá: {item.unitPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</p>
                    </div>
                </div>
            ))}
          </div>
        </div>
      </div>
    </>
  );
}
export default CheckOutUI;
