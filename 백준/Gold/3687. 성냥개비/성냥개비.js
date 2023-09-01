let T;
const MinNum = [0, 0, 1, 7, 4, 2, 0, 8]; // 7개까지는 1자리 수로 최소값을 만들 수 있음!
let Dp;

const makeMax = (n) => {
  let result = '';

  if (n % 2 === 1) {
    result += '7';
    n -= 3;
  }

  for (let i = 1; i <= n / 2; i++) {
    result += '1';
  }

  return result;
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');

  T = +input.shift();

  // 최소값 찾기 위한 Dp 배열 채우기
  Dp = new Array(101).fill(Number.MAX_SAFE_INTEGER);
  Dp[2] = 1;
  Dp[3] = 7;
  Dp[4] = 4;
  Dp[5] = 2;
  Dp[6] = 6;
  Dp[7] = 8;
  for (let i = 8; i <= 100; i++) {
    for (let j = 2; j <= 7; j++) {
      Dp[i] = Math.min(Dp[i], Dp[i - j] * 10 + MinNum[j]);
    }
  }

  let answer = '';
  input.forEach((N) => {
    N = +N;

    // 최댓값
    const max = makeMax(N);

    answer += `${Dp[N]} ${max}`;
    answer += '\n';
  });

  console.log(answer);
};

main();