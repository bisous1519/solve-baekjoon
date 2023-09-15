// T
// 자연수 N (3 <= N <= 9)
//   1~N 오름차순으로 정렬된 수
//   +, -, 숫자이어붙이기
//   => 셋중 하나를 숫자마다 적절히 조물딱해서
//      수식의 결과가 0이 되는 모든 수식을 한줄에 하나씩 출력
// => 싹다 해보자
//    1~9 사이 공백 8개
//    -> 8개에 각각 3개씩 넣어봄(+, -, 공백)
//    => 3^8 * 테케 9개 = 6만

let T;
let N;
const [PLUS, MINUS, CONN] = ['+', '-', ' '];
const CAL = ['+', '-', ' '];
let ansArr = [];
let answer = '';

const print = (arr) => {
    let sik = '';
    arr.forEach(el => {
        sik += el;
    })

    ansArr.push(sik);
}

const connNums = (arr, idx) => {
    let num = arr[idx];
    
    // idx 번째 숫자 바로 뒤 연산자가 공백이면 합체
    while(idx <= N * 2 - 3) {
        if(arr[idx + 1] !== CONN) break;
        
        idx += 2;
        num = num * 10 + arr[idx];
    }
    
    return [num, idx + 1];
}

const makeZero = (arr, idx) => {
    if(idx === N * 2 - 1) {
        // 만든 식 계산
        let [result, i] = connNums([...arr], 0); // [첫번째 숫자, 첫번째 연산자 idx]
        while(i <= N * 2 - 3) {

            // 지금 연산자 뒤에 오는 숫자
            const [num, nexti] = connNums([...arr], i + 1);

            // 이번 연산자로 계산하기
            if(arr[i] === PLUS) result += num;
            else if(arr[i] === MINUS) result -= num;

            // 다음 연산자 idx
            i = nexti;

        }

        // 찾고있던!!
        if(result === 0) print(arr);

        return;
    }

    let newArr = [...arr];
    CAL.forEach(cal => {
        newArr[idx] = cal;
        makeZero([...newArr], idx + 2);
    })
    
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    T = +input.shift();
    input.forEach(el => {
        N = +el;
        
        // 1~N이 들어있는 배열 만들기(사이에 공백 한칸씩)
        let arr = new Array(N * 2 - 1).fill(0);
        for(let n=1, i=0; n<=N; n++) {
            arr[i] = n;
            i+=2;
        }

        // 모든 식 만들어보고 계산해서 0되는 식 찾기
        makeZero([...arr], 1);

        // 0되는 식 다 찾음
        ansArr.sort();
        answer += ansArr.join('\n') + '\n\n';
        ansArr = [];
    })

    console.log(answer);
}

main();