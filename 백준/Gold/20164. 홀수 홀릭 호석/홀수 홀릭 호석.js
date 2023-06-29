let Min = Number.MAX_SAFE_INTEGER;
let Max = 0;

const countOddNum = (arr) => arr.filter((num) => num % 2 === 1).length;

const recur = (N, cntOdd) => {
  let arr = N.toString().split('');
  let len = arr.length;

  let sumCntOdd = cntOdd + countOddNum(arr);

  if (len === 1) {
    Min = Math.min(Min, sumCntOdd);
    Max = Math.max(Max, sumCntOdd);
    return;
  } else if (len === 2) {
    let temp = parseInt(arr[0]) + parseInt(arr[1]);
    recur(temp, sumCntOdd);
  } else if (len >= 3) {
    for (let i = 0; i < len - 2; i++) {
      for (let j = i + 1; j < len - 1; j++) {
        // 삼등분함
        let [num1, num2, num3] = [0, 0, 0];
        for (let k = 0; k <= i; k++) {
          num1 *= 10;
          num1 += parseInt(arr[k]);
        }
        for (let k = i + 1; k <= j; k++) {
          num2 *= 10;
          num2 += parseInt(arr[k]);
        }
        for (let k = j + 1; k < len; k++) {
          num3 *= 10;
          num3 += parseInt(arr[k]);
        }

        // 세 개 수 만들어짐
        recur(num1 + num2 + num3, sumCntOdd);
      }
    }
  }
};

const main = () => {
  const input = require('fs')
    .readFileSync('/dev/stdin')
    .toString()
    .trim();
  const N = parseInt(input);

  recur(N, 0);
  console.log(Min, Max);
};

main();