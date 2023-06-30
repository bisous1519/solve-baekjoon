let R;
let C;
let Map;

const main = () => {
  const input = require('fs')
    .readFileSync('/dev/stdin')
    .toString()
    .trim()
    .split('\n');
  const tempArr = input[0].split(' ');
  R = +tempArr[0];
  C = +tempArr[1];
  Map = input[1].split(' ').map((num) => +num);

  let sum = 0;
  for (let r = 1; r <= R; r++) {
    let isBlocked = false;
    let blockedIdx;
    for (let c = 0; c < C; c++) {
      if (r <= Map[c]) {
        if (!isBlocked) {
          // 지금부터 막힘
        } else {
          // 여기까지 막힘
          sum += c - blockedIdx - 1;
        }
        isBlocked = true;
        blockedIdx = c;
      }
    }
  }

  console.log(sum);
};

main();