// 차례로 돌아가며 게임
    // 선 : 홀수번째
    // 후 : 짝수번째
    // 매 차례 : 두 점을 선택해 연결 (이전에 그은걸 또그으면 안되고, 교차는 가능)
    // 처음으로 사이클 완성하는 순간 종료
// => 출력
    // 몇 번째 차례에서 완성됐는지 또는 아직 진행중인지 판단
// => 풀이
    // 진행되는 차례 M이 1,000,000이라 매번 차례가 추가될 때 마다 확인하는 완탐은 안됨
    // 연결하려는데 루트가 같으면 사이클!
    // -> 유니온파인드

let N, M;
let Parents;
let isFin = false;

const find = (a) => {
    if(Parents[a] === a) return a;
    else return Parents[a] = find(Parents[a]);
}

const union = (a, b) => {
    const pA = find(a);
    const pB = find(b);
    if(pA <= pB) Parents[pB] = pA;
    else Parents[pA] = pB;
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    const [N, M] = input.shift().split(' ').map(Number);
    
    Parents = new Array(N).fill(null).map((_, idx) => idx);
    input.some((row, nth) => {
        const [a, b] = row.split(' ').map(Number);

        if(find(a) === find(b)) {
            console.log(nth + 1);
            isFin = true;
            return true;
        }

        union(a, b);
    })

    if(!isFin) console.log(0);
}

main();