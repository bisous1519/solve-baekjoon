// 소설 여러장 => 합쳐서 한 개의 파일
//   두 개를 합쳐나가는 방식으로.
//   두 파일 크기의 합 : 두 개 파일을 합칠 때 필요한 비용(시간 등)
//   => 최종적으로 한 개 파일로 만들었을 때 필요한 비용의 총 합
// 작은숫자부터 합치는게 최적!?
//   => 이유는 : 앞서 더한 숫자를 계속 중복적으로 다시 더하므로 작은수 부터 더해야 중복되는 값이 더 작다!
//   근데 이제 무조건 작은숫자부터 차례로 더한다기보다
//      그때그때 상황에서 작은 숫자 두개를 차례로 더함!
//      => 우선순위큐! 또는,, 민힙?

let T;

const PriorityQueue = function() {
    this.data = [];

    this.length = () => this.data.length;

    getParentIdx = (idx) => Math.floor((idx - 1) / 2);
    getLeftChildIdx = (idx) => idx * 2 + 1;
    getRightChildIdx = (idx) => idx * 2 + 2;

    this.enqueue = (value) => {
        this.data.push(value);
        heapifyUp();
    }

    this.dequeue = () => {
        if(this.length() === 0) return undefined;
        else if(this.length() === 1) return this.data.pop();
        else {
            const head = this.data[0];
            this.data[0] = this.data.pop();
            heapifyDown();

            return head;
        }
    }

    heapifyUp = () => {
        // 배열의 가장 끝에 넣고 min heap의 형태를 갖추도록 조물조물함
        let idx = this.length() - 1;
        const lastNode = this.data[idx];

        while(idx > 0) {
            let parentIdx = getParentIdx(idx);
            if(this.data[parentIdx] > lastNode) {
                this.data[idx] = this.data[parentIdx];
                idx = parentIdx;
            }else break;
        }

        this.data[idx] = lastNode;
    }

    heapifyDown = () => {
        let idx = 0;
        const head = this.data[0];
        const length = this.length();

        while(getLeftChildIdx(idx) < length) {
            const leftIdx = getLeftChildIdx(idx);
            const rightIdx = getRightChildIdx(idx);

            const smallerIdx =
                rightIdx < length && this.data[leftIdx] > this.data[rightIdx]
                ? rightIdx
                : leftIdx;

            if(this.data[smallerIdx] < head) {
                this.data[idx] = this.data[smallerIdx];
                idx = smallerIdx;
            } else break;  
        }

        this.data[idx] = head;
    }
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    T = +input.shift();

    let answer = '';
    for(let t=1; t<=T; t++) {
        const N = +input.shift();

        // 데이터를 우선순위 큐에 넣기
        let pq = new PriorityQueue();
        input.shift().split(' ').forEach(el => {
            pq.enqueue(+el);
        })
        
        // 하나씩 뽑으면서 최소비용 계산
        let sum = 0;
        while(pq.length() > 0) {
            const file1 = pq.dequeue();
            const file2 = pq.dequeue();

            if(file1 && file2) {
                const newFile = file1 + file2;
                pq.enqueue(newFile);
                sum += newFile;
            }
        }

        answer += sum + '\n';
    }

    console.log(answer);
}

main();