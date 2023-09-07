// 전깃줄 N개
//   a <-> b 로 이어진 전깃줄이 N개 주어짐
// 전깃줄이 서로 교차하지 않게 하기 위해 제거해야 할 최소한의 전깃줄 개수 출력
// 왼쪽 기준으로 정렬했을 때
//   -> 이전 전깃줄들 하나씩 살펴보면서
//   -> 이전 전깃줄의 a가 내 a 보다 작고 이전의b가 내 b보다 작아야 안겹치는!
//   -> 이 조건을 만족하는 전깃줄일 경우
//      -> 걔가 끌고온 안겹치는 전깃줄 개수에 나!를 +1해서 이번 전깃줄 안겹치는 개수에 저장
//  -> 배열을 쭉 순회하면서 가장 큰 값을 출력!

let N;
let LIS;
let lines;

const Line = function(s, e) {
    this.s = s;
    this.e = e;
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    N = +input.shift();
    lines = input.map(row => {
        const [s, e] = row.split(' ');
        return new Line(+s, +e);
    })

    // 왼쪽 오름차순 정렬
    lines.sort((a, b) => a.s - b.s);

    // 최장 증가 수열
    LIS = new Array(N).fill(0);
    for(let cur=0; cur<N; cur++) {
        LIS[cur] = 1;
        for(let pre=0; pre<cur; pre++) {
            if(lines[pre].s < lines[cur].s && lines[pre].e < lines[cur].e ) {
                if(LIS[cur] < LIS[pre] + 1) {
                    LIS[cur] = LIS[pre] + 1;
                }
            }
            // console.log(LIS);
            // console.log('--------')
        }
    }

    // 최대값 찾기
    let max = 0;
    LIS.forEach(el => {
        max = Math.max(max, el);
    })

    console.log(N - max);
}

main();