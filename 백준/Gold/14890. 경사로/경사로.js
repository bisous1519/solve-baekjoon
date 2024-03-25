let N, L;
let Map;

const check = (map) => {
  let answer = 0;

  for(let r=0; r<N; r++) {
    const row = map[r];
    let possible = 1;
    for(let c=1; c<N; c++) {
      if(row[c - 1] == row[c]) possible++;
      else if(row[c - 1] + 1 == row[c] && possible >= L) possible = 1;
      else if(row[c - 1] == row[c] + 1 && possible >= 0) possible = 1 - L;
      else {
        possible = -1;
        break;
      }
    }
      
    if(possible >= 0) answer++;
  }

  return answer;
}

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');
    
  Map = input.map(row => row.split(' ').map(Number));
  [N, L] = Map.shift();
    
  let newMap = Array.from(Array(N), () => Array(N));
  for(let r=0; r<N; r++) {
    for(let c=0; c<N; c++) {
      newMap[c][r] = Map[r][c];
    }
  }
  
  console.log(check(Map) + check(newMap));
}

main();