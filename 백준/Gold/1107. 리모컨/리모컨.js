let N, M;
let nums;

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    N = +input.shift();
    M = +input.shift();
    nums = input.shift();

    const brokens = nums
    ? nums
        .split(' ')
        .reduce((acc, v) => {
        acc[v] = true;
        return acc;
        }, {})
    : {};

    let cnt = Math.abs(100 - N);
    for (let i = 0; i < 1000000; i++) {
        const numStr = i.toString();
        let isValid = true;
        for (let j = 0; j < numStr.length; j++) {
            if (brokens[numStr[j]]) {
                isValid = false;
                break;
            }
        }
        if (isValid) {
            cnt = Math.min(cnt, Math.abs(i - N) + numStr.length);
        }
    }

    console.log(cnt);
}

main();