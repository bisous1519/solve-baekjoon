// => 출력
//    가장 작은 조각의 길이가 최대가 되도록
// => 풀이
    // 이분탐색
    // : 이분탐색 문제들의 포인트는,
    //   '우리가 찾고자하는 값을 올바르게 인식하고, 그 값을 정하는 기준을 알아야 하는 것!' 이라고 함..
    // 도출 과정
        // 1. 찾으려는 것은 케익조각의 길이 (가장 작은 조각이 최댓값을 갖게 하기)
        // 2. 1번 자르면 2조각, 2번 자르면 3조각, ..
        // 3. 케익조각 길이의 범위 : 1 ~ Lcm
        // 4. 임의로 가장 작은조각의 길이를 정했을 때( => mid값),
        //    즉, n등분 시 각 조각의 크기를 mid보다 작게 자를 수 없다고 할 때!
        // 5. n등분 할 수 없다면, mid를 더 작게 잡아야 함.
        // 6. n보다 더 많이 자를 수 있다면, mid가 더 커도 됨.
        // -> 이러한 과정으로 mid의 범위를 찾아가자
    // 내 알고리즘
        // 골자 : 각 N(Qi)마다 mid를 찾아감.
        //       가장 작은 조각의 길이라 mid라고 했을 때 몇 번 자를 수 있나?
        // - 처음 mid는 (0 + L)/2 로 잡고,
        //   이분탐색하면서 N번 자를 수 없다면 mid를 더 줄이고,
        //   N번보다 많이 자를 수 있다면 mid를 더 키워봄
        // - 각 mid 정해졌을 때 마다 몇 번 자를 수 있는지 확인하는 방법은,
        //   for문으로 조각을 쭉 돌면서 확인.
    // 시간복잡도
        // L을 이분탐색 : logL
        // mid 정해질 때 마다 몇 번 자를 수 있는지 확인 : M
        // 각 인원마다 반복 : N
        // => O(MNlogL) = 1000 * 1000 * 6?

let N, M, L;
let Cakes = [];
let answer = '';

const isPossible = (mid, n) => {
    let prev = 0;
    for(let m=0; m<=M; m++) {
        if(Cakes[m] - prev >= mid) {
            // 여기 자름!
            n --;
            prev = Cakes[m];
        }
    }

    return n < 0 ? true : false;
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [N, M, L] = input.shift().split(' ').map(Number);
    for(let m=0; m<M; m++) {
        Cakes.push(+input.shift());
    }
    Cakes.push(L);

    input.forEach(n => {
        // 이분탐색
        let max = 0;
        let left = 0;
        let right = L;
        while(left <= right) {
            let mid = Math.floor((left + right) / 2);

            // 이 mid값으론 n보다 더 자를 수 있음 => mid를 더 키워도 됨
            if(isPossible(mid, n)) {
                left = mid + 1;
                max = Math.max(max, mid);
            }

            // 이 mid값으론 n번도 못자름 => mid값이 더 작아야 됨
            else right = mid - 1;
        }

        answer += `${max}\n`;
    })

    console.log(answer);
}

main();