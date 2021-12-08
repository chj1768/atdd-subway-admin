package nextstep.subway.line;

import static nextstep.subway.line.LineTestUtil.*;
import static nextstep.subway.line.SectionTestUtil.*;
import static nextstep.subway.station.StationTestUtil.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.AcceptanceTest;
import nextstep.subway.line.dto.SectionRequest;

@DisplayName("지하철 구간 관련 기능")
public class SectionAcceptanceTest extends AcceptanceTest {

    @DisplayName("지하철 구간의 상행종점 삭제 요청")
    @Test
    void deleteEndUpStation() {
        // given
        // 지하철_노선_등록_되어_있음
        Long 상행종점_생성_아이디 = 지하철역_생성_및_아이디_반환("상행종점");
        Long 중간역_생성_아이디 = 지하철역_생성_및_아이디_반환("중간역");
        Long 노선_생성_아이디 =
            지하철_노선_생성_및_아이디_반환("신분당선", "red", 상행종점_생성_아이디, 중간역_생성_아이디, 10);
        Long 하행종점_생성_아이디 = 지하철역_생성_및_아이디_반환("하행종점");
        SectionRequest 구간_요청_정보 = 구간_요청_등록_정보(중간역_생성_아이디, 하행종점_생성_아이디, 5);
        노선_구간_등록_요청(노선_생성_아이디, 구간_요청_정보);

        // when
        // 역_사이에_새로운_역_등록_요청
        ExtractableResponse<Response> 노선_구간_삭제_응답 = 노선_구간_삭제_요청(노선_생성_아이디, 상행종점_생성_아이디);

        // then
        지하철_노선에_포함된_지하철역_삭제됨(노선_구간_삭제_응답);
        ExtractableResponse<Response> 노선_조회_응답 = 지하철_노선_조회_요청(노선_생성_아이디);
        지하철_노선에_삭제된_지하철역이_포함되지_않음(노선_조회_응답, 상행종점_생성_아이디);
    }

    @DisplayName("지하철 구간의 하행종점 삭제 요청")
    @Test
    void deleteEndDownStation() {
        // given
        // 지하철_노선_등록_되어_있음
        Long 상행종점_생성_아이디 = 지하철역_생성_및_아이디_반환("상행종점");
        Long 중간역_생성_아이디 = 지하철역_생성_및_아이디_반환("중간역");
        Long 노선_생성_아이디 =
            지하철_노선_생성_및_아이디_반환("신분당선", "red", 상행종점_생성_아이디, 중간역_생성_아이디, 10);
        Long 하행종점_생성_아이디 = 지하철역_생성_및_아이디_반환("하행종점");
        SectionRequest 구간_요청_정보 = 구간_요청_등록_정보(중간역_생성_아이디, 하행종점_생성_아이디, 5);
        노선_구간_등록_요청(노선_생성_아이디, 구간_요청_정보);

        // when
        // 역_사이에_새로운_역_등록_요청
        ExtractableResponse<Response> 노선_구간_삭제_응답 = 노선_구간_삭제_요청(노선_생성_아이디, 하행종점_생성_아이디);

        // then
        지하철_노선에_포함된_지하철역_삭제됨(노선_구간_삭제_응답);
        ExtractableResponse<Response> 노선_조회_응답 = 지하철_노선_조회_요청(노선_생성_아이디);
        지하철_노선에_삭제된_지하철역이_포함되지_않음(노선_조회_응답, 하행종점_생성_아이디);
    }

