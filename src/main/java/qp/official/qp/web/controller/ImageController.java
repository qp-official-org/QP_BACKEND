package qp.official.qp.web.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.domain.Image;
import qp.official.qp.service.ImageService.ImageCommandService;
import qp.official.qp.web.dto.ImageRequestDTO;
import qp.official.qp.web.dto.ImageResponseDTO;
import qp.official.qp.web.dto.ImageResponseDTO.CreateResultDTO;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/images")
@Slf4j
public class ImageController {

    private final ImageCommandService imageCommandService;

    @PostMapping
    public ApiResponse<ImageResponseDTO.CreateResultDTO> uploadImage(@RequestParam MultipartFile image) throws IOException {
        Image savedImage = imageCommandService.saveImage(image);
        CreateResultDTO result = ImageResponseDTO.CreateResultDTO.builder().url(savedImage.getUrl()).build();
        return ApiResponse.onSuccess(
            SuccessStatus.Image_OK,
            result);
    }

    @DeleteMapping
    public ApiResponse<?> deleteImage(@RequestBody ImageRequestDTO.ImageDTO request) throws IOException {
        String url = request.getUrl();
        imageCommandService.deleteImage(url);
        return ApiResponse.onSuccess(
            SuccessStatus.Image_OK,
            null);
    }

    @DeleteMapping("/reset-S3")
    public ApiResponse<?> deleteAllImages() throws  IOException {
        imageCommandService.deleteAllImages();
        return ApiResponse.onSuccess(
            SuccessStatus.Image_OK,
            null);
    }

}
