public class SudokuSolver {
    // 全局标记数组，记录行/列/宫格的数字存在情况
    private boolean[][] row = new boolean[9][9];
    private boolean[][] col = new boolean[9][9];
    private boolean[][] box = new boolean[9][9];

    public void solveSudoku(char[][] board) {
        // 1. 预处理：初始化标记数组（根据初始数字）
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1'; // 转为0-8索引
                    int k = (i / 3) * 3 + (j / 3); // 宫格编号
                    row[i][num] = true;
                    col[j][num] = true;
                    box[k][num] = true;
                }
            }
        }
        // 2. 从(0,0)开始回溯
        backtrack(board, 0, 0);
    }

    // 回溯函数：尝试填充board[i][j]
    private boolean backtrack(char[][] board, int i, int j) {
        // 终止条件：已遍历完所有行
        if (i == 9) return true;
        
        // 移动到下一行
        if (j == 9) return backtrack(board, i + 1, 0);
        
        // 如果当前格子已填，跳过
        if (board[i][j] != '.') {
            return backtrack(board, i, j + 1);
        }

        // 尝试填入数字1-9
        int k = (i / 3) * 3 + (j / 3); // 宫格编号
        for (char c = '1'; c <= '9'; c++) {
            int num = c - '1'; // 转为0-8索引
            
            // 检查是否冲突
            if (row[i][num] || col[j][num] || box[k][num]) {
                continue;
            }
            
            // 填入数字并更新标记
            board[i][j] = c;
            row[i][num] = true;
            col[j][num] = true;
            box[k][num] = true;
            
            // 递归处理下一个格子
            if (backtrack(board, i, j + 1)) {
                return true; // 找到解，直接返回
            }
            
            // 回溯：恢复状态
            board[i][j] = '.';
            row[i][num] = false;
            col[j][num] = false;
            box[k][num] = false;
        }
        return false; // 当前位置无解
    }

    // 测试用例
    public static void main(String[] args) {
        char[][] board = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };

        new SudokuSolver().solveSudoku(board);
        
        // 打印结果
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }
}
