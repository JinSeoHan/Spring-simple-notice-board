package com.kw4.controller;

import com.kw4.dto.BoardDto;
import com.kw4.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // The method of the class specified as controller in mvc must either create a template path with a return value or redirect it
@AllArgsConstructor // Automatically create generators for all fields that exist in the class
public class BoardController {
    private BoardService boardService;

    @GetMapping("/post")
    public String write() {
        return "board/write.html";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        if(boardDto.getTitle().length() == 0 || boardDto.getContent().length() == 0) return "redirect:/";
        boardService.savePost(boardDto);
        return "redirect:/";
    }

    //Find and move posts with id values and count views
    @GetMapping("/post/{id}")
    public String read(@PathVariable Long id, Model model) {
        boardService.countViews(id);
        BoardDto dto = boardService.findbyId(id);
        model.addAttribute("board", dto);
        return "board/detail.html";
    }

    //Returns the update page
    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        BoardDto boardDto = boardService.findbyId(id);

        model.addAttribute("board", boardDto);
        return "board/update.html";
    }
    //Put to modify the data.
    @PutMapping("/post/edit/{id}")
    public String update(@PathVariable Long id, BoardDto boardDto, Model model) {
        int views = boardService.findbyId(id).getViews();
        boardDto.setViews(views);
        if(boardDto.getTitle().length() == 0 || boardDto.getContent().length() == 0){
            boardDto = boardService.findbyId(id);
            model.addAttribute("board", boardDto);
            return "board/update.html";
        }
        boardService.savePost(boardDto);

        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deletePost(id);

        return "redirect:/";
    }

    //Return all board information
   @GetMapping("/")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1")Integer pageNum) {
        List<BoardDto> boardDtoList = boardService.getBoardList(pageNum);

        Integer[] pageList = boardService.getPageList(pageNum);



       //Map boardDtoList to ${boardList} in board/list.html
       model.addAttribute("boardList", boardDtoList);
       model.addAttribute("pageList", pageList);
       model.addAttribute("currPage", pageNum);
       return "board/list.html";
    }
}
