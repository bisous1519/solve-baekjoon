let N, M;
let Nums;
let Min = Number.MAX_SAFE_INTEGER;

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');

  [N, M] = input
    .shift()
    .split(' ')
    .map((el) => +el);
  Nums = [...input.map((el) => +el)];

  // 초기화
  Nums.sort((a, b) => a - b);
  let start = 0;
  let end = 0;

  // 투포인터
  while (start <= end && end < N) {
    const diff = Nums[end] - Nums[start];

    // 차가 M이라면 즉시 종료
    if (diff === M) {
      console.log(M);
      return;
    }

    if (diff < M) {
      end++;
    } else {
      Min = Math.min(Min, diff);
      start++;
    }
  }

  console.log(Min);
};

main();