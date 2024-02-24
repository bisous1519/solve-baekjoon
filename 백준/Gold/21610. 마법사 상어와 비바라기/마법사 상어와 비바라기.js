// Map N*N
//   - (1, 1) ~ (N, N)
//   - 각칸에 저장할 수 있는 물의 양 제한 x
//   - A[r][c] : (r, c)에 저장된 물의 양
// 맵의 각 끝은 연결됨
//   - N행 아래 -> 1행,
//     1행 위 -> N행
//     N열 오른쪽 -> 1열
//     1열 왼쪽 -> N열
// 비바라기 시전
//   - (N, 1), (N, 2), (N-1, 1), (N-1, 2)에 비구름이 생김
// 이동 명령
//   - 방향 d, 거리 s
//   - 방향 : 8방 (1~8 : ←, ↖, ↑, ↗, →, ↘, ↓, ↙ )
//   - 왼쪽 아래 4칸에 초기 구름이 생기고 나서
//     - 각 명령마다 : 1번~5번 과정이 순서대로 진행됨
//     - 1. 구름이 d, s에 따라 이동
//     - 2. 이동한 위치 물이 1씩 증가
//     - 3. 4칸 각각에서 -> 자기 기준 대각선 네개 중 물이 1이상인 곳 개수만큼 물 증가
//     - 4. 이전 구름 다 사라지고
//          -> Map 모든 칸 돌면서 물양이 2이상이고, 2번에서 구름있던 위치가 아닌칸에 새로운 구름이 생김
//     - 5. 새로운 구름 자리의 물들이 2감소
// => 출력
//    M번의 이동이 모두 끝난 후, 바구니에 들어있는 물 양의 합


let N, M;
let Map = [];
let cloud = [];
let isCloud;
const dr = [0, 0, -1, -1, -1, 0, 1, 1, 1]; // 0 좌 좌상 상 상우 우 우하 하 하좌
const dc = [0, -1, -1, 0, 1, 1, 1, 0, -1];
const crossR = [-1, -1, 1, 1]; // 좌상 우상 좌하 우하
const crossC = [-1, 1, -1, 1];

const Loc = function(r, c) {
    this.r = r;
    this.c = c;
}

const isIn = (r, c) => {
    return 0<=r && r<N && 0<=c && c<N;
}

const move = (d, s) => {
    cloud.forEach((loc, idx) => {
        let r = loc.r;
        let c = loc.c;

        for(let i=0; i<s; i++) {
            r += dr[d];
            c += dc[d];
            
            if(r < 0) r = N - 1;
            else if(r === N) r = 0;

            if(c < 0) c = N - 1;
            else if(c === N) c = 0;
        }

        cloud[idx] = new Loc(r, c);
    })
}

const bug = () => {
    cloud.forEach(loc => {
        let cnt = 0;
        
        for(let d=0; d<4; d++) {
            const goR = loc.r + crossR[d];
            const goC = loc.c + crossC[d];

            if(!isIn(goR, goC)) continue;
            
            if(Map[goR][goC] > 0) cnt++;
        }

        Map[loc.r][loc.c] += cnt;
    })
}

const newCloud = () => {
    let newC = [];
    
    // console.log('이전상태')
    isCloud = new Array(N).fill(null).map(() => new Array(N).fill(false));
    cloud.forEach(loc => isCloud[loc.r][loc.c] = true)
    // console.log(isCloud.map(row => row.map(el => el ? 'T' : 'F')))
    
    Map.forEach((row, r) => row.forEach((water, c) => {
        // 구름이 아니었고 2이상인 곳
        if(!isCloud[r][c] && water >= 2) {
            newC.push(new Loc(r, c));
        }
    }))
    cloud = [...newC];

    // isCloud = new Array(N).fill(null).map(() => new Array(N).fill(false));
    // newC.forEach(loc => isCloud[loc.r][loc.c] = true)
    // console.log('새로운 구름 상태')
    // console.log(isCloud.map(row => row.map(el => el ? 'T' : 'F')))
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [N, M] = input.shift().split(' ').map(Number);
    for(let n=0; n<N; n++) {
        Map.push(input.shift().split(' ').map(Number));
    }

    // 초기 구름 위치
    cloud.push(new Loc(N-2, 0)); // 좌상
    cloud.push(new Loc(N-2, 1)); // 우상
    cloud.push(new Loc(N-1, 0)); // 좌하
    cloud.push(new Loc(N-1, 1)); // 우하
    isCloud = new Array(N).fill(null).map(() => new Array(N).fill(false));
    cloud.forEach(loc => isCloud[loc.r][loc.c] = true)

    // 이동 명령 하나씩 실행
    input.forEach((row, nth) => {
        const [d, s] = row.split(' ').map(Number);
        // console.log(nth+1, '-----------');
        
        // 1. 이동
        move(d, s % N);
        
        // 2. 이동한 위치에 물 1씩 증가
        cloud.forEach(loc => Map[loc.r][loc.c]++)

        // 3. 물복사버그 마법 (대각선)
        bug();
        
        // 4. 새로운 구름
        newCloud();
        
        // 5. 새로운 구름자리에 물 2감소
        cloud.forEach(loc => Map[loc.r][loc.c] -= 2)

        // console.log(nth+1, '-----------');
        // console.log(Map.map(row => row.join(' ')).join('\n'))

    })

    // 물의 합
    let sum = 0;
    Map.forEach(row => row.forEach(water =>  sum += water))

    console.log(sum);
}

main();