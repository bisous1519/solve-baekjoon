// 송도 -> 락페 : 맥주 한박스 들고 출발
// 맥주 20개
// 50미터에 한 병씩 마심
// 편의점에서 : 빈 병 버리고 새 맥주 삼 (박스에 총 20병 넘을 수 없음)
// 편의점, 집, 락페 좌표가 주어짐
// -> 잘 도착할 수 있나?
// => 테케 하나마다 도착가능: happy, 못함: sad 출력
// 입력
//   T : 테케 (<= 50)
//   N : 편의점 개수 (<= 100)
//   집좌표, 편의점 좌표 N개, 락페 좌표
// 풀이
//   => 맥주 1병당 50미터
//   각 노드에서 다른 노드 갈 때 몇 병씩 필요한지 비용까지 같이해서 연결리스트로 구성 (단, 20병 이상 필요하면 연결x)
//   BFS 돌림 (단, 편의점 도착하면 맥주 20병으로 채움)


let T;
let N;
let locs;
let list;
let answer = '';
const maxBot = 20; // 최대 소지할 수 있는 맥주병 수
const maxLen = 1000; // 20병으로 최대 갈 수 있는 거리
const lenPerBot = 50; // 1병으로 최대 50m 감

const Loc = function(r, c) {
    this.r = r;
    this.c = c;
}

const Node = function(num, cost) {
    this.num = num;
    this.cost = cost;
}

const isConstore = (num) => {
    return 1 <= num && num <= N;
}

const bfs = () => {
    let queue = [];
    let isVisited = new Array(N+2).fill(false);

    queue.push({num: 0, bot: maxBot});
    isVisited[0] = true;

    while(queue.length > 0) {
        let size = queue.length;
        while(size -- > 0) {
            const cur = queue.shift();

            let fin = false;
            list[cur.num].forEach(node => {
                if(node.cost <= cur.bot && !isVisited[node.num]) { // 갈 수 있음
                    // 도착
                    if(node.num === N + 1 || fin) fin = true;

                    // 아직 도착 아님
                    else {
                        let newBot = cur.bot - node.cost;
                        if(isConstore(node.num)) { // 편의점이면 다시 맥주 채워짐
                            newBot = maxBot;
                        }
                        queue.push({num: node.num, bot: newBot});
                        isVisited[node.num] = true;
                    }
                }
            })

            // 도착!
            if(fin) return 'happy';
        }
    }

    // 못도착
    return 'sad';
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');
    
    T = +input.shift();

    for(let t = 1; t <= T; t++) {
        // 입력
        N = +input.shift();

        locs = new Array(N+2);
        for(let n = 0; n < N+2; n++) {
            const [r, c] = input.shift().split(' ').map(el => +el);
            locs[n] = new Loc(r, c);
        }

        // 연결리스트 구성
        list = new Array(N+2).fill(null).map(() => new Array());
        for(let n = 0; n < N + 1; n++) {
            for(let i = n+1; i < N + 2; i++) {
                const r = Math.abs(locs[n].r - locs[i].r);
                const c = Math.abs(locs[n].c - locs[i].c);
                if(r + c <= maxLen) { // 20병 내로 갈 수 있음
                    list[n].push(new Node(i, Number((r + c) / lenPerBot)));
                    list[i].push(new Node(n, Number((r + c) / lenPerBot)));
                }
            }
        }

        // BFS 돌림
        const result = bfs();

        answer += result + '\n';
    }

    console.log(answer);
}

main ();