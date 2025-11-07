import { useSearchParams } from "react-router-dom";
import ProductSearchItem from "../../components/UI/userComponents/ProductSearchItem";
import { useCallback, useEffect, useState } from "react";
import { FilterProduct } from "../../service/user/product";
import "../../style/UserScss/FilterProduct.scss";

function ProductSearchItemManager() {
    const [param] = useSearchParams();
    const initialProductName = param.get("productName");
    
    // Khai báo state và khởi tạo giá trị ban đầu
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [activeFilters, setActiveFilters] = useState({
        productName: initialProductName || "",
        minPrice: null,
        maxPrice: null,
        brand: "", 
        categoryId: null,
    });
    
    // --- HÀM GỌI API CHUNG CHO TẤT CẢ LỌC ---
    const executeFilter = useCallback(async(filtersToApply) => {
        setLoading(true);
        setError(null);
        try {
            //Hàm FilterProduct phải chấp nhận đối tượng filtersToApply
            const data = await FilterProduct(filtersToApply);
            setProducts(data || []);
        }
        catch(error) {
            // SỬA LỖI: Dùng biến 'error' đã được bắt
            setError(error.message); 
            setProducts([]);
        }
        finally {
            setLoading(false);
        }
    }, [FilterProduct]); 

    // --- HÀM XỬ LÝ SỰ KIỆN LỌC TỪ UI CON ---
    const handleFilterChange = (newFilterValue) => {
        const updatedFilters = {
            ...activeFilters,
            ...newFilterValue
        };
        
        // SỬA LỖI GHI ĐÈ: Cập nhật state bằng đối tượng filters đã được tổng hợp
        setActiveFilters(updatedFilters); 
        
        // Gọi API ngay lập tức với bộ lọc mới
        executeFilter(updatedFilters);
    };

    // --- EFFECT LỌC BAN ĐẦU (khi URL thay đổi) ---
    useEffect(() => {
        window.scrollTo(0, 0);
        
        const currentName = initialProductName || "";
        
        // Tạo bộ lọc ban đầu, giữ lại các bộ lọc cũ (Giá, Brand,...)
        const initialLoadFilters = {
            ...activeFilters,
            productName: currentName
        };
        
        // Cập nhật state với tên sản phẩm từ URL
        setActiveFilters(initialLoadFilters);

        // Chỉ gọi API nếu có tên sản phẩm
        if (currentName) {
            executeFilter(initialLoadFilters);
        }
    }, [executeFilter, initialProductName]); // Chạy lại khi URL productName thay đổi

    const formProps = {
        products,
        loading,
        error,
        filters: activeFilters, 
        onFilterChange: handleFilterChange,
    }
    
    return(
        <>
            <ProductSearchItem {...formProps}/>
        </>
    )
}

export default ProductSearchItemManager;