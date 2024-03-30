let DP;
let S;
let N;
let Words;

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    S = input.shift().split('');
    N = +input.shift();
    Words = input.map(row => row.split(''));

    DP = new Array(S.length + 1).fill(false);
    for(let s=0; s<S.length; s++) {
        if(!(DP[s] || s === 0)) continue;

        Words.forEach(word => {
            if(s + word.length <= S.length) {
                let isImpossible = word.some((el, idx) => {
                    if(el !== S[s+idx]) return true;
                })
    
                if(!isImpossible) DP[s + word.length] = true;
            }
            
        })
    }
    
    console.log(DP[S.length] ? 1 : 0);
}

main();