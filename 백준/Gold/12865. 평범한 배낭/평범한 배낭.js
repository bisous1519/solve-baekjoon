// N개의 물건
// 각 물건은 무게 W, 가치 V
// 무게 최대 K
// 넣을 수 있는 물건들 가치의 최댓값 출력

let N, K;
let Products;
let dp;

const Pro = function(w, v) {
    this.w = w;
    this.v = v;
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    [N, K] = input.shift().split(' ').map(el => +el);

    Products = input.map(row => {
        const arr = row.split(' ');
        return new Pro(+arr[0], +arr[1]);
    });
    
    // 무게 기준 오름차순 정렬
    Products.sort((a, b) => a.w - b.w);

    // dp
    dp = new Array(N+1).fill(null).map(() => new Array(K+1).fill(0));
    for(let n=1; n<=N; n++) {
        for(let k=1; k<=K; k++) {
            if(k < Products[n-1].w) {
                dp[n][k] = dp[n-1][k];
            }else if(k === Products[n-1].w) {
                dp[n][k] = Math.max(Products[n-1].v, dp[n-1][k]);
            }else {
                dp[n][k] = Math.max(dp[n-1][k], Products[n-1].v + dp[n-1][k-Products[n-1].w])
            }
        }
    }

    // console.log(dp.map(row => row.join(' ')).join('\n'))
    console.log(dp[N][K]);
}

main();