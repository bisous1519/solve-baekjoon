// 미친아두이노 피해다니기
// Map R*C
    // . : 빈칸
    // R : 미친아두이노
    // I : 종수의 위치
// 5가지 과정 반복 (종수 아두이노 : I, 미친 아두이노 : R)
    // 1. I를 8방으로 이동시키거나, 그대로 놔둠 (order 대로 움직임)
    // 2. I가 R 자리로 이동하면 게임 끝. 짐.
    // 3. R은 8방 중 I와 가장 가까워지는 방향(|r1-r2| + |s1-s2|가 가장 작은)으로 한 칸 이동.
    // 4. R이 I 자리로 이동하면 게임 끝. 짐.
    // 5. 2개 이상의 R이 같은 칸에 있으면, 해당 칸 R 모두 파괴
// => 출력
    // 입력으로 주어진 방향대로 I가 움직였을 때, 보드의 상태
    // 중간에 지면, 몇 번째 움직임에서 죽는지 출력 ("kraj X")
// => 풀이
    // 하란대로!
    // 근데, 모든 미친 아두이노들이 동시에 움직이는지..? R 두개 이상이 한칸에 있을 경우에 대한 시점이 명확하지 x
// --------------------
// 1트 : Map은 전부 .으로 두고, 종수는 (JR, JC), 미친아두이노들은 Robots에 loc들을 배열로 저장해서 풀었는데 이런 참조 과정들이 시간초과를 낸걸까?
// 2트 : 미친 아두이노들 좌표를 Robots 배열로 안빼고 Map 내에서 돌려보자


let Map;
let R, C;
let Orders;
let JR, JC;
let Robots = [];
const dr = [0, 1, 1, 1, 0, 0, 0, -1, -1, -1]; // _, 좌하, 하, 우하, 좌, 가만, 우, 좌상, 상, 우상
const dc = [0, -1, 0, 1, -1, 0, 1, -1, 0, 1];

const Loc = function(r, c) {
    this.r = r;
    this.c = c;
}

const isSameIR = (i) => {
    let isEnd = Robots.some(loc => {
        if(JR === loc.r && JC === loc.c) {
            console.log(`kraj ${i+1}`);
            return true;
        }
    })

    return isEnd;
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [R, C] = input.shift().split(' ').map(Number);
    Orders = input.pop().split('').map(Number);
    Map = input.map(row => row.split(''));

    // 종수 위치 확인
    Map.some((row, r) => row.some((el, c) => {
        if(el === 'I') {
            Map[r][c] = '.';
            JR = r;
            JC = c;
            return true;
        }
    }))

    // 게임 시작
    for(let i=0; i<Orders.length; i++) {
        // 1. I가 order 대로 움직임
        JR += dr[Orders[i]];
        JC += dc[Orders[i]];
        
        // 2. I가 R 자리로 이동하면 게임 끝. 짐.
        if(Map[JR][JC] === 'R') {
            console.log(`kraj ${i+1}`);
            return;
        }
        
        // 3. R은 8방 중 I와 가장 가까워지는 방향(|r1-r2| + |s1-s2|가 가장 작은)으로 한 칸 이동.
        let newLocR = []; // 이동한 R 좌표들 저장
        for(let r=0; r<R; r++) {
            for(let c=0; c<C; c++) {
                if(Map[r][c] === '.') continue;

                // I와 가장 가까운 곳으로 이동
                Map[r][c] = '.';
                let min = Number.MAX_SAFE_INTEGER;
                let toR, toC;
                for(let d=0; d<10; d++) {
                    const goR = r + dr[d];
                    const goC = c + dc[d];
                    let dis = Math.abs(JR - goR) + Math.abs(JC - goC);
                    if(min > dis) {
                        min = dis;
                        toR = goR;
                        toC = goC;
                    }
                }
                newLocR.push(new Loc(toR, toC));

                // 4. R이 I 자리로 이동하면 게임 끝. 짐.
                if(toR === JR && toC === JC) {
                    console.log(`kraj ${i+1}`);
                    return;
                }
            }
        }
        
        // 5. 2개 이상의 R이 같은 칸에 있으면, 해당 칸 R 모두 파괴
        let isBoom = []; // 파괴된 곳의 좌표들 저장
        newLocR.forEach(loc => {
            if(Map[loc.r][loc.c] === '.') Map[loc.r][loc.c] = 'R';
            else isBoom.push(loc);
        })
        isBoom.forEach(loc => {
            Map[loc.r][loc.c] = '.';
        })
    }

    // 게임에서 이긴 경우
    Map[JR][JC] = 'I';
    console.log(Map.map(row => row.join('')).join('\n'));
}

main();