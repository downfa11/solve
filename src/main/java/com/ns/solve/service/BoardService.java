package com.ns.solve.service;

import com.ns.solve.domain.Board;
import com.ns.solve.repository.BoardRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }
    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }
    public Board updateBoard(Long id, Board boardDetails) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Board not found"));
        board.setTitle(boardDetails.getTitle());
        board.setType(boardDetails.getType());
        board.setCreator(boardDetails.getCreator());
        return boardRepository.save(board);
    }
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
