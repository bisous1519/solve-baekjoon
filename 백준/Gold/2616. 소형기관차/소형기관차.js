let N; // 총 객차 수
let People; // 각 객차에 타고 있는 사람 수 배열
let M; // 소형기관차 한 대가 끌 수 있는 객차 수
let sum; // 누적합 배열
let dp; // dp 배열

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');

  // 입력
  N = +input.shift();
  People = input
    .shift()
    .split(' ')
    .map((el) => +el);
  M = +input.shift();

  // 누적합
  sum = new Array(N + 1).fill(0);
  People.forEach((num, idx) => {
    sum[idx + 1] = sum[idx] + num;
  });

  // 디피
  dp = new Array(4).fill(null).map(() => new Array(N + 1).fill(0));
  // i개 소형기관차 사용했을 때
  for (let i = 1; i <= 3; i++) {
    // n번째 객체까지 중에서 고를 때
    for (let n = i * M; n <= N; n++) {
      dp[i][n] = Math.max(
        dp[i][n - 1],
        dp[i - 1][n - M] + (sum[n] - sum[n - M])
      );
    }
  }

  console.log(dp[3][N]);
};

main();