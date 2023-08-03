let R, C; // 맵의 크기
let Map; // 말의 위치 저장하는 배열
let Check; // 지나간 자리 체크하는 배열
const dQR = [-1, -1, 0, 1, 1, 1, 0, -1]; // 상 상우 우 우하 하 하좌 좌 좌상
const dQC = [0, 1, 1, 1, 0, -1, -1, -1];
const dKR = [-2, -1, 1, 2, 2, 1, -1, -2]; // 상좌 상우 우상 우하 하좌 하우 좌상 좌하
const dKC = [1, 2, 2, 1, -1, -2, -2, -1];

const printCheck = () => {
  console.log(Check.map((row) => row.join(' ')).join('\n'));
};

const isIn = (r, c) => {
  return 0 <= r && r < R && 0 <= c && c < C;
};

const checkQueen = (loc) => {
  for (let i = 0; i < loc.length; i += 2) {
    const r = loc[i];
    const c = loc[i + 1];

    for (let d = 0; d < dQR.length; d++) {
      let goR = r;
      let goC = c;
      while (true) {
        goR += dQR[d];
        goC += dQC[d];

        if (!isIn(goR, goC)) break; // 맵 밖이면 그만
        if (Map[goR][goC]) break; // 말이 있으면 그만

        Check[goR][goC] = true;
      }
    }
  }
};

const checkKnight = (loc) => {
  for (let i = 0; i < loc.length; i += 2) {
    const r = loc[i];
    const c = loc[i + 1];

    for (let d = 0; d < dKR.length; d++) {
      const goR = r + dKR[d];
      const goC = c + dKC[d];

      if (!isIn(goR, goC)) continue; // 맵 밖이면 패스

      Check[goR][goC] = true;
    }
  }
};

const checkLoc = (locArr) => {
  for (let i = 0; i < locArr.length; i += 2) {
    const r = locArr[i];
    const c = locArr[i + 1];
    Map[r][c] = true;
    Check[r][c] = true;
  }
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');

  const [r, c] = input.shift().split(' ');
  R = +r;
  C = +c;

  // 맵에 말들 위치 체크
  Map = new Array(R).fill(null).map(() => new Array(C).fill(false));
  Check = [...Map.map((row) => [...row])];
  // Queen
  const [q, ...locQ] = [
    ...input
      .shift()
      .split(' ')
      .map((el) => +el - 1),
  ];
  checkLoc(locQ);
  // Knight
  const [k, ...locK] = [
    ...input
      .shift()
      .split(' ')
      .map((el) => +el - 1),
  ];
  checkLoc(locK);
  // Pawn
  const [p, ...locP] = [
    ...input
      .shift()
      .split(' ')
      .map((el) => +el - 1),
  ];
  checkLoc(locP);

  // 말들 움직이는 범위 체크
  checkQueen(locQ);
  checkKnight(locK);

  // 안전한 자리 개수세기
  let count = 0;
  Check.forEach((row) => (count += row.filter((el) => el === false).length));
  console.log(count);
};

main();