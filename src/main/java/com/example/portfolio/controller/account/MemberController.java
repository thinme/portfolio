package com.example.portfolio.controller.account;

import com.example.portfolio.entity.account.Member;
import com.example.portfolio.entity.board.Board;
import com.example.portfolio.service.account.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path ="/account")
public class MemberController {


    private final MemberService memberService;

    @Autowired
    protected MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/getAccountList")
    public String getAccountList(Model model) {
        model.addAttribute("memberList",
                memberService.getMemberListEncodingByMemberList(
                        memberService.getMemberList()));
        return "/account/getAccountList";
    }

    @GetMapping("/getAccount")
    public String getAccount(Member member, Model model) {
        model.addAttribute("member", memberService.getMember(member));
        return "/account/getAccount";
    }

    @PostMapping("/updateAccount")
    public String updateAccount(Member member) {
        memberService.updateMember(member);
        return "redirect:/account/getAccountList";
    }

    @GetMapping("/deleteAccount")
    public String deleteAccount(Member member) {
        memberService.deleteMember(member);
        return "redirect:/account/getAccountList";
    }

    @GetMapping("/insertAccount")
    public String insertAccountView() {
        return "/account/insertAccount";
    }

    @PostMapping("/insertAccount")
    public String insertAccountView(Member member) {
        memberService.insertMember(member);
        return "redirect:/account/getAccountList";
    }

    @GetMapping("/selectAccount")
    public String selectAccount() {
        return "/account/selectAccount";
    }

    @PostMapping("/selectAccount")
    public String resultAccount(Member member, Model model) {
        model.addAttribute("member",
                memberService.getMemberWhereIdOrEmail(member.getEmail(), member.getId()));
        return "/account/resultAccount";
    }

    @GetMapping("/selectMembersBoards")
    public String selectBoard(Member member, Model model) {
        System.out.println("-----------------");
        System.out.println(member.getId());
        for (Member for_member : memberService.getMemberListAndBoardListByMemberId(member.getId())) {
            model.addAttribute("boardList", for_member);
            System.out.println("---------------1212---------------");
            for (Board board : for_member.getBoardList()) {


            }
        }
        return "/board/getBoardList";
    }
}



