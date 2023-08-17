let N;
let prime = [];

const moveLeft = (left, sum) => {
  if (left !== -1 && left + 1 <= prime.length - 1) {
    return [left + 1, sum - prime[left]];
  } else return [-1, sum];
};

const moveRight = (right, sum) => {
  if (right !== -1 && right + 1 <= prime.length - 1) {
    return [right + 1, sum + prime[right + 1]];
  } else return [-1, sum];
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim();
  N = +input;

  // 에라토스테네스의 체
  let chkPrime = new Array(N + 1).fill(true);
  chkPrime[0] = false;
  chkPrime[1] = false;
  //   for (let n = 2; n <= N; n++) {
  //     for (let i = n + 1; i <= N; i++) {
  //       if (i % n === 0) chkPrime[i] = false;
  //     }
  //   }
  for (let i = 2; i * i <= N; i++) {
    if (!chkPrime[i]) continue;
    for (let j = i * i; j <= N; j += i) {
      chkPrime[j] = false;
    }
  }

  // 소수 배열
  chkPrime.forEach((isTrue, i) => {
    if (isTrue) prime.push(i);
  });

  // 연속된 소수의 합으로 나타낼 수 있는 경우의 수
  //   let left = 0;
  //   let right = 0;
  //   let sum = prime[0];
  //   let count = 0;
  //   while (true) {
  //     if (sum === N) {
  //       count++;
  //       [right, sum] = moveRight(right, sum);
  //       [left, sum] = moveLeft(left, sum);
  //     } else if (sum > N) {
  //       [left, sum] = moveLeft(left, sum);
  //     } else {
  //       [right, sum] = moveRight(right, sum);
  //     }

  //     if (left === -1 || right === -1) {
  //       break;
  //     }
  //   }

  let left = (sum = count = 0);
  for (let right = 0; right < prime.length; right++) {
    sum += prime[right];
    while (sum > N) {
      sum -= prime[left];
      left++;
    }
    if (sum === N) count++;
  }

  console.log(count);
};

main();