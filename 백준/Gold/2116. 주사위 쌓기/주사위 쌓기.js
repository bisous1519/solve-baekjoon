let N; // 주사위 개수
let Dice = []; // 각 주사위 마다, 어떤 숫자가(key) 몇 번 인덱스(value)에 있는지 객체화해서 저장할 배열
let max = 0;
const findUpSide = {
  // A면(인덱스)이 바닥면일 때 -> B면(인덱스)이 윗면임을 객체화
  0: 5,
  1: 3,
  2: 4,
  3: 1,
  4: 2,
  5: 0,
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');
  N = +input.shift();

  // 각 주사위를 객체화해서 배열로 저장
  input.forEach((nthDice) => {
    let temp = {};
    nthDice.split(' ').forEach((num, idx) => {
      temp = {
        ...temp,
        [+num]: idx,
      };
    });
    Dice.push(temp);
  });

  // 첫번째 주사위의 각 면이 아랫면일 때 최댓값
  for (let firstBottom in Dice[0]) {
    let sum = 0;
    let bottom = +firstBottom;

    for (let n = 0; n < N; n++) {
      // bottom에 의해 top이 정해짐
      let top;
      let topIdx = findUpSide[Dice[n][bottom]];
      for (let num in Dice[n]) {
        if (Dice[n][num] === topIdx) {
          top = +num;
          break;
        }
      }

      // 사이드 면 중에 최댓값 찾아서 누적
      for (let maxSide = 6; maxSide >= 1; maxSide--) {
        if (maxSide === bottom || maxSide === top) continue;
        sum += maxSide;
        break;
      }

      // 지금 주사위의 윗면이 다음 주사위의 아랫면
      bottom = top;
    }

    max = Math.max(max, sum);
  }

  console.log(max);
};

main();