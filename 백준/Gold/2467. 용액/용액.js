// N개 용액 (2 <= N <= 10만)
//   두개 합쳐서 가장 0에 가까운 두 용액 찾기
// 투포인터
//   양쪽끝에 포인터 두고
//   왼쪽포인터 움직였을 때 합이랑 오른쪽포인터 움직였을 때 합 비교해서
//   절댓값이 더 작은쪽으로 포인터 움직임

let N;
let Nums;

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    N = +input.shift();
    Nums = input.shift().split(' ').map(el => +el);

    let l = 0;
    let r = N - 1;
    let numL = Nums[l];
    let numR = Nums[r];
    let min = Math.abs(numL + numR);
    while(l < r - 1) {
        const moveL = Math.abs(Nums[l + 1] + Nums[r]);
        const moveR = Math.abs(Nums[l] + Nums[r - 1]);
        if(moveL <= moveR) {
            l++;
            if(moveL < min) {
                min = moveL;
                numL = Nums[l];
                numR = Nums[r];
            }
        }else {
            r--;
            if(moveR < min) {
                min = moveR;
                numL = Nums[l];
                numR = Nums[r];
            }
        }
    }

    console.log(`${numL} ${numR}`);
}

main();