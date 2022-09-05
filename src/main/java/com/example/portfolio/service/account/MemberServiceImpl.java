package com.example.portfolio.service.account;

import com.example.portfolio.encrypt.EncryptAES256;
import com.example.portfolio.entity.account.Member;
import com.example.portfolio.entity.customDto.CustomDtoExample;
import com.example.portfolio.repository.account.MemberRepository;
import com.example.portfolio.repository.customRepository.CustomDtoExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepo;
    private final EncryptAES256 encryptAES256;
    private final CustomDtoExampleRepository customDtoExampleRepository;

    @Autowired
    protected MemberServiceImpl(MemberRepository memberRepo, CustomDtoExampleRepository customDtoExampleRepository, EncryptAES256 encryptAES256){
        this.memberRepo = memberRepo;
        this.customDtoExampleRepository = customDtoExampleRepository;
        this.encryptAES256 = encryptAES256;
    }
    @Override
    public List<Member> getMemberList() {
        return (List<Member>) memberRepo.findAll();
    }

    @Override
    public void insertMember(Member member) {
        memberRepo.save(member);
    }

    @Override
    public Member getMember(Member member) {
        return memberRepo.findById(member.getSeq()).get();
    }

    @Override
    public void updateMember(Member member) {
        Member findMember = memberRepo.findById(member.getSeq()).get();
        findMember.setId(member.getId());
        findMember.setEmail(member.getEmail());
        memberRepo.save(findMember);
    }

    @Override
    public void deleteMember(Member member) {
        memberRepo.deleteById(member.getSeq());
    }

    @Override
    public List<Member> getMemberListAndBoardListByMemberId(String memberId) {
        return memberRepo.findAllByMemberIdEqualsBoardWriter(memberId);
    }

    @Override
    public CustomDtoExample getCustomDtoBMemberId(String memberId) {
        return customDtoExampleRepository.findExample(memberId);
    }

    @Override
    public Member getMemberWhereIdOrEmail(String Email, String Id) {
        return memberRepo.findMemberByEmailOrId(Email, Id);
    }

    @Override
    public List<Member> getMemberListEncodingByMemberList(List<Member> memberList) {
        for(Member member : memberList) {
            try {
                member.setPassword(encryptAES256.encrypt(member.getPassword()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return memberList;

    }
}
