// Map N*N (2 <= N <= 100)
//   - 지역별 높이 정보 주어짐 (1 <= 높이 <= 100)
//   - 그 지역에 비가 내렸을 때 물에 잠기지 않는 안전 영역이 최대 몇개 만들어지는지?
//   - 비 양에 따라 일정 높이 이하 모든 지점은 물에 잠김
// 안전 영역
//   : 물에 잠기지 않고 상하좌우 인접한 곳!의 뭉치들
// => 출력
//    안전 영역이 제일 많을 때의 안전 영역 개수!
// => 풀이
//    높이 종류를 배열에 담아놓고
//      -> 각 높이마다
//          - 물에 잠기게 하고
//          - 안전 영역 개수 세기 -> 최댓값 갱신

let N;
let Map;
let hList = [];
let height = {}; // 각 높이마다 해당 좌표배열 저장
let isVisited;
let max = 1;
const dr = [-1, 0, 1, 0]; // 위오아왼
const dc = [0, 1, 0, -1];

const Loc = function(r, c) {
    this.r = r;
    this.c = c;
}

const isIn = (r, c) => {
    return 0<=r && r<N && 0<=c && c<N;
}

const bfs = (loc) => {
    let queue = [loc];

    while(queue.length > 0) {
        let size = queue.length;
        while(size -- > 0) {
            const cur = queue.shift();

            for(let d=0; d<4; d++) {
                const goR = cur.r + dr[d];
                const goC = cur.c + dc[d];

                if(!isIn(goR, goC)) continue;
                if(Map[goR][goC] === 0) continue;
                if(isVisited[goR][goC]) continue;

                isVisited[goR][goC] = true;
                queue.push(new Loc(goR, goC));
            }
        }
    }
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    N = +input.shift();
    Map = input.map((row, r) => row.split(' ').map((el, c) => {
        height = {
            ...height,
            [+el] : [...(height[+el] ? height[+el] : []), new Loc(r, c)],
        };

        return +el;
    }));
    
    // 객체는 순서 보장이 안됨
    // -> .keys로 키값 배열을 뽑으면 지금 키가 숫자기 때문에 오름차순 정렬됨
    hList = Object.keys(height);
    hList.forEach((h, nth) => {
        // 높이 h 인 곳 침수 (낮은곳부터)
        height[h].forEach(loc => {
            Map[loc.r][loc.c] = 0;
        })

        // 안전영역 개수 구하기
        let safeCnt = 0;
        isVisited = new Array(N).fill(null).map(() => new Array(N).fill(false));
        for(let r=0; r<N; r++) {
            for(let c=0; c<N; c++) {
                if(Map[r][c] !== 0 && !isVisited[r][c]) {
                    bfs(new Loc(r, c));
                    safeCnt ++;
                }
            }
        }

        // 최대개수 갱신
        max = Math.max(max, safeCnt);
    })

    console.log(max);
}

main();