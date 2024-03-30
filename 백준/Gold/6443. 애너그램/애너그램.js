// => 풀이
    // 순열
        // 입력받은 문자열을 문자로 잘라서 배열에 넣음
        // 순열을 구함 (20P20..?)
        // 구해진 순열을 Set에 넣음 (중복제거)
        // Set을 다시 배열에 넣은 후 정렬해서 출력
// ----------------
// 1트 : 시간초과
    // 애너그램 하나 다 만들어졌을 때 Set에 넣어서 중복 제거했는데,
    // 중복 제거 하는 방식에서 다른방법이나 백트래킹 필요.
// 2트
    // 각 문자열을 정렬해서 받음 (acb -> abc)
    // 문자가 쓰인 개수를 카운트한 객체를 만듦.
    // 이 객체에 저장된 숫자를 기준으로
    // -> 문자가 쓰인 개수를 감소시키며 순열을 만듦.

let N;
let word;
let wordLen;
let answer = '';

const permu = (nth, anagram, leftLetters) => {
    if(nth === wordLen) {
        answer += anagram.join('') + '\n';
        return;
    }

    for(let letter in leftLetters) {
        if(leftLetters[letter] > 0) {
            leftLetters[letter] --;
            anagram[nth] = letter;

            permu(nth + 1, anagram, leftLetters);
            leftLetters[letter] ++;
        }
    }
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    N = +input.shift();

    input.forEach(row => {
        word = row.split('').sort();
        wordLen = word.length;

        // 쓰인 알파벳 개수 카운트
        let leftLetters = {};
        word.forEach(el => {
            if(!leftLetters[el]) leftLetters[el] = 0;
            leftLetters[el] ++;
        })
        
        permu(0, new Array(wordLen), leftLetters);
    })

    console.log(answer);
}

main();