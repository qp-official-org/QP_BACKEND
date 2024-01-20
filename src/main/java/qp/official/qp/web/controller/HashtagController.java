package qp.official.qp.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
import qp.official.qp.service.HashtagService.HashtagService;
import qp.official.qp.web.dto.HashtagRequestDTO;
import qp.official.qp.web.dto.HashtagResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/hashtag")
public class HashtagController {

    private final HashtagService hashtagService;

    @PostMapping("/")
    public ApiResponse<HashtagResponseDTO.HashtagReturnDTO> generate(@RequestBody HashtagRequestDTO.HashtagDTO request){
        Hashtag hashtag = hashtagService.saveHashtag(request);
        return ApiResponse.onSuccess(SuccessStatus.Hashtag_OK.getCode(), SuccessStatus.Hashtag_OK.getMessage(), HashtagConverter.toHashtagResultDTO(hashtag));
    }

    @GetMapping("/")
    public ApiResponse<HashtagResponseDTO.HashtagReturnDTO> findHashtag(@RequestBody HashtagRequestDTO.HashtagDTO request){
        Hashtag hashtag = hashtagService.findHashtag(request);
        return ApiResponse.onSuccess(SuccessStatus.Hashtag_OK.getCode(), SuccessStatus.Hashtag_OK.getMessage(), HashtagConverter.toHashtagResultDTO(hashtag));
    }

    @DeleteMapping("/{hashtagId}")
    public ApiResponse<HashtagResponseDTO.HashtagReturnDTO> deleteHashtag(@RequestBody HashtagRequestDTO.HashtagDTO request, @PathVariable Long hashtagId){
        try {
            Hashtag hashtag = hashtagService.deleteHashtag(request,hashtagId);
            return ApiResponse.onSuccess(SuccessStatus.Hashtag_OK.getCode(), SuccessStatus.Hashtag_OK.getMessage(), HashtagConverter.toHashtagResultDTO(hashtag));
        } catch (Exception e) {
            return ApiResponse.onFailure(ErrorStatus.HASHTAG_NOT_EXIST.getCode(), ErrorStatus.HASHTAG_NOT_EXIST.getMessage(), null);
        }
    }

}
