// => 출력
    // 번호 순서대로 배치하기 위해 옮기는 아이 최소 수
// => 풀이
    // 가장 긴 증가하는 부분수열..!
        // 3752614 에서 356이 가장 긴 증가부분수열임
        // 길이 7 - 가장긴 증가부분수열 3 = 4 가됨,,
        // 가장 긴 증가하는 부분수열을 놓고 나머지 수들을 끼워맞추는게
        // 가장 최소로 아이를 옮겨서 정렬하는 방법이구나 ^~^ ㅜ
    // 이것을 DP로 푼다고 합니다. 왜
/*
3 7 5 2 6 1 4
1 2 2 1 3 1 2
*/

let N;
let Students;
let arr;

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    N = +input.shift();
    Students = input.map(Number);

    arr = new Array(N).fill(0);
    arr[0] = 1;
    for(let n=1; n<N; n++) {
        let num = Students[n];
        let max = 0;
        for(let i=n-1; i>=0; i--) {
            if(Students[i] < num) {
                max = Math.max(max, arr[i]);
            }
        }

        if(max === 0) arr[n] = 1;
        else arr[n] = max + 1;
    }

    console.log(N - Math.max(...arr));
}

main();