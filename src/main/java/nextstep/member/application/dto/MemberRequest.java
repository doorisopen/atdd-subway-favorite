package nextstep.member.application.dto;

import nextstep.member.domain.Member;

public class MemberRequest {
    private String email;
    private String password;
    private Integer age;

    public Member toMember() {
        return new Member(email, password, age);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return age;
    }
}
