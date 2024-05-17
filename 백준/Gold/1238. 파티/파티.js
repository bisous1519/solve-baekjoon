// N명, X번 마을에 모임
// 단방향 도로 M개
    // 각 도로를 지날 때 Ti의 시간 소비
// => 출력
    // N명 중 가장 많은 시간이 걸리는 학생의 소요 시간
// => 풀이
    // 다익스트라
        // 1~N번 각각에서
            // X까지 가는 최소 시간을 구함
            // X에서 그 번호까지 돌아가는 최소 시간을 구함
            // 두개 합의 max 값을 갱신

let N, M, X;
let List;
let max = 0;

const Node = function(to, time) {
    this.to = to;
    this.time = time;
}

const pQueue = function() {
    this.data = [];
    this.size = () => this.data.length;

    getPIdx = (idx) => Math.floor((idx - 1) / 2);
    getLCIdx = (idx) => idx * 2 + 1;
    getRCIdx = (idx) => idx * 2 + 2;

    this.push = (node) => {
        this.data.push(node);
        moveUp();
    }

    this.pop = () => {
        if(this.data.length === 0) return undefined;
        if(this.data.length === 1) return this.data.pop();
        
        const head = this.data[0];
        this.data[0] = this.data.pop();
        moveDown();
        
        return head;
    }

    moveUp = () => {
        let idx = this.data.length - 1;
        const lastNode = this.data[idx];

        while(idx > 0) {
            let pIdx = getPIdx(idx);
            if(this.data[pIdx].time > lastNode.time) {
                this.data[idx] = this.data[pIdx];
                idx = pIdx;
            }
            else break;
        }

        this.data[idx] = lastNode;
    }

    moveDown = () => {
        const head = this.data[0];
        let idx = 0;

        while(getLCIdx(idx) < this.data.length) {
            let lcIdx = getLCIdx(idx);
            let rcIdx = getRCIdx(idx);

            let smallerCIdx
                = rcIdx < this.data.length && this.data[rcIdx].time < this.data[lcIdx].time
                ? rcIdx
                : lcIdx;

            if(head.time > this.data[smallerCIdx].time) {
                this.data[idx] = this.data[smallerCIdx];
                idx = smallerCIdx;
            }
            else break;
        }

        this.data[idx] = head;
    }
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [N, M, X] = input.shift().split(' ').map(Number);

    List = new Array(N+1).fill(null).map(() => []);
    input.forEach(row => {
        const [from, to, time] = row.split(' ').map(Number);
        List[from].push(new Node(to, time));
    })

    // 최소시간 저장 배열 minTime[from][to] : from -> to 걸리는 최소 시간
    let minTime = new Array(N+1).fill(null).map(() => new Array(N+1).fill(Number.MAX_SAFE_INTEGER));
    for(let from=1; from<=N; from++) {
        let pq = new pQueue();
        pq.push(new Node(from, 0));
        minTime[from][from] = 0;

        while(pq.size() > 0) {
            const cur = pq.pop();

            List[cur.to].forEach(node => {
                const newTime = cur.time + node.time;
                if(minTime[from][node.to] > newTime) {
                    minTime[from][node.to] = newTime;
                    pq.push(new Node(node.to, newTime));
                }
            })
        }
    }

    // 모든 학생이 왕복하면서 max 갱신
    for(let n=1; n<=N; n++) {
        max = Math.max(max, minTime[n][X] + minTime[X][n]);
    }

    console.log(max);
}

main();