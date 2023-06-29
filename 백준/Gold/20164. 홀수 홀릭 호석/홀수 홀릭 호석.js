let Min = Number.MAX_SAFE_INTEGER;
let Max = 0;

const countOddNum = (arr) => arr.filter((num) => num % 2 === 1).length;

const recur = (N, cntOdd) => {
  const arr = N.split('');
  const len = arr.length;

  const sumCntOdd = cntOdd + countOddNum(arr);

  if (len === 1) {
    Min = Math.min(Min, sumCntOdd);
    Max = Math.max(Max, sumCntOdd);
    return;
  } else if (len === 2) {
    const temp = parseInt(arr[0]) + parseInt(arr[1]);
    recur(temp.toString(), sumCntOdd);
  } else if (len >= 3) {
    for (let i = 0; i < len - 2; i++) {
      for (let j = i + 1; j < len - 1; j++) {
        // 삼등분함
        const num1 = parseInt(N.substring(0, i + 1));
        const num2 = parseInt(N.substring(i + 1, j + 1));
        const num3 = parseInt(N.substring(j + 1));

        // 세 개 수 만들어짐
        recur((num1 + num2 + num3).toString(), sumCntOdd);
      }
    }
  }
};

const main = () => {
  const input = require('fs')
    .readFileSync('/dev/stdin')
    .toString()
    .trim();
  const N = input;

  recur(N, 0);
  console.log(Min, Max);
};

main();