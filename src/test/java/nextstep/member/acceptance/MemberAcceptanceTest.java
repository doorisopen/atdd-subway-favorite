package nextstep.member.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static nextstep.member.acceptance.MemberSteps.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberAcceptanceTest extends AcceptanceTest {
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final int AGE = 20;

    @DisplayName("회원가입을 한다.")
    @Test
    void createMember() {
        // when
        ExtractableResponse<Response> response = 회원_생성_요청(EMAIL, PASSWORD, AGE);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("회원 정보를 조회한다.")
    @Test
    void getMember() {
        // given
        ExtractableResponse<Response> createResponse = 회원_생성_요청(EMAIL, PASSWORD, AGE);

        // when
        ExtractableResponse<Response> response = 회원_정보_조회_요청(createResponse);

        // then
        회원_정보_조회됨(response, EMAIL, AGE);

    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void updateMember() {
        // given
        ExtractableResponse<Response> createResponse = 회원_생성_요청(EMAIL, PASSWORD, AGE);

        // when
        ExtractableResponse<Response> response = 회원_정보_수정_요청(createResponse, "new" + EMAIL, "new" + PASSWORD, AGE);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("회원 정보를 삭제한다.")
    @Test
    void deleteMember() {
        // given
        ExtractableResponse<Response> createResponse = 회원_생성_요청(EMAIL, PASSWORD, AGE);

        // when
        ExtractableResponse<Response> response = 회원_삭제_요청(createResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    /**
     * Given 회원 가입을 하고
     * Given 베어러 인증 로그인 요청 해서 토큰을 발급 받은 후
     * When 내 정보를 조회하면
     * Then 내 정보를 조회할 수 있다.
     */
    @DisplayName("내 정보를 조회한다.")
    @Test
    void getMyInfo() {
        // given
        ExtractableResponse<Response> createResponse = 회원_생성_요청(EMAIL, PASSWORD, AGE);
        ExtractableResponse<Response> loginResponse = 베어러_인증_로그인_요청(EMAIL, PASSWORD);
        String token = loginResponse.jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> findResponse = OAUTH_인증으로_내_회원_정보_조회_요청(token);

        // then
        assertThat(findResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(findResponse.jsonPath().getString("email")).isEqualTo(EMAIL);
    }
}
