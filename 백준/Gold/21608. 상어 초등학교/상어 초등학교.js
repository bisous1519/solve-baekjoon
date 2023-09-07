// 교실 N*N
//   (r, c) : (1, 1) ~ (N, N)
//   한 칸에 한명씩 앉음
//   상하좌우로 인접한 한칸
// 학생수 N^2 (1 ~ N^2번)
//   입력에 주어진 순서대로 자리에 앉힘
//   자리에 앉는 기준 :
//     1. 인접한 칸에 좋아하는 학생이 가장 많은 칸
//     2. 인접한 칸에 비어있는 칸이 가장 많은 칸
//     3. 행 번호가 가장 작은 칸
//     4. 열 번호가 가장 작은 칸

// 학생을 전부 자리에 앉히고 -> 만족도 구해서 출력

let N;
let Map;
const dr = [-1, 0, 1, 0]; // 상우하좌
const dc = [0, 1, 0, -1];

const Loc = function(r, c, likes, blanks) {
    this.r = r;
    this.c = c;
    this.likes = likes;
    this.blanks = blanks;
}

const isIn = (r, c) => {
    return 1<=r && r<=N && 1<=c && c<=N;
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    N = +input.shift();
    Map = new Array(N+1).fill(null).map(() => new Array(N+1).fill(0));

    // 순서대로 자리에 앉힘
    let likes = new Array(N*N+1).fill(null).map(() => new Array(N*N+1).fill(false));
    input.forEach(row => {
        const [num, ...arr] = row.split(' ').map(el => +el);
        
        arr.forEach(i => {
            likes[num][i] = true;
        })

        // 맵 돌면서 좋아하는 학생수랑 비어있는 칸수 카운트한 배열만들기
        let eachSpace = [];
        for(let r=1; r<=N; r++) {
            for(let c=1; c<=N; c++) {
                if(Map[r][c] !== 0) continue; // 자리를 빼앗을 순 없어

                let cntLike = 0;
                let cntBlank = 0;
                for(let d=0; d<4; d++) {
                    const goR = r + dr[d];
                    const goC = c + dc[d];

                    if(!isIn(goR, goC)) continue;
                    if(likes[num][Map[goR][goC]]) cntLike ++;
                    if(Map[goR][goC] === 0) cntBlank ++;
                }

                eachSpace.push(new Loc(r, c, cntLike, cntBlank));
            }
        }

        // 조건에 따라 앉히기
        eachSpace.sort((a, b) => {
            if(a.likes !== b.likes) {
                return (a.likes - b.likes) * -1;
            } else if(a.blanks !== b.blanks) {
                return (a.blanks - b.blanks) * -1;
            } else if(a.r !== b.r) {
                return a.r - b.r;
            } else return a.c - b.c;
        });


        Map[eachSpace[0].r][eachSpace[0].c] = num;
        // console.log(eachSpace);
        // console.log(Map.map(row => row.join(' ')).join('\n'))
    })

    // 만족도 구하기
    let answer = 0;
    for(let r=1; r<=N; r++) {
        for(let c=1; c<=N; c++) {
            let cnt = 0;
            for(let d=0; d<4; d++) {
                const goR = r + dr[d];
                const goC = c + dc[d];
                if(!isIn(goR, goC)) continue;
                
                if(likes[Map[r][c]][Map[goR][goC]]) cnt ++;
            }

            if(cnt === 0) continue;
            answer += (Math.pow(10, cnt - 1));
        }
    }

    console.log(answer);
}

main();