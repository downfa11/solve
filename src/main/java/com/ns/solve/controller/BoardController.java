package com.ns.solve.controller;

import com.ns.solve.domain.Board;
import com.ns.solve.domain.dto.MessageEntity;
import com.ns.solve.service.BoardService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<MessageEntity> createBoard(@RequestBody Board board) {
        Board createdBoard = boardService.createBoard(board);
        return new ResponseEntity<>(new MessageEntity("Board created successfully", createdBoard), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<MessageEntity> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        return new ResponseEntity<>(new MessageEntity("Boards fetched successfully", boards), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageEntity> getBoardById(@PathVariable Long id) {
        Optional<Board> board = boardService.getBoardById(id);
        if (board.isPresent()) {
            return new ResponseEntity<>(new MessageEntity("Board found", board.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageEntity("Board not found", null), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageEntity> updateBoard(@PathVariable Long id, @RequestBody Board boardDetails) {
        Board updatedBoard = boardService.updateBoard(id, boardDetails);
        return new ResponseEntity<>(new MessageEntity("Board updated successfully", updatedBoard), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageEntity> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return new ResponseEntity<>(new MessageEntity("Board deleted successfully", null), HttpStatus.NO_CONTENT);
    }
}
