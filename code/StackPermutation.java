import java.util.*;

public class StackPermutation {
    public static void main(String[] args) {
        List<String> results = generateStackPermutations(3);
        System.out.println("所有合法出栈序列（共 " + results.size() + " 种）：");
        for (String seq : results) {
            System.out.println(seq);
        }
    }

    /**
     * 生成 1~n 的所有合法出栈序列
     * @param n 输入数字个数（如 n=3 表示 1,2,3）
     * @return 所有合法出栈序列的字符串列表
     */
    public static List<String> generateStackPermutations(int n) {
        List<String> results = new ArrayList<>();
        // 使用 StringBuilder 避免频繁字符串拼接
        backtrack(n, 1, new ArrayDeque<>(), new StringBuilder(), results);
        return results;
    }

    /**
     * 回溯核心函数
     * @param n 总数字个数
     * @param nextToPush 下一个待入栈的数字（从 1 开始）
     * @param stack 当前栈状态
     * @param current 当前输出序列（StringBuilder 用于高效拼接）
     * @param results 结果集合
     */
    private static void backtrack(int n, int nextToPush, Deque<Integer> stack, 
                                  StringBuilder current, List<String> results) {
        // 终止条件：已输出 n 个数字
        if (current.length() == n * 2 - 1) { // "1 2 3" 长度 = 5 = 3*2-1
            results.add(current.toString());
            return;
        }

        // 选择1：如果还有数字未入栈，尝试入栈
        if (nextToPush <= n) {
            stack.push(nextToPush);
            int len = current.length();
            if (len > 0) current.append(' ');
            current.append(nextToPush);
            
            // 递归：继续处理下一个数字
            backtrack(n, nextToPush + 1, stack, current, results);
            
            // 回溯：恢复状态
            current.setLength(len); // 撤销 append
            stack.pop();
        }

        // 选择2：如果栈非空，尝试出栈
        if (!stack.isEmpty()) {
            int top = stack.pop();
            int len = current.length();
            if (len > 0) current.append(' ');
            current.append(top);
            
            // 递归：栈顶已出，继续探索
            backtrack(n, nextToPush, stack, current, results);
            
            // 回溯：恢复状态
            current.setLength(len); // 撤销 append
            stack.push(top);        // 重新入栈
        }
    }
}
