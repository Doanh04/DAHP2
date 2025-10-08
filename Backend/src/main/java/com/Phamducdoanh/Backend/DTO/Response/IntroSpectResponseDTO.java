package com.Phamducdoanh.Backend.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
//DTO trả ra sự hợp lệ hoặc còn hạn không của token
public class IntroSpectResponseDTO {
    boolean valid;
}
