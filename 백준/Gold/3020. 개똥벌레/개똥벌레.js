let N;
let H;
let bottom;
let top;

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input2.txt`
    )
    .toString()
    .trim()
    .split('\n');
  const [n, h] = input[0].split(' ');
  N = +n;
  H = +h;
  input.shift();

  bottom = new Array(H + 1).fill(0);
  top = new Array(H + 1).fill(0);

  input.forEach((h, idx) => {
    if (idx % 2 === 0) bottom[h]++; // 석순
    else top[H - h + 1]++; // 종유석
  });

  // 누적합
  for (let h = 1; h <= H; h++) {
    top[h] += top[h - 1];
    bottom[H - h] += bottom[H - h + 1];
  }

  // console.log(bottom.toString());
  // console.log(top.toString());

  let Min = Number.MAX_SAFE_INTEGER;
  let cnt = 0;
  for (let h = 1; h <= H; h++) {
    if (top[h] + bottom[h] < Min) {
      cnt = 1;
      Min = top[h] + bottom[h];
    } else if (top[h] + bottom[h] === Min) {
      cnt++;
    }
  }

  console.log(Min, cnt);
};

main();