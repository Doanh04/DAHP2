import { useEffect, useState } from "react";
import HomeUser from "../../components/UI/userComponents/HomeUser";
import { GetAllCategory, GetTop10 } from "../../service/user/Category";
import "../../style/UserScss/HomeUser.scss";
import { GetAllProduct } from "../../service/user/product"; 

function HomeUserManager() {
    const [listCategory, setListCategory] = useState([]);
    const [top10Product, setTop10Product] = useState([]);
    
    // STATE PHÂN TRANG: Khởi tạo an toàn (đảm bảo content là mảng)
    const [productPaginationData, setProductPaginationData] = useState({ 
        content: [],
        totalElements: 0,
        number: 0, // Spring Page number (0-based)
        size: 20,  // Mặc định 20 mục/trang
    });
    
    const [refresh, setRefresh] = useState(false);
    
    // HÀM FETCH SẢN PHẨM CÓ THAM SỐ PHÂN TRANG (page, size)
    const fetchProducts = async (page, size) => {
        try {
            // Giả sử GetAllProduct(page, size) gửi request phân trang đến server
            const responseProduct = await GetAllProduct(page, size);
            if (responseProduct) {
                // Cập nhật state với dữ liệu phân trang mới
                setProductPaginationData(responseProduct);
            }
        } catch (error) {
            console.error("Lỗi tải sản phẩm phân trang:", error);
        }
    };

    // HÀM XỬ LÝ KHI NGƯỜI DÙNG NHẤN NÚT PHÂN TRANG (1-based)
    const handlePageChange = (page, pageSize) => {
        // Ant Design gửi page 1-based, Spring Boot cần page 0-based
        const serverPage = page - 1; 
        fetchProducts(serverPage, pageSize);
    };

    useEffect(() => {
        const fetchInitialData = async () => {
            // Tải dữ liệu chính (Sản phẩm phân trang) trước
            await fetchProducts(0, productPaginationData.size); 
            
            try {
                // Tải dữ liệu phụ sau
                const data = await GetAllCategory();
                const dataTop10 = await GetTop10();
                
                setListCategory(data);
                setTop10Product(dataTop10);
            } catch (error) {
                console.error("Lỗi khi tải dữ liệu trang chủ:", error);
            }
        };
        fetchInitialData();
    }, [refresh]);

    const handleReload = () => {
        // Tải lại trang hiện tại
        fetchProducts(productPaginationData.number, productPaginationData.size); 
        setRefresh((prev) => !prev);
    };

    const formProps = {
        listCategory,
        top10Product,
        product: productPaginationData, 
        handleReload,
        handlePageChange, 
    };
    
    return (
        <HomeUser {...formProps} />
    );
}
export default HomeUserManager;