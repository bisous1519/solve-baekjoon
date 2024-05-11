// begin을 target으로 바꿀거임

let Begin, Target;
let Words;
let wordLen;

const bfs = () => {
    let queue = [Begin];
    let isVisited = new Array(Words.length).fill(false);
    
    let qIdx = 0;
    let turn = 0;
    while(qIdx < queue.length) {
        let size = queue.length - qIdx;
        turn ++;
        while(size -- > 0) {
            const cur = queue[qIdx ++];
            console.log(turn, cur)
            
            let wIdx = 0;
            for(let word of Words) {
                let diff = 0;
                for(let i=0; i<wordLen; i++) {
                    if(cur.charAt(i) !== word.charAt(i)) diff ++;
                    if(diff > 1) break;
                }
                
                if(diff === 1) {
                    if(word === Target) return turn;
                    if(!isVisited[wIdx]) {
                        queue.push(word);
                        isVisited[wIdx] = true;
                    }
                }
                
                wIdx ++;
            }
        }
    }
    
    return 0;
}

function solution(begin, target, words) {
    [Begin, Target] = [begin, target];
    Words = words;
    wordLen = Begin.length;
    
    if(Words.filter(word => word === Target).length === 0) return 0;
    return bfs();
}