const input = require('fs')
  .readFileSync('/dev/stdin')
  .toString()
  .trim()
  .split('\n');
let [N, M, X, Y, K] = input[0].split(' ').map((value) => parseInt(value));

input.shift();
let Map = [];
for (let i = 0; i < N; i++) {
  Map.push([...input[0].split(' ').map((value) => parseInt(value))]);
  input.shift();
}
// 입력

// 방향
const [EAST, WEST, NORTH, SOUTH] = [1, 2, 3, 4];
// 주사위
let [top, back, front, bottom, left, right] = [0, 0, 0, 0, 0, 0];

const rollEast = () => {
  if (Y >= M - 1) return false;
  Y++;
  let temp = top;
  top = left;
  left = bottom;
  bottom = right;
  right = temp;
  return true;
};
const rollWest = () => {
  if (Y <= 0) return false;
  Y--;
  let temp = top;
  top = right;
  right = bottom;
  bottom = left;
  left = temp;
  return true;
};
const rollNorth = () => {
  if (X <= 0) return false;
  X--;
  let temp = top;
  top = front;
  front = bottom;
  bottom = back;
  back = temp;
  return true;
};
const rollSouth = () => {
  if (X >= N - 1) return false;
  X++;
  let temp = top;
  top = back;
  back = bottom;
  bottom = front;
  front = temp;
  return true;
};

// 굴려보자
input[0].split(' ').map((dir, index) => {
  let tag;
  switch (parseInt(dir)) {
    case EAST:
      tag = rollEast();
      break;
    case WEST:
      tag = rollWest();
      break;
    case NORTH:
      tag = rollNorth();
      break;
    case SOUTH:
      tag = rollSouth();
      break;
  }

  // 제대로 구름
  if (tag) {
    console.log(top);
    if (Map[X][Y] === 0) {
      Map[X][Y] = bottom;
    } else {
      bottom = Map[X][Y];
      Map[X][Y] = 0;
    }
  }
});