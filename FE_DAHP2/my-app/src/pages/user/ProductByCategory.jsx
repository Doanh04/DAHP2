import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import ProductByCategory from "../../components/UI/userComponents/ProductByCategory";
// Giả sử hàm API đã sửa của bạn là GetProductByCategory
import "../../style/UserScss/ProductByCategory.scss"
import { GetProductByCategory } from "../../service/user/product";

function ProductByCategoryManager() {
  const { categoryId } = useParams();

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại (FE dùng 1-based)
  const [pageSize, setPageSize] = useState(15); // Kích thước trang (15 items/trang)
  const [totalItems, setTotalItems] = useState(0);

  useEffect(() => {
    window.scrollTo(0, 0);

    const pageIndex = currentPage - 1;

    const fetchProducts = async () => {
      if (!categoryId) return;

      setLoading(true);
      setError(null);

      try {
        const pageData = await GetProductByCategory(
          categoryId,
          pageIndex,
          pageSize
        );

        setProducts(pageData.content || []);
        setTotalItems(pageData.totalElements || 0);
      } catch (err) {
        setError(err.message);
        setProducts([]);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, [categoryId, currentPage, pageSize]);

  const handlePageChange = (page, size) => {
    setCurrentPage(page);
    setPageSize(size);
  };

  const componentProps = {
    products,
    loading,
    error,
    currentPage,
    pageSize,
    totalItems,
    categoryId,
    handlePageChange,
  };
// console.log(totalItems)
  return (
    <>
      <ProductByCategory {...componentProps} />
    </>
  );
}

export default ProductByCategoryManager;
