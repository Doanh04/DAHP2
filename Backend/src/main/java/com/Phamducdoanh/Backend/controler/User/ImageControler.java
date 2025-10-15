package com.Phamducdoanh.Backend.controler.User;

import com.Phamducdoanh.Backend.Exeption.AppExeption;
import com.Phamducdoanh.Backend.Exeption.ErrorCode;
import com.Phamducdoanh.Backend.Repository.ProductRepository;
import com.Phamducdoanh.Backend.Resolve.ImageMimeTypeResolver;
import com.Phamducdoanh.Backend.entity.ProductEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/product/images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageControler {
    private final ProductRepository productRepository;

    @GetMapping("/{productId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long productId) {

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new AppExeption(ErrorCode.PRODUCTNAME_NOTFOUND));

        byte[] imageBytes = product.getImage();

        if (imageBytes == null || imageBytes.length == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image data not found.");
        }

        // GỌI LỚP TIỆN ÍCH ĐỂ ĐOÁN LOẠI ẢNH TỪ DỮ LIỆU THÔ
        String imageType = ImageMimeTypeResolver.resolveMimeType(imageBytes);

        // Kiểm tra MIME Type
        if (imageType == null || imageType.equals(MediaType.APPLICATION_OCTET_STREAM_VALUE)) {
            // Nếu vẫn không đoán được, có thể trả về lỗi 415 (Unsupported Media Type) hoặc 404
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Could not determine image format.");
        }


        HttpHeaders headers = new HttpHeaders();
        // SỬ DỤNG LOẠI MIME ĐỘNG ĐÃ ĐOÁN ĐƯỢC
        headers.setContentType(MediaType.parseMediaType(imageType));
        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
