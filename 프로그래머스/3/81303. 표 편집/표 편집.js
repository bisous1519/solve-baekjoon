let answer; // 각 행 삭제여부 담아둘 배열
let history = []; // 삭제된 행 인덱스 담아둘 스택
let curNode; // 현재 위치 (인덱스)

const Node = function(idx, prev) {
    this.idx = idx;
    this.prev = prev;
    this.next;
}

const cmdD = (num) => {
    for(let i=0; i<num; ) {
        
    }
}


function solution(n, k, cmd) { 
    answer = new Array(n).fill("O");
    cursor = k;
    
    // 양방향 연결리스트 생성
    let prevNode = new Node(0);
    for(let i=1; i<n; i++) {
        const newNode = new Node(i, prevNode);
        prevNode.next = newNode;
        prevNode = newNode;
        
        if(i === k) {
            curNode = newNode;
        }
    }
    
    // 명령 하나씩 돌면서 실행
    cmd.forEach(el => {
        const [curCmd, num] = el.split(' ');
        let i = 0;
        switch(curCmd){
            case 'U':
                while(i < num && curNode.prev){
                    curNode = curNode.prev;
                    i++;
                }
                break;
            case 'D':
                while(i < num && curNode.next){
                    curNode = curNode.next;
                    i++;
                }
                break;
            case 'C':
                history.push(curNode);
                const prev = curNode.prev;
                const next = curNode.next;
                if(prev && next){
                    prev.next = next;
                    next.prev = prev;
                    curNode = next;
                }else if(prev){
                    prev.next = null;
                    curNode = prev;
                }else if(next){
                    next.prev = null;
                    curNode = next;
                }
                break;
            case 'Z':
                const node = history.pop();
                const prevNode = node.prev;
                const nextNode = node.next;
                if(prevNode){
                    prevNode.next = node;
                }
                if(nextNode){
                    nextNode.prev = node;
                }
                break;
        }
    })
    
    // history에 있는 애들만 answer에서 X로 바꿈
    history.map(node => {
        answer[node.idx] = 'X';
    })
    
    return answer.join('');
}