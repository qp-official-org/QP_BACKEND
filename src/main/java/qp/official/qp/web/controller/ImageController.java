package qp.official.qp.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.web.dto.ImageResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {
    @PostMapping
    public ApiResponse<ImageResponseDTO.CreateResultDTO> uploadImage(
        @RequestParam MultipartFile image
    ) {
        return null;
    }

    @DeleteMapping
    public ApiResponse<?> deleteImage() {
        return null;
    }
}
