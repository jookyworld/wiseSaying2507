import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

public class App {
    Scanner scanner = new Scanner(System.in);
    List<WiseSaying> wiseSayings = new ArrayList<>();
    int lastId = 0;

    void run() {
        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();

            Rq rq = new Rq(cmd);

            switch (rq.getActionName()) {
                case "종료":
                    System.out.println("시스템을 종료합니다.");
                    return;
                case "등록":
                    actionWrite();
                    break;
                case "목록":
                    actionList();
                    break;
                case "삭제":
                    actionDelete(rq);
                    break;
                case "수정":
                    actionUpdate(rq);
                    break;
            }

        }
    }

    void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();
        int writedId = write(author, content);
        System.out.println(writedId + "번 명언이 등록되었습니다.");
    }

    int write(String author, String content) {
        lastId++;
        WiseSaying wiseSaying = new WiseSaying(lastId, author, content);
        wiseSayings.add(wiseSaying);
        return lastId;
    }

    void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("------------------------------");

//        for (int i = wiseSayings.size() - 1; i >= 0; i--) {
//            WiseSaying ws = wiseSayings.get(i);
//            System.out.println(ws.getId() + " / " + ws.getAuthor() + " / " + ws.getContent());
//        }

        IntStream.range(0, wiseSayings.size())
                .map(i -> wiseSayings.size() - 1 - i)
                .mapToObj(wiseSayings::get)
                .forEach(ws -> {
                    System.out.println(ws.getId() + " / " + ws.getAuthor() + " / " + ws.getContent());
                });

    }

    void actionDelete(Rq rq) {
        int deleteId = rq.getParamAsInt("id", -1);
        if (deleteId == -1) {
            System.out.println("숫자를 입력해주세요.");
            return;
        }
        WiseSaying deleteWiseSaying = getIdFromCmd(deleteId);

        if (deleteWiseSaying == null) return;

        delete(deleteWiseSaying);
        System.out.println(deleteId + "번 명언이 삭제되었습니다.");
    }

    void delete(WiseSaying wiseSaying) {
        wiseSayings.remove(wiseSaying);
    }

    void actionUpdate(Rq rq) {
        int updateId = rq.getParamAsInt("id", -1);
        if (updateId < -1) {
            System.out.println("숫자를 입력해주세요.");
            return;
        }
        WiseSaying updateWiseSaying = getIdFromCmd(updateId);
        if (updateWiseSaying == null) return;

        System.out.println("명언(기존) : " + updateWiseSaying.getContent());
        System.out.print("명언 : ");
        String updateContent = scanner.nextLine().trim();
        System.out.println("작가(기존) : " + updateWiseSaying.getAuthor());
        System.out.print("작가 : ");
        String updateAuthor = scanner.nextLine().trim();
        update(updateWiseSaying, updateAuthor, updateContent);

        System.out.println(updateId + "번 명언이 수정되었습니다.");
    }

    void update(WiseSaying wiseSaying, String updateAuthor, String updateContent) {
        wiseSaying.setAuthor(updateAuthor);
        wiseSaying.setContent(updateContent);
    }

    WiseSaying getIdFromCmd(int id) {
        return wiseSayings.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseGet(() -> {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                    return null;
                });
    }
}
