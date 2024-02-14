package com.example.ReviewZIP.domain.userStores;


import com.example.ReviewZIP.domain.userStores.dto.request.UserStoresRequestDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user-stores")
public class UserStoresController {

    private final UserStoresService userStoresService;

    @PostMapping("/")
    @Operation(summary = "특정 장소를 관심 장소로 등록 API",description = "특정 장소를 유저의 관심 장소로 등록한다, 입력 시 CreateUserStoresDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USERSTORE401", description = "유저 관심장소 저장에 실패했습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),

    })
    public ApiResponse<SuccessStatus> createUserStores(@RequestBody UserStoresRequestDto.CreateUserStoresDto dto) {
        userStoresService.createUserStores(dto);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }


    @GetMapping("/location")
    @Operation(summary = "특정 위치의 장소가 관심 장소 인지 판별하는 API",description = "특정 위치의 위도 경도 값을 바탕으로 관심 장소인지 판별하는 기능")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "lat", description = "장소의 위도"),
            @Parameter(name = "lon", description = "장소의 경도"),

    })
    public ApiResponse<Boolean> isInterestPlace(@RequestParam Double lat, @RequestParam Double lon) {
        return ApiResponse.onSuccess(userStoresService.isInterestPlace(lat,lon));
    }

    @DeleteMapping("/{storeId}")
    @Operation(summary = "기존의 관심장소를 해제하는 API",description = "관심장소의 아이디를 입력받아 관심장소를 해제하는 기능")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USERSTORE402", description = "유저 관심장소 삭제에 실패했습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),

    })
    @Parameters({
            @Parameter(name = "storeId", description = "장소의 아이디"),

    })
    public ApiResponse<SuccessStatus> deleteUserStores(@PathVariable Long storeId) {
        userStoresService.deleteUserStores(storeId);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

}
