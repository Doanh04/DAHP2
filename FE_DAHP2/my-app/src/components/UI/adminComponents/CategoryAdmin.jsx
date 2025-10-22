import { Button } from "antd";
import {
  DeleteOutlined,
  EditOutlined,
  UserAddOutlined,
} from "@ant-design/icons";

function CategoryAdmin({
  listCategory,
  handleReload,
  loading,
  error,
}) 
{
  return (
    <>
      <div className="p-4">
        <h2 className="textTitle">Quản lý Danh mục</h2>
        <table className="category__table">
          <thead>
            <tr>
              <td>ID</td>
              <td>Tên danh mục</td>
              <td>Mô tả</td>
              <td>Chức năng</td>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              listCategory.map((item) => (
                <tr key={item.categoryId}>
                  <td>{item.categoryId}</td>
                  <td>{item.categoryName}</td>
                  <td>{item.description}</td>
                    <td>
                      <Button>
                        <EditOutlined style={{ color: "#dbdb38ff" }} />
                      </Button>
                      <Button>
                        <DeleteOutlined style={{ color: "cd2b2bff" }} />
                      </Button>
                    </td>
                </tr>
              ))
            ) : (
              <tr>
              <td colSpan="4" style={{ textAlign: "center", padding: "16px" }}>
                <div className="spiner"></div>
              </td>
            </tr>
            )}
          </tbody>
        </table>
      </div>
    </>
  );
}
export default CategoryAdmin;
