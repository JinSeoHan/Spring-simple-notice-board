package com.kw4.service;


import com.kw4.dto.BoardDto;
import com.kw4.entity.BoardEntity;
import com.kw4.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor // Automatically create generators for all fields that exist in the class
@Service // Specify Service Tier
public class BoardService {
    private static final int BLOCK_PAGE_NUM_COUNT = 5;//Number of page numbers present in the block
    private static final int PAGE_POST_COUNT = 8; // Number of posts that exist on a page
    private BoardRepository boardRepository;

    @Transactional // Apply Transaction
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    //views + 1
    @Transactional
    public int countViews(Long id) {
        return boardRepository.countView(id);
    }

    //Return board information with id in boardDto
    public BoardDto findbyId(Long id){
        BoardEntity boardEntity = boardRepository.getById(id);
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .views(boardEntity.getViews())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }

    //Return total post list by putting it in Dto list
    public List<BoardDto> getBoardList(Integer pageNum) {
        Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate")));

        List<BoardEntity> boardEntities = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntities){
            boardDtoList.add(this.convertEntityToDto(boardEntity));
        }
        return boardDtoList;
    }

    public BoardDto convertEntityToDto(BoardEntity boardEntity) {
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .views(boardEntity.getViews())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }

    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
        //total post count
        Double postsTotalCount = Double.valueOf(this.getBoardCount());
        //Calculating the last page number calculated based on the total post (Round calculation)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));
        //Adjust the page start number
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum -2;
        //Calculating the last page number of the block based on the current page
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT-1)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT-1
                : totalLastPageNum;
        //Assign Page Numbers
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++,idx++) {
            pageList[idx] = val;
        }
        return pageList;
    }

}
