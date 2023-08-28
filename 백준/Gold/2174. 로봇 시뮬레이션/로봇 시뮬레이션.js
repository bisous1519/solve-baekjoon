let R, C;
let N, M;
let Map;
let Robots = {};
let answer = 'OK';
let fin = false;
const [goN, goW, goS, goE] = [0, 1, 2, 3];
const dr = [-1, 0, 1, 0]; // 위 왼 아 오 (-> L이면 +1, R이면 -1)
const dc = [0, -1, 0, 1];

const Robot = function (r, c, d) {
  this.r = r;
  this.c = c;
  this.d = d;
};

const isIn = (r, c) => {
  return 1 <= r && r <= R && 1 <= c && c <= C;
};

const turnR = (num, repeat) => {
  const robot = Robots[num];
  let newDir = (4 + robot.d - (repeat % 4)) % 4;

  Robots = {
    ...Robots,
    [num]: new Robot(robot.r, robot.c, newDir),
  };
};

const turnL = (num, repeat) => {
  const robot = Robots[num];
  const newDir = (robot.d + repeat) % 4;

  Robots = {
    ...Robots,
    [num]: new Robot(robot.r, robot.c, newDir),
  };
};

const moveF = (num, repeat) => {
  const robot = Robots[num];
  let goR = robot.r;
  let goC = robot.c;
  for (let rep = 1; rep <= repeat; rep++) {
    goR += dr[robot.d];
    goC += dc[robot.d];

    // 벽에 충돌
    if (!isIn(goR, goC)) {
      answer = `Robot ${num} crashes into the wall`;
      fin = true;
      return;
    }

    // 로봇에 충돌
    if (Map[goR][goC] !== 0) {
      answer = `Robot ${num} crashes into robot ${Map[goR][goC]}`;
      fin = true;
      return;
    }
  }

  Map[robot.r][robot.c] = 0;
  Map[goR][goC] = num;
  Robots = {
    ...Robots,
    [num]: new Robot(goR, goC, robot.d),
  };
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');

  const [a, b] = input.shift().split(' ');
  C = +a;
  R = +b;

  const [n, m] = input.shift().split(' ');
  N = +n;
  M = +m;

  // 로봇 입력
  Map = new Array(R + 1).fill(null).map(() => new Array(C + 1).fill(0));
  for (let n = 1; n <= N; n++) {
    const [a, b, d] = input.shift().split(' ');
    const dir = d === 'N' ? goN : d === 'W' ? goW : d === 'S' ? goS : goE;

    Map[R - +b + 1][+a] = n;
    Robots = {
      ...Robots,
      [n]: new Robot(R - +b + 1, +a, dir),
    };
  }

  // 명령 입력받으면서 수행
  for (let m = 1; m <= M; m++) {
    const [num, command, repeat] = input.shift().split(' ');
    switch (command) {
      case 'F':
        moveF(+num, +repeat);
        break;
      case 'L':
        turnL(+num, +repeat);
        break;
      case 'R':
        turnR(+num, +repeat);
        break;
    }

    if (fin) break;
  }

  console.log(answer);
};

main();