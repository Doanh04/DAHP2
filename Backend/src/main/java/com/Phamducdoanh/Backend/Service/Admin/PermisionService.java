package com.Phamducdoanh.Backend.Service.Admin;

import com.Phamducdoanh.Backend.DTO.Request.PermisionRequestDTO;
import com.Phamducdoanh.Backend.DTO.Response.PermistionResponseDTO;
import com.Phamducdoanh.Backend.Maper.PermisionMaper;
import com.Phamducdoanh.Backend.Repository.PermisionRepository;
import com.Phamducdoanh.Backend.entity.PermisstionEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class PermisionService {
    PermisionRepository  permisionRepository;
    PermisionMaper permisionMaper;
//    Hàm tạo permision
    public PermistionResponseDTO creatPermision(PermisionRequestDTO request) {
        PermisstionEntity permisstion = permisionMaper.toPermisionEntity(request);
        permisstion = permisionRepository.save(permisstion);
        return permisionMaper.toPerMisionResponse(permisstion);
    }

//    Hàm get List permision
    public List<PermistionResponseDTO> getAll(){
        var permisions = permisionRepository.findAll();
        return permisions.stream().map(permisionMaper::toPerMisionResponse).toList();
    }

    public void delete(String permisionName) {
        permisionRepository.deleteById(permisionName);
    }
}
