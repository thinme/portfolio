package com.example.portfolio.service.account;

import com.example.portfolio.entity.account.Member;
import com.example.portfolio.entity.customDto.CustomDtoExample;

import java.util.List;

public interface MemberService {

    List<Member> getMemberList();

    void insertMember(Member member);

    Member getMember(Member member);

    void updateMember(Member member);

    void deleteMember(Member member);

    List<Member> getMemberListAndBoardListByMemberId(String meberId);

    CustomDtoExample getCustomDtoBMemberId(String memberId);

    Member getMemberWhereIdOrEmail(String Email, String Id);

    List<Member> getMemberListEncodingByMemberList(List<Member> memberlist);
}
