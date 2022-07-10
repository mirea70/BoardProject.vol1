package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.PageHandler;
import com.fastcampus.ch4.domain.SearchCondition;
import com.fastcampus.ch4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, Model m ,HttpSession session, RedirectAttributes rattr) {
        String writer = (String)session.getAttribute("id"); // getAttribute는 Object 타입 반환하여 형변환 필요
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.modify(boardDto);   // insert

            if(rowCnt!=1)
                throw new Exception("Modify failed");

            rattr.addFlashAttribute("msg","MOD_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            m.addAttribute("msg","MOD_ERR");
            return "board3";
        }
    }

    @PostMapping("/write")
    public String write(BoardDto boardDto, Model m ,HttpSession session, RedirectAttributes rattr) {
        String writer = (String)session.getAttribute("id"); // getAttribute는 Object 타입 반환하여 형변환 필요
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.write(boardDto);   // insert

            if(rowCnt!=1)
                throw new Exception("Write failed");

            rattr.addFlashAttribute("msg","WRT_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            m.addAttribute("msg","WRT_ERR");
            return "board3";
        }
    }

    @GetMapping("/write")
    public String write(Model m) {
        m.addAttribute("mode", "new");  //읽기와 쓰기에 사용. 쓰기에 사용할 때는 mode=new
        return "board3";
    }


    @PostMapping("remove")
    public String remove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String)session.getAttribute("id");
        try {
            m.addAttribute("page", page);
            m.addAttribute("pageSize",pageSize);

            int rowCnt = boardService.remove(bno, writer);

            if(rowCnt != 1){
                throw new Exception("board remove error");
            }
            rattr.addFlashAttribute("msg", "DEL_OK"); // rattr.addFlashAttribute : 단 한번만 메시지 넘겨줌!
                       // jsp에서 받을때 param 사용금지! // --> 에러 한번만 발생하게 가능! (세션을 한번쓰고 지워버림) --> 세션에도 부담이 덜감!

        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg","DEL_ERR");
        }

        return "redirect:/board/list"; // ?page=${page}&pageSize=${pageSize}  <- 모델에 넣으면 자동으로 이렇게 바뀜
    }

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model m) {
        try {
            BoardDto boardDto = boardService.read(bno);
//            m.addAttribute("boardDto", boardDto); // 아래 문장과 동일
            m.addAttribute(boardDto);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "board3";
    }

    @GetMapping("/list")

        public String list(SearchCondition sc, Model m, HttpServletRequest request) {
            if(!loginCheck(request))
                return "redirect:/login/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

//            if(page==null) page=1;
//            if(pageSize==null) pageSize=10;

            try {
                int totalCnt = boardService.getSearchResultCnt(sc);
                PageHandler pageHandler = new PageHandler(totalCnt, sc);

//                Map map = new HashMap();
//                map.put("offset", (page-1)*pageSize);
//                map.put("pageSize", pageSize);

                List<BoardDto> list = boardService.getSearchResultPage(sc);
                m.addAttribute("list", list);
                m.addAttribute("ph", pageHandler);

            } catch (Exception e) {
                e.printStackTrace();
                m.addAttribute("msg","List_ERR");
                m.addAttribute("totalCnt", 0);
            }

            return "boardList3"; // 로그인을 한 상태이면, 게시판 화면으로 이동
        }

        private boolean loginCheck(HttpServletRequest request) {
            // 1. 세션을 얻어서
            HttpSession session = request.getSession();
            // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
            return session.getAttribute("id")!=null;
        }
    }