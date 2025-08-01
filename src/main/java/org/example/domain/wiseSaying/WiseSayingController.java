package org.example.domain.wiseSaying;

import org.example.Rq;
import org.example.WiseSaying;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class WiseSayingController {
    private final Scanner scanner;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(Scanner scanner) {
        this.scanner = scanner;
        this.wiseSayingService = new WiseSayingService();
    }

    public void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();
        WiseSaying wiseSaying = wiseSayingService.write(author, content);
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    public void actionList() {
        List<WiseSaying> wiseSayings = wiseSayingService.getWiseSayings();

        System.out.println("번호 / 작가 / 명언");
        System.out.println("------------------------------");

        IntStream.range(0, wiseSayings.size())
                .map(i -> wiseSayings.size() - 1 - i)
                .mapToObj(wiseSayings::get)
                .forEach(ws -> {
                    System.out.println(ws.getId() + " / " + ws.getAuthor() + " / " + ws.getContent());
                });

    }

    public void actionDelete(Rq rq) {
        int deleteId = rq.getParamAsInt("id", -1);
        if (deleteId == -1) {
            System.out.println("숫자를 입력해주세요.");
            return;
        }
        WiseSaying deleteWiseSaying = wiseSayingService.findById(deleteId);

        if (deleteWiseSaying == null) return;

        wiseSayingService.delete(deleteWiseSaying);
        System.out.println(deleteId + "번 명언이 삭제되었습니다.");
    }

    public void actionModify(Rq rq) {
        int updateId = rq.getParamAsInt("id", -1);
        if (updateId < -1) {
            System.out.println("숫자를 입력해주세요.");
            return;
        }
        WiseSaying updateWiseSaying = wiseSayingService.findById(updateId);
        if (updateWiseSaying == null) return;

        System.out.println("명언(기존) : " + updateWiseSaying.getContent());
        System.out.print("명언 : ");
        String updateContent = scanner.nextLine().trim();
        System.out.println("작가(기존) : " + updateWiseSaying.getAuthor());
        System.out.print("작가 : ");
        String updateAuthor = scanner.nextLine().trim();
        wiseSayingService.modify(updateWiseSaying, updateAuthor, updateContent);

        System.out.println(updateId + "번 명언이 수정되었습니다.");
    }

}
