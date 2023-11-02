// 거리 D
// 지름길
//   - 일방통행
// => 출력
//    운전해야하는 거리 최솟값
// => 풀이
//    입력되는 각 위치를 노드로 봄
//    0, D 를 포함해서 입력받은 위치(지름길 시작, 끝)들 배열에 넣고
//      -> sort한다음에 filter 돌려서 노드를 뽑음
//    각 노드에서 갈 수 있는 노드 위치랑 거리 계산해서 리스트 만듦
//    리스트 bfs돌림

let N, D;

const Node = function(loc, dis) {
  this.loc = loc;
  this.dis = dis;
}

const main = () => {
  const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

  [N, D] = input.shift().split(' ').map(el => +el);

  // 연결리스트 만들기
  let list = new Array(D + 1).fill(null).map(() => []);
  input.forEach(row => {
    const [s, e, cost] = row.split(' ').map(el => +el);

    if(s <= D && e <= D && e - s > cost) {
      list[s].push(new Node(e, cost));
    }
  })
  // console.log(list);

  // 최소 거리 갱신
  let dis = new Array(D + 1).fill(Number.MAX_SAFE_INTEGER);
  dis[0] = 0;
  for(let curloc = 0; curloc <= D; curloc ++) {
    if(curloc !== 0) {
      dis[curloc] = Math.min(dis[curloc], dis[curloc - 1] + 1);
    }

    list[curloc].forEach(node => {
      if(dis[node.loc] > dis[curloc] + node.dis) {
        dis[node.loc] = dis[curloc] + node.dis;
      }
    })
  }

  console.log(dis[D]);
}

main();