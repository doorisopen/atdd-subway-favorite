package nextstep.member.application;

import nextstep.member.application.dto.MemberRequest;
import nextstep.member.application.dto.MemberResponse;
import nextstep.member.domain.Member;
import nextstep.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse createMember(MemberRequest request) {
        Member member = memberRepository.save(request.toMember());
        return MemberResponse.of(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse findMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return MemberResponse.of(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse findMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
        return MemberResponse.of(member);
    }

    public void updateMember(Long id, MemberRequest param) {
        Member member = memberRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        member.update(param.toMember());
    }

    public void updateMember(String email, MemberRequest param) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
        member.update(param.toMember());
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public void deleteMember(String email) {
        memberRepository.deleteByEmail(email);
    }
}