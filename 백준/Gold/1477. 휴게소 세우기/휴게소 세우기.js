// 휴게소 N개 (<=50)
// M개 더 (<=100)
// -> 휴게소가 없는 구간의 길이의 최댓값을 최소로
//    - 이미 휴게소 있는 곳에 또 세울 수 없음
//    - 고속도로 끝에 세울 수 없음
//    - 정수 위치에만 세움
// 모든 휴게소 방문
// => 출력
//    M개의 휴게소를 짓고 난 후, 휴게소 없는 구간의 최댓값의 최솟값 출력
// => 풀이
//    휴게소 간의 간격을 이분탐색으로 줄이거나 늘려가면서
//    각 간격으로 휴게소를 추가 설치했을 때 M개 설치 가능하면 간격 최솟값 갱신!

let N, M, L;
let Points = [];
let pLen;
let min = Number.MAX_SAFE_INTEGER;

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [N, M, L] = input.shift().split(' ').map(el => +el);

    if(N !== 0) Points = input.shift().split(' ').map(el => +el);
    
    Points.push(0);
    Points.push(L);
    Points.sort((a, b) => a - b);
    let pLen = Points.length;

    let left = 0;
    let right = L - 1;
    while(left <= right) {
        const mid = Math.floor((left + right) / 2);

        // mid의 간격씩 휴게소 설치하면 총 몇개 추가설치 가능?
        let sum = 0;
        for(let i=1; i<pLen; i++) {
            const dif = Points[i] - Points[i-1];
            const cnt = Math.floor(dif / mid);

            if(cnt > 0) {
                if(dif % mid === 0) sum += cnt - 1;
                else sum += cnt;
            }
        }

        // 간격 더 넓혀서 설치해야함
        if(sum > M) left = mid + 1;

        // 간격 더 좁혀서 설치해야 함
        else {
            // 간격 최솟값을 찾아야 되니까 더 좁은 간격으로 가능한지 봐야함
            right = mid - 1;

            min = Math.min(min, mid);
        }
    }

    console.log(min);
}

main();