let R;
let C;
let Map = [];
let CCTVlist = [];
let CCTVcnt;
let Min = Number.MAX_SAFE_INTEGER;
const D = {
  up: [-1, 0],
  down: [1, 0],
  left: [0, -1],
  right: [0, 1],
};
const dirNum = {
  // 각 감카의 감시 방향
  1: [['up'], ['down'], ['left'], ['right']],
  2: [
    ['left', 'right'],
    ['up', 'down'],
  ],
  3: [
    ['up', 'right'],
    ['right', 'down'],
    ['down', 'left'],
    ['left', 'up'],
  ],
  4: [
    ['left', 'up', 'right'],
    ['up', 'right', 'down'],
    ['right', 'down', 'left'],
    ['down', 'left', 'up'],
  ],
  5: [['up', 'down', 'left', 'right']],
};

function CCTV(num, r, c, d = 0) {
  this.num = num;
  this.r = r;
  this.c = c;
  this.d = d;
}

const isIn = (r, c) => {
  return 0 <= r && r < R && 0 <= c && c < C;
};

const countBlind = (copyMap) => {
  let cnt = 0;
  copyMap.forEach((row) =>
    row.forEach((el) => {
      if (el === 0) cnt++;
    })
  );
  Min = Math.min(Min, cnt);
};

const camOn = (cctvDir) => {
  // 감시 되는 곳 체크하기 위해 Map 카피
  let copyMap = [...Map.map((row) => [...row])];

  // cctv 하나씩 정해진 방향대로 감시 체크
  CCTVlist.forEach((cctv, idx) => {
    dirNum[cctv.num][cctvDir[idx]].forEach((dir) => {
      // Map 경계선 만나거나 벽 만날때까지 쭉 감시 체크
      let goR = cctv.r;
      let goC = cctv.c;
      while (true) {
        goR += D[dir][0];
        goC += D[dir][1];
        if (!isIn(goR, goC)) break;
        if (copyMap[goR][goC] === 6) break;

        if (copyMap[goR][goC] === 0) {
          // 오키 감시 체크
          copyMap[goR][goC] = 7;
        }
      }
    });
  });

  // 사각지대 개수 세기
  countBlind(copyMap);
};

// CCTVlist에서 몇 번째 CCTV의 방향을 정할건지
// cctvDir[] : 각 카메라가 보고 있는 방향이 들어있는 배열
const setCCTVDir = (nth, cctvDir) => {
  if (nth >= CCTVcnt) {
    // console.log('set CCTV Dir: ', cctvDir.join(' '));
    // 정해진 방향들로 Map 감시하러 가보기
    camOn(cctvDir);
    return;
  }

  // nth번째 감시 카메라의 방향을 정하고 다음 카메라의 방향 정하러감
  const curcam = CCTVlist[nth].num;
  dirNum[curcam].forEach((dir, idx) => setCCTVDir(nth + 1, [...cctvDir, idx]));
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');
  const [r, c] = input[0].split(' ');
  R = +r;
  C = +c;
  input.shift();

  Map = [...input.map((row) => [...row.split(' ').map((num) => +num)])];
  //   console.log(Map.map((row) => row.join(' ')).join('\n'));

  // Map 돌면서 CCTV 있는 곳 종류랑 위치를 list에 따로 저장
  Map.map((row, r) =>
    row.map((num, c) => {
      // CCTV 면
      if (1 <= num && num <= 5) {
        CCTVlist.push(new CCTV(num, r, c));
      }
    })
  );
  CCTVcnt = CCTVlist.length;

  // 감시하러
  setCCTVDir(0, []);

  // 출력
  console.log(Min);
};

main();