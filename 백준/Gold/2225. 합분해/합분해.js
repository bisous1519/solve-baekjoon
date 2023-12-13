// dp[k][n] = k개를 더해서 합이 n이 될 경우의 수

let N, K;
let dp;

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split(' ');

  [N, K] = input.map(el => +el);

  dp = new Array(K + 1).fill(null).map(() => new Array(N + 1).fill(0));
  for(let n=0; n<=N; n++) {
    dp[1][n] = 1;
  }

  for(let k=1; k<=K; k++) {
    for(let n=0; n<=N; n++) {
      for(let m=0; m<=n; m++) {
        dp[k][n] += dp[k-1][n-m];
        dp[k][n] %= 1000000000
      }
    }
  }

  console.log(dp[K][N]);
}

main();