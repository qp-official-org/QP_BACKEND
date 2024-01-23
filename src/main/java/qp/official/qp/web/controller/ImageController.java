package qp.official.qp.web.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.domain.Image;
import qp.official.qp.service.ImageService.ImageService;
import qp.official.qp.web.dto.ImageResponseDTO;
import qp.official.qp.web.dto.ImageResponseDTO.CreateResultDTO;
import qp.official.qp.web.dto.ImageResponseDTO.CreateResultDTO.CreateResultDTOBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/")
    public ApiResponse<ImageResponseDTO.CreateResultDTO> uploadImage(@RequestParam MultipartFile image) throws IOException {
        Image savedImage = imageService.saveImage(image);
        CreateResultDTO result = ImageResponseDTO.CreateResultDTO.builder().url(savedImage.getUrl()).build();
        return ApiResponse.onSuccess(SuccessStatus.Image_OK.getCode(), SuccessStatus.Image_OK.getMessage(), result);
    }

    @DeleteMapping
    public ApiResponse<?> deleteImage() {
        return null;
    }
}