    @DisplayName("지하철 구간 사이의 역 삭제 요청")
    @Test
    void deleteBetweenStation() {
        // given
        // 지하철_노선_등록_되어_있음
        Long 상행종점_생성_아이디 = 지하철역_생성_및_아이디_반환("상행종점");
        Long 중간역_생성_아이디 = 지하철역_생성_및_아이디_반환("중간역");
        Long 노선_생성_아이디 =
            지하철_노선_생성_및_아이디_반환("신분당선", "red", 상행종점_생성_아이디, 중간역_생성_아이디, 10);
        Long 하행종점_생성_아이디 = 지하철역_생성_및_아이디_반환("하행종점");
        SectionRequest 구간_요청_정보 = 구간_요청_등록_정보(중간역_생성_아이디, 하행종점_생성_아이디, 5);
        노선_구간_등록_요청(노선_생성_아이디, 구간_요청_정보);

        // when
        // 역_사이에_새로운_역_등록_요청
        ExtractableResponse<Response> 노선_구간_삭제_응답 = 노선_구간_삭제_요청(노선_생성_아이디, 중간역_생성_아이디);

        // then
        지하철_노선에_포함된_지하철역_삭제됨(노선_구간_삭제_응답);
        ExtractableResponse<Response> 노선_조회_응답 = 지하철_노선_조회_요청(노선_생성_아이디);
        지하철_노선에_삭제된_지하철역이_포함되지_않음(노선_조회_응답, 중간역_생성_아이디);

    }

    @DisplayName("노선에 등록되어 있지 않은 역을 제거할 경우")
    @Test
    void invalidDeleteNotFoundStation() {
        // given
        // 지하철_노선_등록_되어_있음
        Long 상행종점_생성_아이디 = 지하철역_생성_및_아이디_반환("상행종점");
        Long 중간역_생성_아이디 = 지하철역_생성_및_아이디_반환("중간역");
        Long 노선_생성_아이디 =
            지하철_노선_생성_및_아이디_반환("신분당선", "red", 상행종점_생성_아이디, 중간역_생성_아이디, 10);
        Long 하행종점_생성_아이디 = 지하철역_생성_및_아이디_반환("하행종점");
        SectionRequest 구간_요청_정보 = 구간_요청_등록_정보(중간역_생성_아이디, 하행종점_생성_아이디, 5);
        노선_구간_등록_요청(노선_생성_아이디, 구간_요청_정보);
        Long 노선에_없는_역_생성_아이디 = 지하철역_생성_및_아이디_반환("포함되지않은역");

        // when
        // 역_사이에_새로운_역_등록_요청
        ExtractableResponse<Response> 노선_구간_삭제_응답 = 노선_구간_삭제_요청(노선_생성_아이디, 노선에_없는_역_생성_아이디);

        // then
        지하철_노선에_포함되지_않은_지하철역_삭제_실패됨(노선_구간_삭제_응답);
    }

    @DisplayName("노선의 구간이 하나인 노선에서 마지막 구간을 제거할 경우")
    @Test
    void invalidDeleteLastSection() {
        // given
        // 지하철_노선_등록_되어_있음
        Long 상행종점_생성_아이디 = 지하철역_생성_및_아이디_반환("상행역");
        Long 하행종점_생성_아이디 = 지하철역_생성_및_아이디_반환("하행역");
        Long 노선_생성_아이디 =
            지하철_노선_생성_및_아이디_반환("신분당선", "red", 상행종점_생성_아이디, 하행종점_생성_아이디, 10);

        // when
        // 역_사이에_새로운_역_등록_요청
        ExtractableResponse<Response> 노선_구간_삭제_응답 = 노선_구간_삭제_요청(노선_생성_아이디, 상행종점_생성_아이디);

        // then
        지하철_노선의_마지막_남은_구간_삭제_실패됨(노선_구간_삭제_응답);
    }

    @DisplayName("역 사이에 새로운 역 등록 요청")
    @Test
    void addBetweenStations() {
        // given
        // 지하철_노선_등록_되어_있음
        Long 상행역_생성_아이디 = 지하철역_생성_및_아이디_반환("상행역");
        Long 하행역_생성_아이디 = 지하철역_생성_및_아이디_반환("하행역");
        Long 노선_생성_아이디 =
            지하철_노선_생성_및_아이디_반환("신분당선", "red", 상행역_생성_아이디, 하행역_생성_아이디, 10);
        Long 새로운역_생성_아이디 = 지하철역_생성_및_아이디_반환("새로운역");
        SectionRequest 구간_요청_정보 = 구간_요청_등록_정보(상행역_생성_아이디, 새로운역_생성_아이디, 5);

        // when
        // 역_사이에_새로운_역_등록_요청
        ExtractableResponse<Response> 노선_구간_등록_응답 = 노선_구간_등록_요청(노선_생성_아이디, 구간_요청_정보);

        // then
        지하철_노선에_지하철역_등록됨(노선_구간_등록_응답);

        ExtractableResponse<Response> 노선_조회_응답 = 지하철_노선_조회_요청(노선_생성_아이디);
        역_사이에_새로운_역_등록됨(노선_조회_응답, 새로운역_생성_아이디);
    }

