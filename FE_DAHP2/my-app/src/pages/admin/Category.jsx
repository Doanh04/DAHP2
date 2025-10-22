import { useEffect, useState } from "react";
import CategoryAdmin from "../../components/UI/adminComponents/CategoryAdmin";
import "../../style/adminSCSS/Category.scss"
import { GetAllCategory } from "../../service/admin/Category";

function Category() {
  const [listCategory, setListCategory] = useState([]);
const [refresh, setRefresh] = useState(false);
const [loading, setLoading] = useState(false);
const [error, setError] = useState(null);


useEffect(()=>{
  const getCategory = async ()=>{
    try{
      const data = await GetAllCategory();
      setListCategory(data)
      setLoading(true)
    }
    catch(error){
      setError("Lỗi không tải được dữ liệu, liên hệ với dev");
    }
  }
  getCategory();
},[refresh])
const handleReload = () => {
    setRefresh((prev) => !prev);
  };
const formProp = {
  listCategory,
  loading,
  error,
  handleReload
}
  return (
    <>
     <CategoryAdmin {...formProp}/>
    </>
  );
}
export default Category;
