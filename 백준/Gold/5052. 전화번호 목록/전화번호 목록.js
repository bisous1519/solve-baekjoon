// 전화번호 목록
    // 일관성 있음 : 한 번호가 다른 번호의 접두어인 경우가 없는 경우
// => 출력
    // 목록이 일관성이 있는지 없는지 말해 YES / NO
// => 풀이
    // n이 만개여서 한번에 쭉 보면서 O(N)으로 해결해야함
    // O(N) 까진 안될거같고, 정렬한 후에 각 번호마다 이분탐색,,? ㄴㄴ..
// -------------
// 아이디어 생각하는게 힘들어서 구선생 참고했는데
// 이 문제 풀이 포인트는, 접두어!만 신경쓰면 된다는 것인거같다
// 어느 한 문자열이 다른 문자열의 중간 어딘가에 포함되는지는 신경안써도 되는것!
// => 정렬한 후, 현재 문자열이 다음 문자열의 접두어로 포함되는지만 확인.
//    n이 만개, 전화번호는 길어야 10자리고, 테케는 50개라 가능.
//    그리고, 현재 문자열이 다음 문자열 길이보다 길면 그냥 패스! 하면 시간 더 줄일 수 있음

let T;
let N;
let Tel;
let answer = '';

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    T = +input.shift();

    for(let t=0; t<input.length;) {
        N = +input[t];

        // 전화번호 입력받고
        Tel = new Array(N);
        for(let n=t+1; n<t+1+N; n++) {
            Tel[n-1] = input[n];
        }
        t += N + 1;

        // 소트
        // Tel.sort((a, b) => a - b); // 문자열이 숫자형으로 암시적 형변환 후에 정렬함
        Tel.sort((a, b) => a.localeCompare(b)); // 문자열을 사전순으로 정렬할 때

        // 일관성 여부 검사
        let result = 'YES';
        for(let n=0; n<N-1; n++) {
            let prev = Tel[n];
            let next = Tel[n+1];
            
            if(Tel[n].length > Tel[n+1].length) continue;
            
            if(next.startsWith(prev)) {
            // if(prev === next.slice(0, prev.length)) {
                result = 'NO';
                break;
            }
        }

        answer += result + '\n';
    }

    console.log(answer);
}

main();