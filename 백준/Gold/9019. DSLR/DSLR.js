// 계산기
    // 레지스터는 0 이상 10,000 미만의 네자리수를 저장
    // 네자리수 => d1, d2, d3, d4
    // 명령어 D, S, L, R
// => 출력
    // 서로 다른 두 정수 A, B(A!==B) 에 대해 A를 B로 바꾸는 최소한의 명령어 생성
// => 풀이
    // bfs
    // 주의 : 각 연산 적용시 0100 -> 100, 0001 -> 1 이런식임
    //       => 그냥 문자열로 푸는게 낫겟다! 필요할때 숫자로 변환하고 다시 문자열로 ㄱ
    // 주의2 : 입력된 A나 B가 1 이면 -> 0001 로 바꾸고 시작해야함
// ----------------
// 1트 : bfs 돌면서 이미 만들어졌던 수가 만들어졌을 경우에 대한 방문배열을 안만들어줬음 -> 메모리초과

let T;
let A, B;
let answer = '';

const makeStr = (a) => {
    let temp = '';
    for(let i=0; i<4-a.length; i++) {
        temp += '0';
    }
    return temp + a;
}

const D = (a) => {
    let temp = (+a * 2) % 10000;
    return makeStr(temp + ''); // 문자열로 만들고 앞자리 0으로 채우기
}

const S = (a) => {
    if(+a === 0) return '9999';
    return makeStr((+a - 1) + '');
}

const L = (a) => {
    let temp = a.slice(1) + a.charAt(0);
    return makeStr(temp);
}

const R = (a) => {
    let temp = a.charAt(3) + a.slice(0, 3);
    return makeStr(temp);
}

const bfs = () => {
    let queue = [[A, '']];
    let isVisited = new Array(10000).fill(false);
    isVisited[+A] = true;

    let qIdx = 0;
    let newA = '';
    while(qIdx < queue.length) {
        let size = queue.length - qIdx;
        while(size -- > 0) {
            let [a, order] = queue[qIdx ++];

            if(a === B) {
                answer += order + '\n';
                return;
            }

            newA = D(a);
            if(!isVisited[+newA]) {
                isVisited[+newA] = true;
                queue.push([newA, order + 'D']);
            }

            newA = S(a);
            if(!isVisited[+newA]) {
                isVisited[+newA] = true;
                queue.push([newA, order + 'S']);
            }

            newA = L(a);
            if(!isVisited[+newA]) {
                isVisited[+newA] = true;
                queue.push([newA, order + 'L']);
            }

            newA = R(a);
            if(!isVisited[+newA]) {
                isVisited[+newA] = true;
                queue.push([newA, order + 'R']);
            }
        }
    }
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    T = input.shift();

    input.forEach(row => {
        [A, B] = row.split(' ');

        A = makeStr(A);
        B = makeStr(B);
        bfs(A, '');
    })

    console.log(answer);
}

main();