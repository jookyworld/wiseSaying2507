package org.example.domain.wiseSaying;

import org.example.WiseSaying;

import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    WiseSayingService() {
        wiseSayingRepository = new WiseSayingRepository();
    }

    public List<WiseSaying> getWiseSayings() {
        return wiseSayingRepository.getWiseSayings();
    }

    public WiseSaying write(String author, String content) {
        return wiseSayingRepository.write(author, content);
    }

    void delete(WiseSaying wiseSaying) {
        wiseSayingRepository.delete(wiseSaying);
    }

    void modify(WiseSaying wiseSaying, String updateAuthor, String updateContent) {
        wiseSayingRepository.modify(wiseSaying, updateAuthor, updateContent);
    }

    WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }

}