    @DisplayName("새로운 역을 상행 종점으로 등록 요청")
    @Test
    void addEndUpStation() {
        // given
        // 지하철_노선_등록_되어_있음
        Long 상행역_생성_아이디 = 지하철역_생성_및_아이디_반환("상행역");
        Long 하행역_생성_아이디 = 지하철역_생성_및_아이디_반환("하행역");
        Long 노선_생성_아이디 =
            지하철_노선_생성_및_아이디_반환("신분당선", "red", 상행역_생성_아이디, 하행역_생성_아이디, 10);
        Long 새로운역_생성_아이디 = 지하철역_생성_및_아이디_반환("새로운역");
        SectionRequest 상행_종점_구간_등록_정보 = 구간_요청_등록_정보(새로운역_생성_아이디, 상행역_생성_아이디, 5);

        // when
        // 새로운역_상행_종점으로_등록_요청
        ExtractableResponse<Response> 노선_구간_등록_응답 = 노선_구간_등록_요청(노선_생성_아이디, 상행_종점_구간_등록_정보);

        // then
        지하철_노선에_지하철역_등록됨(노선_구간_등록_응답);

        ExtractableResponse<Response> 노선_조회_응답 = 지하철_노선_조회_요청(노선_생성_아이디);
        새로운역_상행_종점으로_등록됨(노선_조회_응답, 새로운역_생성_아이디);
    }

    @DisplayName("새로운 역을 하행 종점으로 등록 요청")
    @Test
    void addEndDownStation() {
        // given
        // 지하철_노선_등록_되어_있음
        Long 상행역_생성_아이디 = 지하철역_생성_및_아이디_반환("상행역");
        Long 하행역_생성_아이디 = 지하철역_생성_및_아이디_반환("하행역");
        Long 노선_생성_아이디 =
            지하철_노선_생성_및_아이디_반환("신분당선", "red", 상행역_생성_아이디, 하행역_생성_아이디, 10);
        Long 새로운역_생성_아이디 = 지하철역_생성_및_아이디_반환("새로운역");
        SectionRequest 하행_종점_구간_등록_정보 = 구간_요청_등록_정보(하행역_생성_아이디, 새로운역_생성_아이디, 5);

        // when
        // 지하철_노선에_지하철_구간_등록_요청
        ExtractableResponse<Response> 노선_구간_등록_응답 = 노선_구간_등록_요청(노선_생성_아이디, 하행_종점_구간_등록_정보);

        // then
        지하철_노선에_지하철역_등록됨(노선_구간_등록_응답);

        ExtractableResponse<Response> 노선_조회_응답 = 지하철_노선_조회_요청(노선_생성_아이디);
        새로운역_하행_종점으로_등록됨(노선_조회_응답, 새로운역_생성_아이디);

    }

    @DisplayName("역 사이에 새로운 역을 등록할 경우 기존 역 사이 길이보다 크면 등록 할 수 없음")
    @Test
    void invalidAddFatherThanDistance() {
        // given
        // 지하철_노선_등록_되어_있음
        Long 상행역_생성_아이디 = 지하철역_생성_및_아이디_반환("상행역");
        Long 하행역_생성_아이디 = 지하철역_생성_및_아이디_반환("하행역");
        Long 노선_생성_아이디 =
            지하철_노선_생성_및_아이디_반환("신분당선", "red", 상행역_생성_아이디, 하행역_생성_아이디, 10);
        Long 새로운역_생성_아이디 = 지하철역_생성_및_아이디_반환("새로운역");
        SectionRequest 기존_역_사이_거리보다_큰_구간_등록_정보 =
            구간_요청_등록_정보(상행역_생성_아이디, 새로운역_생성_아이디, 15);

        // when
        // 기존_역_사이_거리보다_클_경우
        ExtractableResponse<Response> 노선_구간_등록_응답 = 노선_구간_등록_요청(노선_생성_아이디, 기존_역_사이_거리보다_큰_구간_등록_정보);

        // then
        지하철_노선_등록_실패됨(노선_구간_등록_응답);
    }

