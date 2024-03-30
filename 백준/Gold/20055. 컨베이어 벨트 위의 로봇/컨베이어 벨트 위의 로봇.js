// 컨베이어 벨트
    // 1 ~ 2N
    // 각 칸은 내구도가 있음 Ai
    // 1번칸 : 올리는 위치
    // N번칸 : 내리는 위치
    // 1번칸에만 올릴 수 있고, N에 가면 즉시 내림.
// 로봇
    // 컨베이어 벨트 위에서 스스로 이동 가능
    // 올리는위치에 올리거나, 로봇이 어떤 칸으로 이동하면 그 칸 내구도는 1 감소
// 과정
    // 1. 벨트가 로봇과 함께 한 칸 회전
    // 2. 가장 먼저 올라간 로봇부터, 회전 방향으로 한 칸 이동할 수 있다면 이동 (없다면 가만히)
    //    (이동하려는 칸에 로봇이 없고, 내구도가 1이상 이어야 함)
    // 3. 올리는 위치에 로봇을 올림 (올리는 칸 내구도가 0이 아닐때)
    // 4. 내구도가 0인 칸 개수가 K개 이상이면 종료. 아니면 1로 돌아감
// => 출력
    // 종료되었을 때 몇 번째 단계가 진행중이었는지
// => 풀이
    // 몇번째 단계인지 세면서 순서대로 구현
    // 컨베이어벨트 돌릴 때 뒤에거 빼서 앞으로 unshift 하면 시간초과걸릴수도 있어서 인덱스를 옮기는 방식으로.. ㅜ

let N, K;
let Negu;
let Robots;

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [N, K] = input.shift().split(' ').map(Number);
    Negu = input.shift().split(' ').map(Number);
    Robots = new Array(N).fill(false);

    let depth = 0;
    let cntZero = 0;
    while(true) {
        depth ++;

        // 1. 벨트가 로봇과 함께 한 칸 회전
        Negu.unshift(Negu.pop());
        Robots.pop();
        Robots.unshift(false);
        Robots[N-1] = false;
        
        // 2. 가장 먼저 올라간 로봇부터, 회전 방향으로 한 칸 이동할 수 있다면 이동 (없다면 가만히)
        //    (이동하려는 칸에 로봇이 없고, 내구도가 1이상 이어야 함)
        for(let i=N-2; i>=0; i--) {
            if(Robots[i] && !Robots[i+1] && Negu[i+1] >= 1) {
                Robots[i] = false;
                Robots[i+1] = i+1 < N-1 ? true : false;

                if(--Negu[i+1] === 0) cntZero ++;
            }
        }
        
        // 3. 올리는 위치에 로봇을 올림 (올리는 칸 내구도가 0이 아닐때)
        if(Negu[0] > 0) {
            Robots[0] = true;
            if(--Negu[0] === 0) cntZero ++;
        }

        // 4. 내구도가 0인 칸 개수가 K개 이상이면 종료. 아니면 1로 돌아감
        if(cntZero >= K) break;
    }

    console.log(depth);
}

main();