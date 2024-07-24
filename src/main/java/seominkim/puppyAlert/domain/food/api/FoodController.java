package seominkim.puppyAlert.domain.food.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seominkim.puppyAlert.domain.food.dto.FoodRequestDTO;
import seominkim.puppyAlert.domain.food.service.FoodService;
import seominkim.puppyAlert.global.dto.MatchHistoryResponseDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/zipbob")
public class FoodController {

    private final FoodService foodService;

    // 현재 가능한 집밥 조회
    @GetMapping("/all")
    public ResponseEntity findAll(){
        return ResponseEntity.ok(foodService.findAll());
    }

    // 특정 집밥 조회
    @GetMapping("/{zipbobId}")
    public ResponseEntity findOne(@PathVariable long zipbobId){
        return ResponseEntity.ok(foodService.findById(zipbobId));
    }

    // Host 의 집밥 등록
    @PostMapping()
    public ResponseEntity add(@RequestBody FoodRequestDTO foodRequestDTO){
        Long zipbobId = foodService.add(foodRequestDTO);
        return ResponseEntity.ok(zipbobId);
    }

    // Host 집밥 기록 조회
    @GetMapping("/hostHistory")
    public ResponseEntity getHostHistory(@RequestBody String hostId){
        return ResponseEntity.ok(foodService.findHostHistory(hostId));
    }

    // Puppy 집밥 기록 조회
    @GetMapping("/puppyHistory")
    public ResponseEntity<List<MatchHistoryResponseDTO>> getPuppyHistory(@RequestBody String puppyId){
        return ResponseEntity.ok(foodService.findPuppyHistory(puppyId));
    }
}