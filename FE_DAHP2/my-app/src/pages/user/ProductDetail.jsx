import { useParams } from "react-router-dom";
import ProdductDetail from "../../components/UI/userComponents/ProductDetail";
import { useEffect, useState } from "react";
import { GetById } from "../../service/user/ProductDetail";
import "../../style/UserScss/ProductDetail.scss"
import { GetTop10 } from "../../service/user/Category";

function ProductDetailManager(){
    const {productId} = useParams();
    const [product, setProduct] = useState([])
    const [loading, setLoading] = useState([false])
    const [error, setError] = useState(null)
    const [top10Product, setTop10Product] = useState([]);
    useEffect(()=>{
        window.scrollTo(0, 0);
        const fetchProduct = async ()=>{
            setLoading(true)
            try{
                const data = await GetById(productId)
                const dataTop10 = await GetTop10();
                setProduct(data);
                setTop10Product(dataTop10)
                setLoading(false)
            }
            catch(error){
                setError("Không thể tải được dữ liệu sản phẩm")
            }
            finally{
                setLoading(false)
            }
        }
        fetchProduct();
    },[productId])
    const formProp={
        product,
        loading,
        error,
        top10Product
    }
    return(
        <>
            <ProdductDetail {...formProp} />
        </>
    )
}
export default ProductDetailManager;