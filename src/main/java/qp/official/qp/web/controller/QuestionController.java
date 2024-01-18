package qp.official.qp.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.web.dto.QuestionRequestDTO;
import qp.official.qp.web.dto.QuestionResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/questions")
public class QuestionController {

    // 질문 작성
    @PostMapping("/")
    public ApiResponse<QuestionResponseDTO.QuestionReturnDTO> save(@RequestBody QuestionRequestDTO.QuestionPostDTO request){
        return null;
    }

    // 특정 질문 조회
    @GetMapping("/{questionId}")
    public ApiResponse<QuestionResponseDTO.QuestionFindReturnDTO> findQuestion(@PathVariable Long questionId){
        return null;
    }

    // 질문 페이징 조회
    @GetMapping(path = "/", params = {"page, size"})
    public ApiResponse<QuestionResponseDTO.QuestionPagingTitleReturnDTO> findQuestionByPaging(@RequestParam Integer page, @RequestParam Integer size){
        return null;
    }

    // 질문 검색 조회
    @GetMapping(path = "/", params = {"title", "page", "size"})
    public ApiResponse<QuestionResponseDTO.QuestionPagingTitleReturnDTO> findQuestionByTitle(
        @RequestParam String title,
        @RequestParam Integer page,
        @RequestParam Integer size)
    {
        return null;
    }

    // 질문 삭제
    @DeleteMapping("/{questionId}")
    public ApiResponse<QuestionResponseDTO.QuestionReturnDTO> deleteQuestion(@PathVariable Long questionId){
        return null;
    }


    // 질문 수정
    @PatchMapping("/{questionId}")
    public ApiResponse<QuestionResponseDTO.QuestionUpdateReturnDTO> updateQuestion(
        @RequestBody QuestionRequestDTO.QuestionUpdateDTO request,
        @PathVariable Long questionId)
    {
        return null;
    }

}
