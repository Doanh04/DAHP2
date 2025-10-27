import { useEffect, useState } from "react";
import HomeUser from "../../components/userComponents/HomeUser";
import { GetAllCategory, GetTop10 } from "../../service/user/Category";
import "../../style/UserScss/HomeUser.scss"

function HomeUserManager() {
  const [listCategory, setListCategory] = useState([]);
  const [top10Product, setTop10Product] = useState([])

  useEffect(() => {
    const fetchCategory = async () => {
      try {
        const data = await GetAllCategory();
        const dataTop10 = await GetTop10();
        setListCategory(data);
        setTop10Product(dataTop10)
      } catch (error) {
        throw new Error;
      }
    };
    fetchCategory();
  }, []);
  
  console.log("TOp 10", top10Product)

  const formProps ={
    listCategory,
    top10Product
  };
  return (
    <>
      <HomeUser {...formProps}/>
    </>
  );
}
export default HomeUserManager;
