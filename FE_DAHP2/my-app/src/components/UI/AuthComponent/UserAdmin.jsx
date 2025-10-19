import { DeleteOutlined, EditOutlined, UserAddOutlined } from "@ant-design/icons";
 
function UserAdmin(){
    return(
        <>
 <table className="user__table">
                    <thead>
                        <tr>
                        <td>ID</td>
                        <td>Tên tài khoản</td>
                        <td>Họ tên</td>
                        <td>Email</td>
                        <td>Số điện thoại</td>
                        <td>Địa chỉ</td>
                        <td>Quyền</td>
                        <td>Chức năng</td>
                    </tr>
                    </thead>
                <tbody>
                    <tr>
                        <td>17a23be8-cb64-46fa-a2d7-ac3462c95f37</td>
                        <td>doanhTest11</td>
                        <td>Phạm đức Doanh</td>
                        <td>dgrunt13@gmail.com</td>
                        <td>0865393278</td>
                        <td>Hà nội</td>
                        <td>USER</td>
                        <td>
                            <UserAddOutlined style={{color:"#1890FF"}}/>
                            <EditOutlined style={{color:"#dbdb38ff"}}/>
                            <DeleteOutlined style={{color:"#cd2b2bff"}}/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </>
    )
}
export default UserAdmin