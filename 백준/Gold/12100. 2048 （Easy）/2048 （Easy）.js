let N;
let Max = 0;

const getSumMap = (arr) => {
  const result = [];

  for (let i = 0; i < arr.length - 1; i++) {
    if (arr[i] === 0) continue;

    if (arr[i] === arr[i + 1]) {
      result.push(arr[i] * 2);
      arr[i + 1] = 0;
    } else {
      result.push(arr[i]);
    }
  }
  if (arr[arr.length - 1] !== 0) result.push(arr[arr.length - 1]);

  return result;
};

const pushLeft = (array) => {
  for (let i = 0; i < N; i++) {
    const arr = [];

    for (let j = 0; j < N; j++) {
      if (array[i][j] !== 0) {
        arr.push(array[i][j]);
        array[i][j] = 0;
      }
    }
    if (arr.length > 0) {
      const result = getSumMap(arr);

      for (let j = 0; j < result.length; j++) {
        array[i][j] = result[j];
      }
    }
  }
  return array;
};

const pushRight = (array) => {
  for (let i = 0; i < N; i++) {
    const arr = [];

    for (let j = N - 1; j >= 0; j--) {
      if (array[i][j] !== 0) {
        arr.push(array[i][j]);
        array[i][j] = 0;
      }
    }
    if (arr.length > 0) {
      const result = getSumMap(arr);

      for (let j = 0; j < result.length; j++) {
        array[i][N - 1 - j] = result[j];
      }
    }
  }
  return array;
};

const pushUp = (array) => {
  for (let j = 0; j < N; j++) {
    const arr = [];

    for (let i = 0; i < N; i++) {
      if (array[i][j] !== 0) {
        arr.push(array[i][j]);
        array[i][j] = 0;
      }
    }
    if (arr.length > 0) {
      const result = getSumMap(arr);

      for (let i = 0; i < result.length; i++) {
        array[i][j] = result[i];
      }
    }
  }
  return array;
};

const pushDown = (array) => {
  for (let j = 0; j < N; j++) {
    const arr = [];

    for (let i = N - 1; i >= 0; i--) {
      if (array[i][j] !== 0) {
        arr.push(array[i][j]);
        array[i][j] = 0;
      }
    }
    if (arr.length > 0) {
      const result = getSumMap(arr);

      for (let i = 0; i < result.length; i++) {
        array[N - 1 - i][j] = result[i];
      }
    }
  }
  return array;
};

const copyMap = (array) => {
  let arr = [];

  array.forEach((v) => {
    arr.push([...v]);
  });
  return arr;
};

const moveBlock = (map, cnt) => {
  if (cnt === 0) {
    map.forEach((v) => {
      Max = Math.max(Max, ...v);
    });
    return;
  }

  let arr = copyMap(map);
  arr = pushLeft(arr);
  moveBlock(arr, cnt - 1);

  arr = copyMap(map);
  arr = pushRight(arr);
  moveBlock(arr, cnt - 1);

  arr = copyMap(map);
  arr = pushUp(arr);
  moveBlock(arr, cnt - 1);

  arr = copyMap(map);
  arr = pushDown(arr);
  moveBlock(arr, cnt - 1);
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
  const Map = [...input.map((row) => [...row.split(' ').map((el) => +el)])];

  // 최대 5번 이동
  moveBlock(Map, 5);

  console.log(Max);
};

main();