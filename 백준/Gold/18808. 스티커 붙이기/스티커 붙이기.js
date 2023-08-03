let N, M; // 맵크기
let K; // 스티커 개수
let R, C; // 현재 스티커 크기
let Map;
let count = 0;
let sticker;

const rotate = () => {
  const copySticker = [...sticker.map((row) => [...row])];
  const [prevR, prevC] = [R, C];
  [R, C] = [C, R];
  sticker = new Array(R).fill(null).map(() => new Array(C).fill(0));
  for (let r = 0; r < prevR; r++) {
    for (let c = 0; c < prevC; c++) {
      sticker[c][prevR - r - 1] = copySticker[r][c];
    }
  }
};

const check = (startN, startM) => {
  // Map[startN][startM] 부터 sticker 붙일 수 있는지 확인
  for (let n = startN; n < startN + R; n++) {
    for (let m = startM; m < startM + C; m++) {
      if (Map[n][m] === 1 && sticker[n - startN][m - startM] === 1) {
        // 붙일 수 x
        return false;
      }
    }
  }

  // 붙일 수 o
  return true;
};

const attachSticker = (startN, startM) => {
  for (let n = startN; n < startN + R; n++) {
    for (let m = startM; m < startM + C; m++) {
      if (sticker[n - startN][m - startM] === 1) {
        // 맵에 스티커 붙이고 붙인칸개수++
        Map[n][m] = 1;
        count++;
      }
    }
  }
};

const attach = () => {
  // 맨 위, 맨 왼쪽부터 붙일 수 있는지 확인
  for (let startN = 0; startN <= N - R; startN++) {
    for (let startM = 0; startM <= M - C; startM++) {
      const canAttach = check(startN, startM);
      if (canAttach) {
        // 붙일 수 o
        attachSticker(startN, startM); // 스티커 붙이러 가기
        return true;
      }
    }
  }

  // 붙일 수 x
  return false;
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');

  const [n, m, k] = input.shift().split(' ');
  N = +n;
  M = +m;
  K = +k;
  //입력

  // 맵 초기화
  Map = new Array(N).fill(null).map(() => new Array(M).fill(0));
  // console.log(Map.map((row) => row.join(' ')).join('\n'));

  // k개의 스티커 순서대로 붙여보기
  for (let k = 1; k <= K; k++) {
    // 스티커 정보 입력받아서
    const [r, c] = input.shift().split(' ');
    R = +r;
    C = +c;
    sticker = new Array(R);
    for (let i = 0; i < R; i++) {
      sticker[i] = [
        ...input
          .shift()
          .split(' ')
          .map((el) => +el),
      ];
    }

    // 맵에 붙임
    for (let rota = 0; rota < 4; rota++) {
      const isSuccess = attach();
      if (isSuccess) break;

      // 못붙이면 90도씩 회전
      rotate();
    }
  }

  // 맵에서 스티커 붙은 칸의 수 출력
  console.log(count);
};

main();