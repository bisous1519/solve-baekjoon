// 문제 N개
// 제출 기한 T일
// 제출 기한내에 제출 못한 문제는 문제마다 정해진 벌금을 냄
// 벌금 총액이 최소가 되도록
// => 출력
    // 벌금 총액의 최솟값
    // 모든 문제를 해결할 수 있으면 0
// => 풀이
    // 그리딘지,, 디핀지,, ㅎ
    // 디피라고합니다! 배낭문제라네요! ^~^
    // 가방에 담는 문제의 벌금(가치)가 최대! 가 되도록하면 버리는 문제 벌금이 최소가 됨
        // dp[N][T] = Math.max(dp[n-1][t], 나님의 벌금 + dp[n-1][t - 나님의 일수]);

let N, T;
let Task;
let dp;

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [N, T] = input.shift().split(' ').map(Number);
    Task = input.map(row => row.split(' ').map(Number));

    // T안에 모두 풀 수 있으면 0
    let sum = 0;
    Task.forEach(row => sum += row[0]);
    if(sum <= T) {
        console.log(0);
        return;
    }

    dp = new Array(N+1).fill(null).map(() => new Array(T+1).fill(0));
    Task.forEach((row, idx) => {
        const n = idx + 1;
        const [day, fee] = row;

        for(let t=0; t<=T; t++) {
            if(t < day) dp[n][t] = dp[n-1][t];
            else dp[n][t] = Math.max(dp[n-1][t], fee + dp[n-1][t-day]);
        }
    })

    sum = 0;
    Task.forEach(row => sum += row[1]);
    console.log(sum - dp[N][T]);
}

main();