// 두 사람 사이의 경로를 보여줌 (경로가 없는경우는 안보여줌)
// => 출력
//    두 사람 사이의 경로가 존재하는지 여부
//    (있음 : 1, 없음 : 0)
// => 풀이
//    - 유니온파인드

let T;
let N, K, M;
let Parents;
let answer = '';

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

    T = +input.shift();
    for(let t=1, inputIdx=0; t<=T; t++) {
        N = +input[inputIdx ++];
        K = +input[inputIdx ++];

        // 유니온파인드 : 연결된 사이면 부모 같게 만들어주는 작업
        Parents = new Array(N).fill(null).map((_, idx) => idx);
        for(let k=0; k<K; k++) {
            const [a, b] = input[inputIdx + k].split(' ').map(Number);
            union(a, b);
        }

        // 연결된 사인지 여부 출력
        inputIdx += K;
        M = +input[inputIdx ++];
        answer += `Scenario ${t}:\n`;
        for(let m=0; m<M; m++) {
            const [a, b] = input[inputIdx + m].split(' ').map(Number);
            answer += find(a) === find(b) ? '1\n' : '0\n';
        }

        inputIdx += M;
        answer += '\n';
    }

    console.log(answer);
}

main();