package jdk.java.nio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>{@link Path} 클래스 테스트
 *
 * <p>src/main 혹은 src/test는 로컬에서만 사용해야하는 경로다. 이 경로들은 메이븐 개발 환경에서만 존재하는 폴더 구조이고 war나 jar로 빌드되면 존재하지 않는 경로이기 때문.
 * <p>따라서 경로빌드 후 생성될 경로를 절대 경로로 프로퍼티에서 관리하는 편이 좋다.
 *
 * @author fixalot
 * @since 2017-07-27
 */
@Slf4j
public class PathTest {

    @Test
    public void testOf() {
        Path path = Path.of("/a/b/c");
        Path path2 = Path.of("/a", "b", "c");
        assertEquals(path, path2);

        Path path3 = Path.of("a/b/c");
        Path path4 = Path.of("a", "b", "c");
        assertEquals(path3, path4);

        assertEquals(new File("/a/b/c"), path.toFile());
    }

    /**
     * <p>파일 시스템의 루트부터 주어진 경로까지의 절대 경로를 반환하는 메서드.
     * <p>OS의 파일 시스템에 맞는 경로를 반환한다.
     */
    @Test
    public void testAbsolutePath() {
        Path path = Path.of("/a/b/c");
        assertEquals(Path.of("C:\\a\\b\\c"), path.toAbsolutePath());

        Path path2 = Path.of("a/b/c");
//        assertEquals("C:\\dev\\intellij-workspace\\java-lab\\a\\b\\c", path2.toAbsolutePath().toString()); // 로컬 저장소 경로에 따라 달라질 수 있어서 코멘트 처리함
    }

    /**
     * toUri() 테스트
     */
    @Test
    public void testToUri() {
        Path path = Path.of("/a/b/c");
        assertEquals("file:///C:/a/b/c", path.toUri().toString());
        assertEquals("/C:/a/b/c", path.toUri().getPath().toString());
    }

    /**
     * 어떠한 경로 두 개가 있을 때 서로의 상대 경로를 구하는 메서드
     */
    @Test
    public void testRelativize() {
        Path path1 = new File("c:/dev/git").toPath();
        Path path2 = new File("c:/sso").toPath();
        assertEquals(Path.of("../../sso"), path1.relativize(path2)); // dev에서 sso로 가려면 'cd ..\..\sso'
        assertEquals(Path.of("../dev/git"), path2.relativize(path1)); // sso에서 dev로 가려면 'cd ..\dev\git'
        assertEquals("", path1.relativize(path1).toString()); // dev에서 dev로 가려면 'cd .'
    }

    /**
     * source부터 target까지의 상대 경로를 구한다.
     */
    @Test
    public void testRelativize2() {
        Path source = new File("webapp/upload/temp/").toPath();
        Path target = new File("webapp/upload/temp/201612/28201838255.png").toPath();
        assertEquals(Path.of("201612/28201838255.png"), source.relativize(target)); // dev에서 sso로 가려면 'cd ..\..\sso'
    }

    /**
     * <p>기존 Path 객체에 문자열을 이어 붙여 하위 경로를 생성할 때 사용한다.</p>
     * <p>단순히 이어붙이기만 현재 경로와 주어진 경로를 결합하는데,</p>
     * <p>만약 주어진 경로에 ..  / 같은 문자가 있으면 현재 경로에서 상위 경로로 이동하는 결과가 나온다</p>
     *
     * @throws IOException
     */
    @Test
    public void testResolve() throws IOException {
        // 원본
        Path directory = new File("c:/dev/repo").toPath();
        Path directory2 = Path.of(directory.toString());
        Path directory3 = Path.of(directory.toString());
        Path directory4 = Path.of(directory.toString());

        // 파일명으로 붙이기
        directory = directory.resolve("a.txt");
        assertEquals(Path.of("C:/dev/repo/a.txt"), directory);

        // 여러개 이어붙이기
        String[] addtionalDirectories = new String[]{"a", "b", "c"};
        for (String addtionalDirectory : addtionalDirectories) {
            directory2 = directory2.resolve(addtionalDirectory);
        }
        assertEquals(Path.of("C:/dev/repo/a/b/c"), directory2);

        // 상위 경로로 이동하기
        directory3 = directory3.resolve("../parent");
        assertEquals(Path.of("C:/dev/repo/../parent"), directory3);
        // 만약 정규화된 경로를 얻고 싶다면 Path.normalize()를 사용함
        assertEquals(Path.of("C:/dev/parent"), directory3.normalize());

        // 상위 경로로 이동하기: 특이 케이스
        directory4 = directory4.resolve("/super");
        assertEquals(Path.of("C:/super"), directory4);
    }

    /**
     * 주어진 경로에서 시작 인덱스와 종료 인덱스 만큼을 잘라냄
     */
    @Test
    public void testSubpath() {
        Path path = Path.of("/a/b/c/e/f/g.jpg");
        assertEquals(Path.of("a/b/c"), path.subpath(0, 3));
        assertEquals(Path.of("e/f/g.jpg"), path.subpath(3, 6));
    }

    /**
     * normalize() 테스트
     */
    @Test
    public void testNormalize() {
        Path path = Path.of("./../../a/b/c.jpg");
        assertEquals(Path.of("../../a/b/c.jpg"), path.normalize());
    }
}
