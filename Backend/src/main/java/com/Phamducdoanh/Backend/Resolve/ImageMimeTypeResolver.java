package com.Phamducdoanh.Backend.Resolve;

import org.springframework.http.MediaType;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ImageMimeTypeResolver {
    // Phương thức chính để đoán loại ảnh từ byte[]
    public static String resolveMimeType(byte[] imageBytes) {
        if (imageBytes == null || imageBytes.length == 0) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE; // Mặc định cho dữ liệu nhị phân
        }

        try (ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(imageBytes))) {
            if (iis == null) {
                return MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            // Lấy iterator của các ImageReader có thể đọc dữ liệu này
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            if (readers.hasNext()) {
                // Reader đầu tiên sẽ là reader phù hợp nhất (ví dụ: "jpeg", "png")
                ImageReader reader = readers.next();
                String formatName = reader.getFormatName().toLowerCase();

                // Chuyển đổi tên format sang MIME Type chuẩn
                if (formatName.contains("jpeg") || formatName.contains("jpg")) {
                    return MediaType.IMAGE_JPEG_VALUE;
                }
                if (formatName.contains("png")) {
                    return MediaType.IMAGE_PNG_VALUE;
                }
                if (formatName.contains("gif")) {
                    return MediaType.IMAGE_GIF_VALUE;
                }

                // Trả về loại MIME tiêu chuẩn nếu không xác định được
                return "image/" + formatName;
            }
        } catch (IOException e) {
            // Log lỗi nếu cần
        }

        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
}
