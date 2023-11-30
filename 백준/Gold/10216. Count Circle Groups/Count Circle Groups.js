let T;
let N;
let Enemy;
let parent;
let answer = '';

const Info = function(x, y, r) {
  this.x = x;
  this.y = y;
  this.r = r;
}

const find = (i) => {
  if(i === parent[i]) return i;
  return parent[i] = find(parent[i]);
}

const union = (i, j) => {
  const iParent = find(i);
  const jParent = find(j);

  if(iParent !== jParent) {
    if(iParent < jParent) {
      parent[jParent] = iParent;
    }
    else parent[iParent] = jParent;
  }
  
}

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

  T = +input.shift();
  let inputIdx = 0;
  for(let t=0; t<T; t++) {
    N = +input[inputIdx++];

    // 입력
    Enemy = [];
    for(let n=0; n<N; n++) {
      const [x, y, r] = input[inputIdx++].split(' ');
      Enemy.push(new Info(+x, +y, +r));
    }
    
    // 유니온파인드
    parent = new Array(N).fill(null).map((el, idx) => idx);
    let groups = N;
    for(let i=0; i<N; i++) {
      for(let j=i+1; j<N; j++) {
        let xDiff = Enemy[i].x - Enemy[j].x;
        let yDiff = Enemy[i].y - Enemy[j].y;
        let r = Enemy[i].r + Enemy[j].r;

        // 같은 그룹!
        if(Math.pow(xDiff, 2) + Math.pow(yDiff, 2) <= Math.pow(r, 2)) {
          if(find(i) !== find(j)) {
            union(i, j);
            groups --;
          }
          else {
            // 이미 같은그룹
          }
        }
      }
    }

    answer += groups + '\n';
  }
  
  console.log(answer);
}

main();