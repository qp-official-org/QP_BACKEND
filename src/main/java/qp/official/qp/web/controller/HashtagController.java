package qp.official.qp.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.HashtagConverter;
import qp.official.qp.web.dto.HashtagRequestDTO;
import qp.official.qp.web.dto.HashtagResponseDTO;
import qp.official.qp.web.dto.HashtagResponseDTO.HashtagReturnDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/hashtag")
public class HashtagController {

    @PostMapping("/")
    public ApiResponse<HashtagResponseDTO.HashtagReturnDTO> generate(HashtagRequestDTO.HashtagDTO request){
        return null;
    }

    @GetMapping("/")
    public ApiResponse<HashtagResponseDTO.HashtagReturnDTO> findHashtag(HashtagRequestDTO.HashtagDTO request){
        return null;
    }

    @DeleteMapping("/{hashtagId}")
    public ApiResponse<HashtagResponseDTO.HashtagReturnDTO> deleteHashtag(HashtagRequestDTO.HashtagDTO request, @PathVariable Long hashtagId)
    {
        return null;
    }




}
