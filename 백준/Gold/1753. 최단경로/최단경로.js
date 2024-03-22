// 방향그래프
// 가중치 o
// 주어진 시작점에서 다른 모든 정점으로의 최단 경로
// 서로 다른 두 정점 사이에 간선이 여러개 있을 수도 있음
// => 다익스트라

let V, E;
let List;
let startNum;

const Node = function(cost, num) {
    this.cost = cost;
    this.num = num;
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
            if(this.data[pIdx].cost > lastNode.cost) {
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
                = rcIdx < this.data.length && this.data[rcIdx].cost < this.data[lcIdx].cost
                ? rcIdx
                : lcIdx;

            if(head.cost > this.data[smallerCIdx].cost) {
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

    [V, E] = input.shift().split(' ').map(Number);
    startNum = +input.shift();

    List = new Array(V+1).fill(null).map(() => []);
    input.forEach(row => {
        const [from, to, cost] = row.split(' ').map(Number);
        List[from].push(new Node(cost, to));
    })

    // 다익스트라
    let minCost = new Array(V+1).fill(Number.MAX_SAFE_INTEGER);
    let pq = new pQueue();
    minCost[startNum] = 0;
    pq.push(new Node(0, startNum));

    while(pq.size() > 0) {
        const cur = pq.pop();

        List[cur.num].forEach(node => {
            const newCost = cur.cost + node.cost;
            if(minCost[node.num] > newCost) {
                minCost[node.num] = newCost;
                pq.push(new Node(newCost, node.num));
            }
        })
    }

    let answer = '';
    minCost.shift();
    minCost.forEach(cost => {
        if(cost !== Number.MAX_SAFE_INTEGER) answer += cost + '\n';
        else answer += 'INF\n';
    })

    console.log(answer);
}

main();