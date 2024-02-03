package qp.official.qp.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.HashtagConverter;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.service.HashtagService.HashtagCommandService;
import qp.official.qp.service.HashtagService.HashtagQueryService;
import qp.official.qp.web.dto.HashtagRequestDTO;
import qp.official.qp.web.dto.HashtagResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/hashtag")
public class HashtagController {

    private final HashtagQueryService hashtagQueryService;
    private final HashtagCommandService hashtagCommandService;


    @PostMapping("/")
    @Operation(summary = "해시태그 생성 API", description = "Request Body에 생성할 해시태그를 입력하세요.")
    public ApiResponse<HashtagResponseDTO.HashtagReturnDTO> generate(@RequestBody HashtagRequestDTO.HashtagDTO request){
        Hashtag hashtag = hashtagQueryService.saveHashtag(request);
        return ApiResponse.onSuccess(SuccessStatus.Hashtag_OK.getCode(), SuccessStatus.Hashtag_OK.getMessage(), HashtagConverter.toHashtagResultDTO(hashtag));
    }

    @GetMapping("/")
    @Operation(summary = "해시태그 조회 API", description = "Request Body에 조회할 해시태그를 입력하세요.")
    public ApiResponse<HashtagResponseDTO.HashtagReturnDTO> findHashtag(@RequestBody HashtagRequestDTO.HashtagDTO request){
        Hashtag hashtag = hashtagCommandService.findHashtag(request);
        return ApiResponse.onSuccess(SuccessStatus.Hashtag_OK.getCode(), SuccessStatus.Hashtag_OK.getMessage(), HashtagConverter.toHashtagResultDTO(hashtag));
    }

    @DeleteMapping("/{hashtagId}")
    @Operation(summary = "해시태그 삭제 API", description = "path variable로 삭제 할 hashtagId를 입력하세요.")
    public ApiResponse<HashtagResponseDTO.HashtagReturnDTO> deleteHashtag(@PathVariable Long hashtagId){
        try {
            Hashtag hashtag = hashtagQueryService.deleteHashtag(hashtagId);
            return ApiResponse.onSuccess(SuccessStatus.Hashtag_OK.getCode(), SuccessStatus.Hashtag_OK.getMessage(), HashtagConverter.toHashtagResultDTO(hashtag));
        } catch (Exception e) {
            return ApiResponse.onFailure(ErrorStatus.HASHTAG_NOT_EXIST.getCode(), ErrorStatus.HASHTAG_NOT_EXIST.getMessage(), null);
        }
    }

}
