package seominkim.puppyAlert.domain.food.api;

import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import seominkim.puppyAlert.domain.food.dto.response.FoodInfoResponse;
import seominkim.puppyAlert.domain.food.entity.FoodStatus;
import seominkim.puppyAlert.domain.food.service.FoodService;
import seominkim.puppyAlert.global.entity.Location;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FoodController.class)
public class FoodControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private FoodService foodService;

    @Test
    @DisplayName("집밥 단건 조회")
    public void findOne() throws Exception {
        // given
        String request = foodRequest();
        FoodInfoResponse response = foodInfoResponse();

        given(foodService.findById(1L)).willReturn(response);

        // when
        Long findId = 1L;
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/food")
                        .param("foodId","1")
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("foodId",response.foodId()).exists());
    }

    private String foodRequest() {
        return "{"
                + "\"hostId\":\"son\","
                + "\"menuName\":\"honny\","
                + "\"time\":\"2024-08-29\","
                + "\"status\":\"READY\","
                + "}";
    }

    private FoodInfoResponse foodInfoResponse(){
        return new FoodInfoResponse(
                1L,
                "son",
                "suppersonny",
                false,
                "칼국수",
                "www.123.com",
                LocalDateTime.now(),
                "tottenham",
                "hotspur",
                new Location(37.7749, -122.4194),
                FoodStatus.READY
        );
    }
}