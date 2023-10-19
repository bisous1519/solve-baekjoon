// 학생 N명 (1 <= N <= 10,000)
// 학생 i에게 Ai만큼 비용 지불 -> i와 1달간 친구가 됨 (1 <= Ai <= 10,000)
//   (단, 친구의 친구도 친구임) 
// 친구 관계 수 M (0 <= M <= 10,000)
// k원 있음 (1 <= k <= 10,000,000)
// 출력
// => 모든 사람과 친구가 되는 최소 비용 출력
//    (다 사귈 수 없으면 oh no 출력)
// 풀이
// => union-find!
//    집합이 몇개가 만들어지는지 보면 됨
//    각 집합의 최소비용 찾아서 비용끼리 더했을 때 k 이하이면 가능
//    각 집합 최소비용 찾는거는 : union할 때 친구비가 더 적은애를 부모로 설정
// Union-find
// => 일단 각 노드는 다 자기자신이 부모임
//    몇 개의 연결 관계를 입력받으면서 a-b가 연결관계면 두개를 union해줌
//    union : a와 b 각각의 루트를 find해줌 => aRoot, bRoot
//            루트가 같으면 걍 return false;
//            루트가 다르면 :
//            aRoot와 bRoot의 친구비 중 작은쪽을 부모로.
//            (aRoot 친구비 < bRoot 친구비 였다면,)
//               -> parents[bRoot] = aRoot 로 바꿔줌
//    find : 자기자신(a)이 루트면 return a;
//           아니면 :
//           parents[a]의 루트를 다시 find함 (재귀)

let N;
let M;
let k;
let cost;
let parents;

const find = (a) => {
    if(parents[a] === a) return a;
    parents[a] = find(parents[a]);
    return parents[a];
}

const union = (a, b) => {
    const aRoot = find(a);
    const bRoot = find(b);
    
    if(aRoot === bRoot) return false;
    else {
        if(cost[aRoot] < cost[bRoot]) {
            // bRoot 친구비가 더 적음
            parents[bRoot] = aRoot;
        }else {
            // aRoot 친구비가 더 적음
            parents[aRoot] = bRoot;
        }
    }
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    [N, M, k] = input.shift().split(' ').map(el => +el);
    cost = input.shift().split(' ').map(el => +el);
    cost.unshift(0);

    // 일단 자기자신이 부모
    parents = new Array(N+1).fill(null).map((_,idx) => idx);
    
    // 친구관계 입력
    input.forEach(row => {
        const [a, b] = row.split(' ').map(el => +el);
        union(a, b);
    })

    // 필요한 친구비 구하기
    let sum = 0;
    for(let i = 1; i <= N; i++) {
        if(parents[i] === i) {
            sum += cost[parents[i]];
        }
    }

    if(sum <= k) console.log(sum);
    else console.log('Oh no');
}

main();