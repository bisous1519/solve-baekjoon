let N;
let originQueue;
let originChk;
let tempQueue;

function solution(order) {
    N = order.length;
    
    originQueue = new Array(N).fill(null).map((_, idx) => idx + 1);
    originChk = new Array(N+1).fill(true);
    tempQueue = [];
    
    let cnt = 0;
    let front = 0;
    while(cnt < N) {
        const target = order[cnt];
        if(originChk[target]) {
            while(true) {
                const a = originQueue[front++];
                originChk[a] = false;
                
                if(a === target) {
                    cnt++;
                    break;
                }
                
                tempQueue.push(a);
            }
        } else {
            const a = tempQueue.pop();
            
            if(a === target) {
                cnt ++;
            }
            else {
                return cnt;
            }
        }
    }
    
    return cnt;
}