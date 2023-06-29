let N;
let Nums;
let Min = Number.MAX_SAFE_INTEGER;
let Max = Number.MIN_SAFE_INTEGER;

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');
    N = input[0];
    input.shift();
    Nums = input[0].split(' ');
    
    Nums.map(num => {
        Min = Math.min(Min, num);
    })
    Nums.map(num => {
        Max = Math.max(Max, num);
    })
    
    console.log(Min, Max);
}

main();