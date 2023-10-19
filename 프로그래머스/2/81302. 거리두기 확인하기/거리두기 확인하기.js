const dr = [-1, 1, 0, 0]; // 위아오왼
const dc = [0, 0, 1, -1];

const isIn = (r, c) => {
    return 0<=r && r<5 && 0<=c && c<5;
}

const checkDis = (place) => {
    let map = place.map(row => row.split(''));
    let student = [];
    
    // 응시자 위치들을 student에 넣음
    for(let r=0; r<5; r++) {
        for(let c=0; c<5; c++) {
            if(map[r][c] === 'P') {
                student.push([r, c]);
            }
        }
    }
    
    while(student.length > 0) {
        const [r, c] = student.shift();
        
        for(let d=0; d<4; d++) {
            let goR = r + dr[d];
            let goC = c + dc[d];
            
            if(!isIn(goR, goC)) continue;
            if(map[goR][goC] === 'X') continue;
            if(map[goR][goC] === 'P') return 0;
            
            // 빈테이블 일 때
            for(let i=0; i<4; i++) {
                let moreR = goR + dr[i];
                let moreC = goC + dc[i];
                
                if(!isIn(moreR, moreC)) continue;
                if(moreR === r && moreC === c) continue;
                if(map[moreR][moreC] === 'P') return 0;
            }
        }
    }
    
    return 1;
}

function solution(places) {
    let answer = [];
    for(let i=0; i<5; i++) {
        answer.push(checkDis(places[i]));
    }
    
    return answer;
}