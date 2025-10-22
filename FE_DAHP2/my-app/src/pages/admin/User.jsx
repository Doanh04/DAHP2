import {
  DeleteOutlined,
  EditOutlined,
  UserAddOutlined,
} from "@ant-design/icons";
import "../../style/adminSCSS/UserAdmin.scss";
import UserAdmin from "../../components/UI/adminComponents/UserAdmin";
import { useEffect, useState, useMemo } from "react";
import {
  DeleteUser,
  GetAllUser,
  UpdateUser,
} from "../../service/admin/UserAdmin";
import { GetRole } from "../../service/Auth/Roles";
import Swal from "sweetalert2";
function UserManagement() {
  const [listAccout, setListAcout] = useState([]);
  const [listRole, setListRole] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [refresh, setRefresh] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedAccount, setSelectedAccount] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [displayedList, setDisplayedList] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await GetAllUser();
        const role = await GetRole();
        setListAcout(data);
        setListRole(role);
        setDisplayedList(data);
        setLoading(true);
      } catch (error) {
        setError("Lỗi không tải được dữ liệu, liên hệ với dev");
      }
    };
    fetchData();
  }, [refresh]);

  const handleReload = () => {
    setRefresh((prev) => !prev);
    setSearchTerm("");
  };
  const handleSearch = () => {
    const query = searchTerm.trim().toLowerCase();
    
    if (query === '') {
        setDisplayedList(listAccout);
    } else {
        const filteredList = listAccout.filter(account =>
            account.username.toLowerCase().includes(query) 
        );
        setDisplayedList(filteredList);
    }
  };
  const handleInputChange = (e) => {
    const value = e.target.value;
    setSearchTerm(value);

    if (value.trim() === "") {
      setDisplayedList(listAccout);
    }
  };
  const showModal = (account) => {
    const initialRoles = account.roles.map((role) => role.roleName);

    const initialData = {
      ...account,
      roles: initialRoles,
    };

    setSelectedAccount(initialData);
    setIsModalOpen(true);
  };

  const handleOk = () => {
    setIsModalOpen(false);
    setSelectedAccount(null);
  };

  const handleCancel = () => {
    setIsModalOpen(false);
    setSelectedAccount(null);
  };

  const handleUpdateAccount = async (values) => {
    const currentUsername = selectedAccount?.username;
    const currentPassword = selectedAccount?.password;

    const payload = {
      userId: values.userId,

      username: currentUsername,

      name: values.name,
      email: values.email,

      password: currentPassword,

      phone: values.phone,
      address: values.address,

      roles: values.roles,
    };

    try {
      await UpdateUser(payload);

      handleOk();
      handleReload();

      return { success: true, message: "Cập nhật tài khoản thành công!" };
    } catch (error) {
      const errorMessage = error.message || "Lỗi không thể cập nhật tài khoản.";

      return { success: false, message: errorMessage };
    }
  };

  const DeleteAccout = async (userId) => {
    try {
      const response = await DeleteUser(userId);

      if (response.code === 200 && response.success === true) {
        return { success: true, message: response.message || "Xóa thành công" };
      } else {
        throw new Error(response.message || "Xóa tài khoản không thành công.");
      }
    } catch (error) {
      throw new Error(error.message || "Lỗi phía server");
    }
  };

  const formProp = {
    listAccout: displayedList,
    listRole,
    loading,
    error,
    isModalOpen,
    handleReload,
    handleOk,
    handleCancel,
    showModal,
    selectedAccount,
    handleUpdateAccount,
    DeleteAccout,
    searchTerm,
    handleSearch,
    handleInputChange,
  };
  return (
    <>
      <UserAdmin {...formProp} />
    </>
  );
}
export default UserManagement;
