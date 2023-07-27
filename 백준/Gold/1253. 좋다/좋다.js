let N;
let Nums;

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');
  N = +input.shift();
  Nums = [...input[0].split(' ').map((el) => +el)];
  // 입력

  // 초기화 : Nums 오름차순 정렬 (투포인터 위해)
  Nums.sort((a, b) => a - b);

  let cnt = 0;
  for (let point = 0; point < N; point++) {
    let lIdx = 0;
    let rIdx = N - 1;
    while (lIdx < rIdx) {
      if (Nums[lIdx] + Nums[rIdx] < Nums[point]) {
        lIdx++;
      } else if (Nums[lIdx] + Nums[rIdx] > Nums[point]) {
        rIdx--;
      } else {
        if (point !== lIdx && point !== rIdx) {
          cnt++;
          break;
        } else if (point === lIdx) {
          lIdx++;
        } else if (point === rIdx) {
          rIdx--;
        }
      }
    }
  }

  console.log(cnt);
};

main();