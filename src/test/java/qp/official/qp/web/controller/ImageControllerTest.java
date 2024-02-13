package qp.official.qp.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.domain.Image;
import qp.official.qp.repository.ImageRepository;
import qp.official.qp.service.ImageService.ImageCommandServiceImpl;
import qp.official.qp.service.ImageService.ImageQueryServiceImpl;
import qp.official.qp.web.dto.ImageRequestDTO;
import qp.official.qp.web.dto.ImageResponseDTO;
import qp.official.qp.web.dto.ImageResponseDTO.ImageCreateResultDTO;

@WebMvcTest(ImageController.class)
class ImageControllerTest {

    @MockBean
    private ImageQueryServiceImpl imageQueryService;
    @MockBean
    private ImageCommandServiceImpl imageCommandService;

    @MockBean
    private ImageRepository imageRepository;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public ImageControllerTest(
        MockMvc mockMvc,
        ObjectMapper objectMapper
    ){
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("uploadImage 테스트 ")
    void uploadImage() throws Exception {
        // given
        Long imageId = 1L;
        String fileName = "test_image.png";
        String testUrl = "https://example.com/qp/test";

        // request : multipartFile 생성
        MockMultipartFile multipartFile = new MockMultipartFile("image", fileName, "png", "test data".getBytes());

        // response 객체 생성
        Image imageResponse = Image.builder()
            .imageId(imageId)
            .url(testUrl)
            .fileName(fileName)
            .build();

        // imageCommandService.saveImage
        when(imageCommandService.saveImage(multipartFile)).thenReturn(imageResponse);

        // when
        // API 호출
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.multipart("/images")
                .file(multipartFile)
        );

        // then
        // API 호출 결과 코드 확인
        action.andExpect(status().isOk());

        // API 호출 결과 변환
        ApiResponse<ImageResponseDTO.ImageCreateResultDTO> response = objectMapper.readValue(
            action.andReturn().getResponse().getContentAsString(),
            new TypeReference<>() {
            }
            );

        // 검증
        assertEquals(testUrl, response.getResult().getUrl());

    }
    @Test
    @DisplayName("deleteImage 테스트")
    void deleteImage() throws Exception {
        // given
        String testUrl = "https://example.com/qp/test";

        // request 객체 생성
        ImageRequestDTO.ImageDTO request = ImageRequestDTO.ImageDTO.builder()
            .url(testUrl)
            .build();

        // when
        String body = objectMapper.writeValueAsString(request);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.delete("/images")
            .contentType("application/json")
            .content(body));

        // then
        // API 호출 결과 코드 확인
        action.andExpect(status().isOk());

        // 검증
        verify(imageCommandService, times(1)).deleteImage(testUrl);

    }

    @Test
    @DisplayName("deleteAllImages 테스트")
    void deleteAllImages() throws Exception {
        doNothing().when(imageCommandService).deleteAllImages();

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.delete("/images/reset-S3"));

        action.andExpect(status().isOk());

        verify(imageCommandService, times(1)).deleteAllImages();
    }

}