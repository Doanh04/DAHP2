import { useEffect, useState } from "react";
import { Button, Col, Form, Input, Row } from "antd";
import { Link } from "react-router-dom";

function SigninLayout({ loading, onFinish, onchange, onreset, formValue, contextHolder }) {
  const [form] = Form.useForm();

  useEffect(()=>{
    form.setFieldsValue(formValue);
  },[formValue, form])
  return (
    <>
    {contextHolder}
      <Form
        form={form}
        name="signinForm"
        labelCol={{ span: 6 }}
        wrapperCol={{ span: 18 }}
        onFinish={onFinish}
        onValuesChange={onchange}
        autoComplete="off"
        className="signinForm"
      >
        <div className="signinForm__title">
          <h1>
            Đăng ký với <strong>ENDLINK</strong>
          </h1>
        </div>
        <Row gutter={[16, 25]} justify={"center"}>
          <Col xl={10} lg={10} md={22} sm={22} xs={22}>
            <Form.Item
              label="Tên tài khoản"
              name="username"
              rules={[
                { required: true, message: "Vui lòng nhập tên của bạn!" },
                { min: 8, message: "Username phải có ít nhất 8 ký tự" },
              ]}
              className="signinForm__FormItem"
            >
              <Input placeholder="Tên tài khoản" />
            </Form.Item>
          </Col>
          <Col xl={10} lg={10} md={22} sm={22} xs={22}>
            <Form.Item
              label="Họ và tên"
              name="name"
              rules={[
                { required: true, message: "Vui lòng nhập đầy đủ họ và tên" },
              ]}
              className="signinForm__FormItem"
            >
              <Input placeholder="Họ tên" />
            </Form.Item>
          </Col>
          <Col xl={10} lg={10} md={22} sm={22} xs={22}>
            <Form.Item
              label="Email"
              name="email"
              rules={[
                { required: true, message: "Vui lòng nhập email của bạn" },
                { type: "email", message: "Email không hợp lệ" },
              ]}
              className="signinForm__FormItem"
            >
              <Input placeholder="Email" />
            </Form.Item>
          </Col>
          <Col xl={10} lg={10} md={22} sm={22} xs={22}>
            <Form.Item
              label="Mật khẩu"
              name="password"
              rules={[
                { required: true, message: "Vui lòng nhập mật khẩu" },
                { min: 6, message: "Mật khẩu phải có ít nhất 6 ký tự" },
              ]}
              hasFeedback
              className="signinForm__FormItem"
            >
              <Input.Password placeholder="Mật khẩu" />
            </Form.Item>
          </Col>
          <Col xl={10} lg={10} md={22} sm={22} xs={22}>
            <Form.Item
              label="Nhập lại mật khẩu"
              name="confirmPassword"
              dependencies={["password"]}
              hasFeedback
              rules={[
                { required: true, message: "Vui lòng xác nhận mật khẩu" },
                ({ getFieldValue }) => ({
                  validator(_, value) {
                    if (!value || getFieldValue("password") === value) {
                      return Promise.resolve();
                    }
                    return Promise.reject(new Error("Mật khẩu không khớp"));
                  },
                }),
              ]}
              className="signinForm__FormItem"
            >
              <Input.Password placeholder="Nhập lại mật khẩu" />
            </Form.Item>
          </Col>
          <Col xl={10} lg={10} md={22} sm={22} xs={22}>
            <Form.Item
              label="Số điện thoại"
              name="phone"
              rules={[
                { required: true, message: "Vui lòng nhập số điện thoại" },
                { pattern: /^0\d{9}$/, message: "Số điện thoại không hợp lệ" },
              ]}
              className="signinForm__FormItem"
            >
              <Input placeholder="Số điện thoại" maxLength={10} />
            </Form.Item>
          </Col>
          <Col xl={24} lg={24} md={24} sm={24} xs={24}>
            <Form.Item
              label="Địa chỉ"
              name="address"
              rules={[{ required: true, message: "Vui lòng nhập địa chỉ" }]}
              className="signinForm__FormItem--address"
            >
              <Input placeholder="Địa chỉ" />
            </Form.Item>
          </Col>
          <Form.Item className="signinForm__button">
            <Button
              type="primary"
              htmlType="submit"
              loading={loading}
              className="signinForm__button--signin"
            >
              Đăng ký
            </Button>
            <Button className="signinForm__button--login">
              <Link to="/auth/token">Đăng nhập</Link>
            </Button>
          </Form.Item>
        </Row>
      </Form>
    </>
  );
}
export default SigninLayout;
