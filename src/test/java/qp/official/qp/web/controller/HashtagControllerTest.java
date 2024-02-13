package qp.official.qp.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.repository.HashtagRepository;
import qp.official.qp.service.HashtagService.HashtagCommandServiceImpl;
import qp.official.qp.service.HashtagService.HashtagQueryService;
import qp.official.qp.service.HashtagService.HashtagQueryServiceImpl;
import qp.official.qp.web.dto.HashtagRequestDTO;
import qp.official.qp.web.dto.HashtagRequestDTO.HashtagDTO;
import qp.official.qp.web.dto.HashtagResponseDTO;
import qp.official.qp.web.dto.HashtagResponseDTO.HashtagReturnDTO;

@WebMvcTest(HashtagController.class)
class HashtagControllerTest {

    @MockBean
    private HashtagCommandServiceImpl hashtagCommandService;

    @MockBean
    private HashtagQueryServiceImpl hashtagQueryService;

    @MockBean
    private HashtagRepository hashtagRepository;

    // MockMvc를 사용하여 API를 테스트
    private final MockMvc mockMvc;

    // ObjectMapper를 사용하여 객체를 JSON으로 변환 또는 JSON을 객체로 변환
    private final ObjectMapper objectMapper;

    @Autowired
    public HashtagControllerTest(
        MockMvc mockMvc,
        ObjectMapper objectMapper
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void generate() throws Exception {
        // given
        Long hashtagId = 1L;
        String hashtag = "test";

        HashtagRequestDTO.HashtagDTO request = HashtagRequestDTO.HashtagDTO.builder()
            .hashtag(hashtag)
            .build();

        Hashtag hashtagResponse = Hashtag.builder()
            .hashtagId(hashtagId)
            .hashtag(hashtag)
            .questionHashTagList(new ArrayList<>())
            .build();

        // hashtagQueryService.saveHashtag
        when(hashtagQueryService.saveHashtag(any(HashtagDTO.class))).thenReturn(hashtagResponse);
        // ExistHashTag 어노테이션 통과 위한 설정
        when(hashtagRepository.findById(any(Long.class))).thenReturn(Optional.of(Hashtag.builder()
            .build()));

        // when
        String body = objectMapper.writeValueAsString(request);
        // API 호출
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post("/hashtag/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body));

        // then
        // API 호출 결과가 200 OK 확인
        action.andExpect(status().isOk());

        // API 호출 결과를 ApiResponse 객체 변환
        ApiResponse<HashtagResponseDTO.HashtagReturnDTO> response = objectMapper.readValue(
            action.andReturn().getResponse().getContentAsString(),
            new TypeReference<>() {
            }
        );

        // 검증
        assertEquals(hashtag, response.getResult().getHashtag());
        assertEquals(hashtagId, response.getResult().getHashtagId());
    }

    @Test
    void findHashtag() throws Exception {
        // given
        Long hashtagId = 1L;
        String hashtag = "test";

        HashtagRequestDTO.HashtagDTO request = HashtagRequestDTO.HashtagDTO.builder()
            .hashtag(hashtag)
            .build();

        Hashtag hashtagResponse = Hashtag.builder()
            .hashtagId(hashtagId)
            .hashtag(hashtag)
            .questionHashTagList(new ArrayList<>())
            .build();

        // hashtagCommandService.findHashtag
        when(hashtagCommandService.findHashtag(any(HashtagDTO.class))).thenReturn(hashtagResponse);
        // ExistHashTag 어노테이션 통과 위한 설정
        when(hashtagRepository.findById(any(Long.class))).thenReturn(Optional.of(Hashtag.builder()
            .build()));

        // when
        String body = objectMapper.writeValueAsString(request);

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/hashtag/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body));

        // then
        // API 호출 결과가 200 OK 확인
        action.andExpect(status().isOk());

        // API 호출 결과를 ApiResponse 객체 변환
        ApiResponse<HashtagResponseDTO.HashtagReturnDTO> response = objectMapper.readValue(
            action.andReturn().getResponse().getContentAsString(),
            new TypeReference<>() {
            }
        );

        // 검증
        assertEquals(hashtagId, response.getResult().getHashtagId());
        assertEquals(hashtag, response.getResult().getHashtag());
    }

    @Test
    void deleteHashtag() throws Exception {
        // given
        Long hashtagId = 1L;
        String hashtag = "test";

        Hashtag hashtagResponse = Hashtag.builder()
            .hashtagId(hashtagId)
            .hashtag(hashtag)
            .questionHashTagList(new ArrayList<>())
            .build();

        when(hashtagQueryService.deleteHashtag(any(Long.class))).thenReturn(hashtagResponse);

        // when
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.delete("/hashtag/{hashtagId}", hashtagId));

        // then
        action.andExpect(status().isOk());

        ApiResponse<HashtagResponseDTO.HashtagReturnDTO> response = objectMapper.readValue(
            action.andReturn().getResponse().getContentAsString(),
            new TypeReference<>(){

            }
        );
        verify(hashtagQueryService, times(1)).deleteHashtag(hashtagId);

        assertEquals(hashtagId, response.getResult().getHashtagId());
        assertEquals(hashtag, response.getResult().getHashtag());
    }

}