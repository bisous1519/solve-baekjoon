let N;
let M;
let R;
let Score = 0;
let OriginMap = [];
let Map = [];
let Dir = {
  E: [0, 1],
  W: [0, -1],
  S: [1, 0],
  N: [-1, 0],
};

const isIn = (n, m) => {
  return 0 <= n && n < N && 0 <= m && m < M;
};

const attack = (attX, attY, attD, defX, defY) => {
  if (Map[attX][attY] === 'F') return;

  let height = +Map[attX][attY];
  while (true) {
    if (height === 0) break;
    if (!isIn(attX, attY)) break;

    // 현재 칸 도미노가 더 높으면 넘어뜨리는 길이가 바뀜
    const curHeight = Map[attX][attY];
    if (1 <= +curHeight && +curHeight <= 5) {
      if (+curHeight > height) {
        height = +curHeight;
      }
    }

    // 넘어뜨리고 다음칸으로
    if (curHeight !== 'F') {
      Score++;
      Map[attX][attY] = 'F';
    }
    attX += Dir[attD][0];
    attY += Dir[attD][1];

    height--;
  }
};

const main = () => {
  const input = require('fs')
    .readFileSync('/dev/stdin')
    .toString()
    .trim()
    .split('\n');
  const [n, m, r] = input[0].split(' ');
  N = n;
  M = m;
  R = r;
  input.shift();

  for (let n = 0; n < N; n++) {
    OriginMap.push(input[0].split(' ').map((num) => +num));
    input.shift();
  } // 맵 입력
  // 맵 깊은복사로 하나 더 만들어놓기
  Map = OriginMap.map((column) => [...column]);

  // 라운드 시작
  for (let r = 1; r <= R; r++) {
    const [attX, attY, attD] = input[0].split(' ');
    const [defX, defY] = input[1].split(' ');
    input.shift();
    input.shift();

    // 공격
    attack(+attX - 1, +attY - 1, attD, +defX - 1, +defY - 1);
    // 수비
    Map[+defX - 1][+defY - 1] = OriginMap[+defX - 1][+defY - 1];
  }

  // 출력
  let answerStr = '';
  answerStr += Score + '\n';
  Map.map((column) => {
    column.map((el) => {
      if (el === 'F') {
        answerStr += 'F' + ' ';
      } else {
        answerStr += 'S' + ' ';
      }
    });
    answerStr += '\n';
  });
  console.log(answerStr);
};

main();