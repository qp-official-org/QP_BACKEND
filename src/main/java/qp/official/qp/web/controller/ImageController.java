package qp.official.qp.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "AWS S3 버킷과 DB에 이미지 저장 하는 API",
        description = "원하는 이미지를 AWS S3 버킷과 DB에 저장하는 API로, 클라이언트로부터 이미지를 받아서 해당 이미지를 저장합니다. 이미지를 저장한 후 이미지의 URL을 생성하여 반환합니다.")
    public ApiResponse<ImageResponseDTO.CreateResultDTO> uploadImage(
        @Parameter(
            description = "`multipart/form-data` 형식의 이미지를 `Input`으로 받습니다. `key` 값은 `image` 입니다."
        ) @RequestParam MultipartFile image) throws IOException {
        Image savedImage = imageCommandService.saveImage(image);
        CreateResultDTO result = ImageResponseDTO.CreateResultDTO.builder().url(savedImage.getUrl()).build();
        return ApiResponse.onSuccess(
            SuccessStatus.Image_OK,
            result);
    }

    @DeleteMapping
    @Operation(summary = "AWS S3 버킷과 DB에 존재 하는 이미지를 삭제 하는 API",
    description = "AWS S3 버킷과 DB에서 이미지를 삭제하는 API로, 클라이언트로부터 이미지의 `URL`을 입력 받아서, AWS S3 버킷과 DB에 `URL`에 해당하는 이미지를 삭제합니다.")
    public ApiResponse<?> deleteImage( @RequestBody ImageRequestDTO.ImageDTO request) throws IOException {
        String url = request.getUrl();
        imageCommandService.deleteImage(url);
        return ApiResponse.onSuccess(
            SuccessStatus.Image_OK,
            null);
    }

    @DeleteMapping("/reset-S3")
    @Operation(summary = " <주의> AWS S3 버킷과 DB 내 모든 이미지를 삭제 하는 API",
    description = "프론트엔드 분들의 사용을 금지합니다. AWS S3 버킷과 DB 내 모든 이미지를 삭제하는 API로, 백엔드 테스트나 배포 전 초기화 용도로 사용하는 API입니다." )
    public ApiResponse<?> deleteAllImages() throws  IOException {
        imageCommandService.deleteAllImages();
        return ApiResponse.onSuccess(
            SuccessStatus.Image_OK,
            null);
    }

}
