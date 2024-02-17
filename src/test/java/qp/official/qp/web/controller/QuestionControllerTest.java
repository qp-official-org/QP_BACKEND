package qp.official.qp.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.ChildStatus;
import qp.official.qp.repository.HashtagRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.service.QuestionService.QuestionCommandService;
import qp.official.qp.service.QuestionService.QuestionQueryService;
import qp.official.qp.service.TokenService.TokenService;
import qp.official.qp.web.dto.QuestionRequestDTO;
import qp.official.qp.web.dto.QuestionResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// WebMvcTest를 사용하여 QuestionController를 테스트
// WebMvcTest는 Controller가 의존하는 Bean들만 로드하여 테스트
@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @MockBean
    private QuestionCommandService questionCommandService;
    @MockBean
    private QuestionQueryService questionQueryService;

    // ExistHashTag 어노테이션에서 사용하는 HashtagRepository
    @MockBean
    private HashtagRepository hashtagRepository;

    // ExistUser 어노테이션에서 사용하는 UserRepository
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    // MockMvc를 사용하여 API를 테스트
    private final MockMvc mockMvc;

    // ObjectMapper를 사용하여 객체를 JSON으로 변환 또는 JSON을 객체로 변환
    private final ObjectMapper objectMapper;

    @Autowired
    public QuestionControllerTest(
            MockMvc mockMvc,
            ObjectMapper objectMapper
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void createQuestion() throws Exception {
        // given
        String testTitle = "new title";
        String testContent = "new content";
        ChildStatus testChildStatus = ChildStatus.ACTIVE;

        Long userId = 1L;
        Long questionId = 1L;
        LocalDateTime createdAt = LocalDateTime.now();

        // Request 객체 생성
        QuestionRequestDTO.CreateDTO questionRequest = QuestionRequestDTO.CreateDTO.builder()
                .userId(userId)
                .title(testTitle)
                .content(testContent)
                .childStatus(testChildStatus)
                .hashtag(new ArrayList<>())
                .build();


        // Expect Response 객체 생성
        Question questionResponse = Question.builder()
                .questionId(questionId)
                .title(testTitle)
                .content(testContent)
                .childStatus(testChildStatus)
                .answers(new ArrayList<>())
                .build();

        // Setter 없이 ReflectionTestUtils를 사용하여 필드값을 설정
        ReflectionTestUtils.setField(questionResponse, "createdAt", createdAt);

        // questionCommandService 에서 createQuestion 메소드가 호출될 때 questionResponse를 반환하도록 설정
        when(questionCommandService.createQuestion(any(QuestionRequestDTO.CreateDTO.class))).thenReturn(questionResponse);
        // ExistUser 어노테이션을 통과하기 위해 userRepository 에서 findById 메소드가 호출될 때 아무 Optional.of(User.builder().build())를 반환하도록 설정
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(User.builder().build()));
        // ExistHashTag 어노테이션을 통과하기 위해 hashtagRepository 에서 findById 메소드가 호출될 때 아무 Optional.of(Hashtag.builder().build())를 반환하도록 설정
        when(hashtagRepository.findById(any(Long.class))).thenReturn(Optional.of(Hashtag.builder().build()));

        // when
        // Request 객체를 JSON으로(request body로) 변환
        String body = objectMapper.writeValueAsString(questionRequest);
        // API 호출
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                .contentType("application/json")
                .content(body));

        // then
        // API 호출 결과가 200 OK인지 확인
        action.andExpect(status().isOk());

        // API 호출 결과를 ApiResponse 객체로 변환
        ApiResponse<QuestionResponseDTO.CreateResultDTO> response = objectMapper.readValue(
                action.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        // questionId 확인
        assertEquals(questionId, response.getResult().getQuestionId());

        // createAt 확인
        assertEquals(createdAt, response.getResult().getCreatedAt());
    }
}