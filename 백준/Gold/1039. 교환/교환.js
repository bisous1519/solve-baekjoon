//남의코드..
const fs = require("fs");
const stdin = (
  process.platform === "linux"
    ? fs.readFileSync("/dev/stdin").toString()
    : `100 1`
).split("\n");

const input = (() => {
  let line = 0;
  return () => stdin[line++];
})();

let data = input().trim().split(" ");
const [N, K] = [data[0].split(""), +data[1]];

// 기본 값은 -1, 한번도 바뀌지 않는경우 -1을 그대로 출력하면 끝이라.
let max = -1;

// 1,000,000 보다 작거나 같은 자연수 이므로 1000000+1을 해줬다.
let visited = new Array(K + 1)
  .fill(null)
  .map((_) => new Array(1000000 + 1).fill(false));

const dfs = (count = 0) => {
  // sum에 N배열을 모두 합쳐 숫자로 바꿔준다.
  const sum = +N.join("");
  
  // 방문을 했다면 탈출!
  if (visited[count][sum] === true) {
    return;
  }
  
  // 만약 count(깊이) === K 가 같다면 탈출!!
  if (count === K) {
    // sum이 크면 max를 바꾼다.
    if (sum > max) {
      max = sum;
    }
    return;
  }
  
  // 경우의 수 구하기 
  // [a,b,c] 가 있다면 [[a,b], [a,c], [b,c]] 가 된다.
  for (let i = 0; i < N.length - 1; i++) {
    for (let j = i + 1; j < N.length; j++) {
      swap(i, j);
      // 만약 첫번째 숫자가 0이 아니라면 dfs를 한번 더!!
      // 0일 경우는 안된다고 문제에 쓰여있음.
      if (N[0] !== "0") {
        dfs(count + 1);
      }
      swap(i, j);
    }
  }
  // visited[count(깊이)][sum(총합)] 위치에 방문 했다고 체크
  visited[count][sum] = true;
};

// swap은 파이썬처럼 자바스크립트도 편하게 가능함.
const swap = (i, j) => {
  [N[i], N[j]] = [N[j], N[i]];
};

dfs();
console.log(max);