package org.example.domain.wiseSaying;

import org.example.WiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    private final List<WiseSaying> wiseSayings = new ArrayList<>();
    int lastId = 0;

    public List<WiseSaying> getWiseSayings() {
        return wiseSayings;
    }

    public WiseSaying write(String author, String content) {
        WiseSaying wiseSaying = new WiseSaying(++lastId, author, content);
        wiseSayings.add(wiseSaying);
        return wiseSaying;
    }

    void delete(WiseSaying wiseSaying) {
        wiseSayings.remove(wiseSaying);
    }

    void modify(WiseSaying wiseSaying, String updateAuthor, String updateContent) {
        wiseSaying.setAuthor(updateAuthor);
        wiseSaying.setContent(updateContent);
    }

    WiseSaying findById(int id) {
        return wiseSayings.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseGet(() -> {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                    return null;
                });
    }
}
