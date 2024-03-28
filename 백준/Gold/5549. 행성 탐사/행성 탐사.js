// Map M * N
// - J : 정글
// - O : 바다
// - I : 얼음
// 조사 대상 영역 K개
// - a b c d : (a, b) ~ (c, d)
// => 출력
//    각 영역에 J, O, I이 각각 몇개씩 있는지
// => 풀이
//    - 누적합
//    - 왼쪽끝부터 오른쪽끝까지 전부 누적합으로 각각이 몇개 있는지 구해놓고
//    - 각 K마다 O(1)로 구해서 출력
//      -> 1000 * 1000 * 4
//    - 이렇게하는 이유는, K개가 전부 (1, 1) ~ (M, N) 일 경우,
//      -> 1000 * 1000 * 100000 뷈!

let R, C;
let K;
let Map;
const dr = [-1, -1, 0, 0]; // 왼위 위 왼 나
const dc = [-1, 0, -1, 0];

const Pla = function (j, o, i) {
    this.j = j;
    this.o = o;
    this.i = i;
}

const isIn = (r, c) => {
    return 0<=r && r<R && 0<=c && c<C;
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [R, C] = input.shift().split(' ').map(Number);
    K = +input.shift();

    Map = new Array(R).fill(null).map(() => []);
    for(let r=0; r<R; r++) {
        Map[r] = input.shift().split('');
    }

    // 누적합 배열 채우기
    let sumMap = new Array(R).fill(null).map(() => new Array(C).fill());
    let [j, o, i] = [0, 0, 0];
    for(let r=0; r<R; r++) {
        for(let c=0; c<C; c++) {
            j = (isIn(r-1, c) ? sumMap[r-1][c].j : 0)
                + (isIn(r, c-1) ? sumMap[r][c-1].j : 0)
                - (isIn(r-1, c-1) ? sumMap[r-1][c-1].j : 0)
                + (Map[r][c] === 'J' ? 1 : 0);
            o = (isIn(r-1, c) ? sumMap[r-1][c].o : 0)
                + (isIn(r, c-1) ? sumMap[r][c-1].o : 0)
                - (isIn(r-1, c-1) ? sumMap[r-1][c-1].o : 0)
                + (Map[r][c] === 'O' ? 1 : 0);
            i = (isIn(r-1, c) ? sumMap[r-1][c].i : 0)
                + (isIn(r, c-1) ? sumMap[r][c-1].i : 0)
                - (isIn(r-1, c-1) ? sumMap[r-1][c-1].i : 0)
                + (Map[r][c] === 'I' ? 1 : 0);

            // sumMap[r][c].j = j;
            // sumMap[r][c].o = o;
            // sumMap[r][c].i = i;
            sumMap[r][c] = new Pla(j, o, i);
        }
    }

    // 조사대상영역 출력
    let answer = '';
    input.forEach(row => {
        const [a, b, c, d] = row.split(' ').map(el => +el - 1);

        j = sumMap[c][d].j - (isIn(a-1, d) ? sumMap[a-1][d].j : 0)
                           - (isIn(c, b-1) ? sumMap[c][b-1].j : 0)
                           + (isIn(a-1, b-1) ? sumMap[a-1][b-1].j : 0);
        o = sumMap[c][d].o - (isIn(a-1, d) ? sumMap[a-1][d].o : 0)
                           - (isIn(c, b-1) ? sumMap[c][b-1].o : 0)
                           + (isIn(a-1, b-1) ? sumMap[a-1][b-1].o : 0);
        i = sumMap[c][d].i - (isIn(a-1, d) ? sumMap[a-1][d].i : 0)
                           - (isIn(c, b-1) ? sumMap[c][b-1].i : 0)
                           + (isIn(a-1, b-1) ? sumMap[a-1][b-1].i : 0);

        answer += `${j} ${o} ${i}\n`;
    })

    console.log(answer);
}

main();