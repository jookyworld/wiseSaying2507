import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);
        int id = 1;
        List<WiseSaying> wiseSayings = new ArrayList<>();
        File lastIdFile = new File("src/wiseSaying/lastId.text");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("종료")) {
                System.out.println("프로그램을 종료합니다");
                break;

            } else if (cmd.equals("등록")) {
                System.out.print("명언 : ");
                String content = scanner.nextLine().trim();
                System.out.print("작가 : ");
                String author = scanner.nextLine().trim();
                WiseSaying wiseSaying = new WiseSaying(id, author, content);
                wiseSayings.add(wiseSaying);

                File file = new File("/Users/jooky/Documents/projects/dev/wiseSaying2507/src/db/wiseSaying/" + id + ".json");
                if (file.createNewFile()) {
                    System.out.println("파일 생성 완료");
                } else {
                    System.out.println("파일 생성 실패");
                }


                System.out.println(id++ + "번 명언이 등록되었습니다.");

            } else if (cmd.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");
                for (WiseSaying wiseSaying : wiseSayings) {
                    System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
                }

            } else if (cmd.contains("삭제")) {
                int deleteId = Integer.parseInt(cmd.substring(cmd.indexOf('=') + 1));
                Optional<WiseSaying> ws = wiseSayings.stream()
                        .filter(e -> e.getId() == deleteId)
                        .findFirst();
                WiseSaying deleteWiseSaying = ws.orElse(null);

                if (deleteWiseSaying == null) {
                    System.out.println(deleteId + "번 명언은 존재하지 않습니다.");
                } else {
                    wiseSayings.remove(deleteWiseSaying);
                    System.out.println(deleteId + "번 명언이 삭제되었습니다.");
                }

            } else if (cmd.contains("수정")) {
                int updateId = Integer.parseInt(cmd.substring(cmd.indexOf('=') + 1));
                Optional<WiseSaying> ws = wiseSayings.stream()
                        .filter(e -> e.getId() == updateId)
                        .findFirst();
                WiseSaying updateWiseSaying = ws.orElse(null);

                if (updateWiseSaying == null) {
                    System.out.println(updateId + "번 명언은 존재하지 않습니다.");
                } else {
                    System.out.println("명언(기존) : " + updateWiseSaying.getContent());
                    System.out.print("명언 : ");
                    String content = scanner.nextLine().trim();
                    System.out.println("작가(기존) : " + updateWiseSaying.getAuthor());
                    System.out.print("작가 : ");
                    String author = scanner.nextLine().trim();
                    updateWiseSaying.setContent(content);
                    updateWiseSaying.setAuthor(author);
                }
            }

        }
        scanner.close();

    }
}

class WiseSaying {
    private int id;
    private String author;
    private String content;

    WiseSaying(int id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    int getId() {
        return this.id;
    }

    String getAuthor() {
        return this.author;
    }

    String getContent() {
        return this.content;
    }

    void setAuthor(String author) {
        this.author = author;
    }

    void setContent(String content) {
        this.content = content;
    }
}
