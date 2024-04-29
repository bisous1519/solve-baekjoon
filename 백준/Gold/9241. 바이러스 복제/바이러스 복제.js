let Before;
let After;

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    Before = input.shift().split('');
    After = input.shift().split('');

    // 앞에서부터 교체된 곳 찾기
    let s = 0;
    while(s < Before.length && s < After.length) {
        if(Before[s] === After[s]) s ++;
        else break;
    }
    s --;

    // 뒤에서부터 교체된 곳 찾기
    let eB = Before.length - 1;
    let eA = After.length - 1;
    while(eB > s && eA > s) {
        if(Before[eB] === After[eA]) {
            eB --;
            eA --;
        }
        else break;
    }
    eB ++;
    eA ++;

    console.log(s < eA ? eA - s - 1 : 0);
}

main();