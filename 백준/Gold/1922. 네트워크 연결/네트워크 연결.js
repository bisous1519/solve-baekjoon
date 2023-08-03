// 참고코드
// 다시 공부해야해
class DisjointSet {
  constructor(n) {
    this.parent = Array.from({ length: n + 1 }, (_, i) => i);
  }

  union(n1, n2) {
    const rootA = this.find(n1);
    const rootB = this.find(n2);
    if (rootA < rootB) this.parent[rootB] = rootA;
    else this.parent[rootA] = rootB;
  }

  find(node) {
    if (this.parent[node] === node) return node;
    this.parent[node] = this.find(this.parent[node]);
    return this.parent[node];
  }

  connected(n1, n2) {
    if (this.find(n1) != this.find(n2)) return false;
    else return true;
  }
}

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');

  const N = +input[0];
  const M = +input[1];
  const costs = input.slice(2).map((v) => v.split(' ').map(Number));
  const COST = 2;
  costs.sort((a, b) => a[COST] - b[COST]);
  const disjointSet = new DisjointSet(N);
  let answer = 0;

  for (let i = 0; i < costs.length; i++) {
    const [from, to, cost] = costs[i];
    if (!disjointSet.connected(from, to)) {
      disjointSet.union(from, to);
      answer += cost;
    }
  }
  console.log(answer);
};

main();