const input = require('fs')
  .readFileSync('/dev/stdin')
  .toString()
  .trim()
  .split('\n')
  .map((el) => el.split(' ').map(Number));

const [N, M, H] = input[0];
const dp = new Array(N + 1).fill(null).map(() => new Array(1001).fill(0));
const list = input;

// 높이가 0을 만드는 경우의 수는 1가지
for (let i = 0; i <= N; i++) {
  dp[i][0] = 1;
}

for (let i = 1; i <= N; i++) {
  for (let j = 1; j <= H; j++) {
    for (const integer of list[i]) {
      if (j >= integer) {
        dp[i][j] += dp[i - 1][j - integer];
        dp[i][j] %= 10007;
      }
    }
    dp[i][j] += dp[i - 1][j];
    dp[i][j] %= 10007;
  }
}

console.log(dp[N][H]);