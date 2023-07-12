let R;
let C;
let Map = [];
let isSelected;
let maxValue = Number.MIN_SAFE_INTEGER;
let Max = Number.MIN_SAFE_INTEGER;
const dr = [0, 1, 0]; // 우 하 좌
const dc = [1, 0, -1];

const isIn = (r, c) => {
  return 0 <= r && r < R && 0 <= c && c < C;
};

const dfs = (r, c, sum, cnt) => {
  if (cnt === 4) {
    Max = Math.max(Max, sum);
    // console.log(isSelected.map((row) => row.join(' ')).join('\n'));
    return;
  }

  // 남은 칸이 최대 숫자여도 어짜피 Max를 갱신할 정도가 안된다면 return
  if (sum + (4 - cnt) * maxValue <= Max) return;

  for (let d = 0; d < 3; d++) {
    const goR = r + dr[d];
    const goC = c + dc[d];
    if (!isIn(goR, goC)) continue;
    if (isSelected[goR][goC]) continue;

    isSelected[goR][goC] = true;
    let nextSum = sum + Map[goR][goC];
    dfs(goR, goC, nextSum, cnt + 1);

    // ㅜ모양 예외처리
    if (cnt === 2 && d === 0) {
      if (isIn(r + 1, c) && !isSelected[r + 1][c]) {
        // ㅜ
        dfs(-1, -1, nextSum + Map[r + 1][c], cnt + 2);
      }
      if (isIn(r - 1, c) && !isSelected[r - 1][c]) {
        // ㅗ
        dfs(-1, -1, nextSum + Map[r - 1][c], cnt + 2);
      }
    } else if (cnt === 2 && d === 1) {
      // ㅏ
      if (isIn(r, c + 1) && !isSelected[r][c + 1]) {
        dfs(-1, -1, nextSum + Map[r][c + 1], cnt + 2);
      }
      // ㅓ
      if (isIn(r, c - 1) && !isSelected[r][c - 1]) {
        dfs(-1, -1, nextSum + Map[r][c - 1], cnt + 2);
      }
    }
    isSelected[goR][goC] = false;
  }
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');
  const [r, c] = input[0].split(' ');
  R = +r;
  C = +c;
  input.shift();

  Map = [...input.map((row) => [...row.split(' ').map((num) => +num)])];
  // 입력

  // Map에서 가장 큰 숫자를 찾아 저장
  Map.forEach((row) =>
    row.forEach((num) => {
      maxValue = Math.max(maxValue, num);
    })
  );

  // 각 칸마다 dfs 돌리러가기
  isSelected = Array.from(Array(R), () => Array(C).fill(false));
  for (let r = 0; r < R; r++) {
    for (let c = 0; c < C; c++) {
      isSelected[r][c] = true;
      // console.log(isSelected.map((row) => row.join(' ')).join('\n'));
      dfs(r, c, Map[r][c], 1);
      isSelected[r][c] = false;
    }
  }

  console.log(Max);
};

main();