    @DisplayName("역 사이에 새로운 역을 등록할 경우 기존 역 사이 길이와 같을 경우 등록 할 수 없음")
    @Test
    void invalidAddSameDistance() {
        // given
        // 지하철_노선_등록_되어_있음
        Long 상행역_생성_아이디 = 지하철역_생성_및_아이디_반환("상행역");
        Long 하행역_생성_아이디 = 지하철역_생성_및_아이디_반환("하행역");
        Long 노선_생성_아이디 =
            지하철_노선_생성_및_아이디_반환("신분당선", "red", 상행역_생성_아이디, 하행역_생성_아이디, 10);
        Long 새로운역_생성_아이디 = 지하철역_생성_및_아이디_반환("새로운역");
        SectionRequest 기존_역_사이_거리와_같은_구간_등록_정보 = 구간_요청_등록_정보(상행역_생성_아이디, 새로운역_생성_아이디, 10);

        // when
        // 기존역_사이_거리와_같은_경우
        ExtractableResponse<Response> 노선_구간_등록_응답 = 노선_구간_등록_요청(노선_생성_아이디, 기존_역_사이_거리와_같은_구간_등록_정보);

        // then
        지하철_노선_등록_실패됨(노선_구간_등록_응답);

    }

    @DisplayName("상행역과 하행역이 이미 노선에 모두 등록되어 있다면 추가할 수 없음")
    @Test
    void invalidAddContainsStation() {
        // given
        // 지하철_노선_등록_되어_있음
        Long 상행역_생성_아이디 = 지하철역_생성_및_아이디_반환("상행역");
        Long 하행역_생성_아이디 = 지하철역_생성_및_아이디_반환("하행역");
        Long 노선_생성_아이디 =
            지하철_노선_생성_및_아이디_반환("신분당선", "red", 상행역_생성_아이디, 하행역_생성_아이디, 10);
        SectionRequest 이미_등록되어_있는_구간_정보 = 구간_요청_등록_정보(상행역_생성_아이디, 하행역_생성_아이디, 10);

        // when
        // 지하철_노선에_지하철_구간_등록_요청
        ExtractableResponse<Response> 노선_구간_등록_응답 = 노선_구간_등록_요청(노선_생성_아이디, 이미_등록되어_있는_구간_정보);

        //then
        지하철_노선_등록_실패됨(노선_구간_등록_응답);
    }

    @DisplayName("상행역과 하행역 둘 중 하나도 포함되어있지 않으면 추가할 수 없음")
    @Test
    void invalidAddNotExistsStations() {
        // given
        // 지하철_노선_등록_되어_있음
        Long 상행역_생성_아이디 = 지하철역_생성_및_아이디_반환("상행역");
        Long 하행역_생성_아이디 = 지하철역_생성_및_아이디_반환("하행역");
        Long 노선_생성_아이디 =
            지하철_노선_생성_및_아이디_반환("신분당선", "red", 상행역_생성_아이디, 하행역_생성_아이디, 10);
        Long 새로운_상행역_생성_아이디 = 지하철역_생성_및_아이디_반환("새로운역");
        Long 새로운_하행역_생성_아이디 = 지하철역_생성_및_아이디_반환("마곡역");
        SectionRequest 기존_역에_포함되지_않는_구간_정보 =
            구간_요청_등록_정보(새로운_상행역_생성_아이디, 새로운_하행역_생성_아이디, 3);

        // when
        // 상행역_하행역_모두_포함하지_않는_구간_등록_요청
        ExtractableResponse<Response> 노선_구간_등록_응답 = 노선_구간_등록_요청(노선_생성_아이디, 기존_역에_포함되지_않는_구간_정보);

        // then
        지하철_노선_등록_실패됨(노선_구간_등록_응답);
    }
}
