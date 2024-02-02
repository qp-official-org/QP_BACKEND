package qp.official.qp.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
@CrossOrigin
@Validated
@RequestMapping("/hashtag")
public class HashtagController {

    private final HashtagQueryService hashtagQueryService;
    private final HashtagCommandService hashtagCommandService;


    @PostMapping("/")
    public ApiResponse<HashtagResponseDTO.HashtagReturnDTO> generate(@RequestBody HashtagRequestDTO.HashtagDTO request){
        Hashtag hashtag = hashtagQueryService.saveHashtag(request);
        return ApiResponse.onSuccess(SuccessStatus.Hashtag_OK.getCode(), SuccessStatus.Hashtag_OK.getMessage(), HashtagConverter.toHashtagResultDTO(hashtag));
    }

    @GetMapping("/")
    public ApiResponse<HashtagResponseDTO.HashtagReturnDTO> findHashtag(@RequestBody HashtagRequestDTO.HashtagDTO request){
        Hashtag hashtag = hashtagCommandService.findHashtag(request);
        return ApiResponse.onSuccess(SuccessStatus.Hashtag_OK.getCode(), SuccessStatus.Hashtag_OK.getMessage(), HashtagConverter.toHashtagResultDTO(hashtag));
    }

    @DeleteMapping("/{hashtagId}")
    public ApiResponse<HashtagResponseDTO.HashtagReturnDTO> deleteHashtag(@PathVariable Long hashtagId){
        try {
            Hashtag hashtag = hashtagQueryService.deleteHashtag(hashtagId);
            return ApiResponse.onSuccess(SuccessStatus.Hashtag_OK.getCode(), SuccessStatus.Hashtag_OK.getMessage(), HashtagConverter.toHashtagResultDTO(hashtag));
        } catch (Exception e) {
            return ApiResponse.onFailure(ErrorStatus.HASHTAG_NOT_EXIST.getCode(), ErrorStatus.HASHTAG_NOT_EXIST.getMessage(), null);
        }
    }

}
