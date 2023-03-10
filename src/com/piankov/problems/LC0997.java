package com.piankov.problems;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

/*
https://leetcode.com/problems/find-the-town-judge/

In a town, there are n people labeled from 1 to n. There is a rumor that one of these people is secretly the town judge.

If the town judge exists, then:

The town judge trusts nobody.
Everybody (except for the town judge) trusts the town judge.
There is exactly one person that satisfies properties 1 and 2.
You are given an array trust where trust[i] = [ai, bi] representing that the person labeled ai trusts the person labeled bi.

Return the label of the town judge if the town judge exists and can be identified, or return -1 otherwise.



Example 1:
Input: n = 2, trust = [[1,2]]
Output: 2

Example 2:
Input: n = 3, trust = [[1,3],[2,3]]
Output: 3

Example 3:
Input: n = 3, trust = [[1,3],[2,3],[3,1]]
Output: -1


Constraints:
1 <= n <= 1000
0 <= trust.length <= 104
trust[i].length == 2
All the pairs of trust are unique.
ai != bi
1 <= ai, bi <= n
 */
public class LC0997 {
    public void test() {
        try (Stream<Path> paths = Files.walk(Paths.get("input/" + this.getClass().getSimpleName() + "/"))) {
            paths.filter(Files::isRegularFile).forEach(this::readFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void readFile(Path path) {
        try {
            int lines = countLines(path.toString()) - 1;

            Scanner scanner = new Scanner(path.toFile());
            int n = scanner.nextInt();

            int[][] trust = new int[lines][2];
            for (int i = 0; i < lines; i++) {
                trust[i][0] = scanner.nextInt();
                trust[i][1] = scanner.nextInt();
            }

            scanner.close();

            System.out.println(findJudge(n, trust));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int countLines(String fileName) {
        Path path = Paths.get(fileName);

        int lines = 0;
        try {
            lines = (int) Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    /*
    ???????? ??????????????:
    1) ?????????????? ?????? ?????????????? trustTo ?? trustBy, ???????????????????? ???? ?????????????? ?? ???????? ?? ?????????????? ???? ????????-???? ????????????????????????????.
    2) ???????????????? ???? ?????????????? ?????????????????? trust ?? ?????????????????? ???????????????????? 2 ?????????????? ?????????????????????????????????? ?????????? ????????????????????
    3) ???????????????? ???? ?????????????? trustTo ?? ???????? ?????????????????? ?????????????? ???? ?????????????????? 0,
    ???? ?????????????????? ?????????????? ?? ?????? ???? ???????????????? ?? ?????????????? trustBy, ?? ???????? ???? ???????????????????????????? ?????????? n-1, ???? ?????? ?? ???????? ??????????

    ??????????????????: O(trust.length)
    ????????????: O(n)
     */
    public int findJudge(int n, int[][] trust) {
        int judgeLabel = -1;

        int[] trustTo = new int[n];
        int[] trustBy = new int[n];

        for (int[] ints : trust) {
            trustTo[ints[0] - 1]++;
            trustBy[ints[1] - 1]++;
        }

        for (int i = 0; i < n; i++) {
            if (trustTo[i] == 0 && trustBy[i] == n - 1) {
                judgeLabel = i + 1;
                break;
            }
        }

        return judgeLabel;
    }
}
