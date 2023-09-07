// 벽장 N개
//   문 2개 열려있음
//   옆으로 한 칸 씩 밀어서 열 수 있음
//   앞으로 열어볼 문 M개
// 재..귀..
//   moveDoor(m번째 문을, a로 열거임(현재 내기준 왼쪽에 열린 문 idx), 옮긴개수)
//   moveDoor(m번째 문을, b로 열거임(현재 내기준 오른쪽에 열린 문 idx), 옮긴개수)

let N;
let M;
let Order;
let min = Number.MAX_SAFE_INTEGER;

const moveDoor = (curIdx, doorA, doorB, cnt) => {
    if(curIdx === M) {
        min = Math.min(min, cnt);
        return;
    }

    if(cnt >= min) return;

    const curDoor = Order[curIdx];
    const doorL = Math.min(doorA, doorB);
    const doorR = Math.max(doorA, doorB);

    const diffL = Math.abs(curDoor - doorL);
    const diffR = Math.abs(doorR - curDoor);

    // 열려있는 문과 지금 열려고 하는 문이 같은 경우
    if(doorL === curDoor || doorR === curDoor) {
        moveDoor(curIdx + 1, doorL, doorR, cnt);
    }

    // 열려있는 문 두개 사이에 내가 있는 경우
    else if(doorL <  curDoor && curDoor < doorR) {
        // 둘 다로 열어봄
        moveDoor(curIdx + 1, curDoor, doorR, cnt + diffL);
        moveDoor(curIdx + 1, doorL, curDoor, cnt + diffR);
    }

    // 몰려있는 경우
    else {
        if(curDoor < doorL) {
            moveDoor(curIdx + 1, curDoor, doorR, cnt + diffL);
        }else {
            moveDoor(curIdx + 1, doorL, curDoor, cnt + diffR);
        }
    }
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    N = +input.shift();
    const [doorA, doorB] = input.shift().split(' ').map(el => +el);
    M = +input.shift();
    Order = input.map(el => +el);

    moveDoor(0, doorA, doorB, 0);

    console.log(min);
}

main();