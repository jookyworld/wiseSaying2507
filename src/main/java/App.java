import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    List<WiseSaying> wiseSayings = new ArrayList<>();
    int lastId = 0;

    void run() {
        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("종료")) {
                System.out.println("시스템을 종료합니다.");
                break;
            } else if (cmd.equals("등록")) {
                actionWrite();
            } else if (cmd.equals("목록")) {
                actionList();
            } else if (cmd.contains("삭제")) {
                actionDelete(cmd);
            } else if (cmd.contains("수정")) {
                actionUpdate(cmd);
            }
        }
        scanner.close();
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
//        for (WiseSaying ws : wiseSayings) {
//            System.out.println(ws.getId() + " / " +=== ws.getAuthor() + " / " + ws.getContent());
//        }
        for (int i = wiseSayings.size() - 1; i >= 0; i--) {
            WiseSaying ws = wiseSayings.get(i);
            System.out.println(ws.getId() + " / " + ws.getAuthor() + " / " + ws.getContent());
        }
    }

    void actionDelete(String cmd) {
        int deleteId = findWiseSayingIdByString(cmd);
        if (deleteId < 0) {
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

    void actionUpdate(String cmd) {
        int updateId = findWiseSayingIdByString(cmd);
        if (updateId < 0) {
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

    int findWiseSayingIdByString(String cmd) {
//        return Integer.parseInt(cmd.substring(cmd.indexOf('=') + 1));
        String[] str = cmd.split("=");
        if (str.length < 2 || str[1].isEmpty()) {
            System.out.println("id를 입력해주세요");
            return -1;
        }
        return Integer.parseInt(str[1]);
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
