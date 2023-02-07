package B107.server.meerkat.controller;

import B107.server.meerkat.config.security.auth.PrincipalDetails;
import B107.server.meerkat.config.utils.Msg;
import B107.server.meerkat.config.utils.ResponseDTO;
import B107.server.meerkat.dto.marker.MarkerDTO;
import B107.server.meerkat.entity.Marker;
import B107.server.meerkat.service.MarkerCheckService;
import B107.server.meerkat.service.MarkerService;
import antlr.MakeGrammar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/marker")
@RequiredArgsConstructor
public class MarkerController {

	private final MarkerService markerService;
	private final MarkerCheckService markerCheckService;


	@PostMapping("/regist")
	public ResponseEntity<ResponseDTO> registMarker(@AuthenticationPrincipal PrincipalDetails principalDetails,
													@RequestBody Marker marker) {
	
		// 토큰으로 사용자 idx 뽑아오기
		Long memberIdx = principalDetails.getMember().getIdx();

		// 첫 가입하고 나서 markerCheck의 member_idx 초기화어케 해주지???
		if(!markerCheckService.isMarkerCheck(memberIdx)) {
			// 등록 가능한 경우
			markerService.registMarker(memberIdx, marker);
			return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.CREATED, Msg.SUCCESS_MARKER_REGISTER));
		}

		// 이미 등록 내역이 있는 경우
		return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.BAD_REQUEST, Msg.FAIL_MARKER_REGISTER));

	}

	/**
	 * 등록 내역 조회
	 * 유효 혹은 만료된 것이 하나의 테이블에 있기 때문에 valid 명시
	 * @param principalDetails
	 * @return
	 */
	@GetMapping("/")
	public ResponseEntity<ResponseDTO> getValidMarker(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Long memberIdx = principalDetails.getMember().getIdx();
		Marker marker = markerService.getValidMarker(memberIdx);
		MarkerDTO resMarker = new MarkerDTO().of(marker);
		return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_MARKER_UPDATE, resMarker));
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDTO> updateMarker (@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody LocalDateTime expDate) {
		Long memberIdx = principalDetails.getMember().getIdx();
		Marker marker = markerService.updateMarker(memberIdx, expDate);
		if(marker == null) {
			return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.FAIL_MARKER_UPDATE));
		}

		MarkerDTO resMarker = new MarkerDTO().of(marker);
		return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_MARKER_UPDATE, resMarker));
	}


	/**
	 * 일단 맵상에 있는 마커 삭제 > 추후에 과거 등록내역 및 거래내역 마커 삭제까지
	 * 해당 사용자의 (유효한) 마커 삭제
	 * @param principalDetails
	 * @return
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDTO> deleteMarker(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Long memberIdx = principalDetails.getMember().getIdx();
		Long result = markerService.deleteMarker(memberIdx);
		if(result == -1L) {
			return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.FAIL_MARKER_DELETE));
		}
		return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_MARKER_DELETE));
	}
}