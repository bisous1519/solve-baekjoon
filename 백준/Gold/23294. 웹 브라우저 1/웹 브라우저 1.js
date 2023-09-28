// 웹 브라우저
//   뒤로가기(Backward)
//   앞으로가기(Frontward)
//   웹페이지 접속(Access)
//   압축(Compress)
// 사용자가 웹 사이트에 접속
//   -> 캐시공간에 웹페이지 정보가 저장됨 (일정 용량 초과시 오래된 페이지가 삭제됨)
//   -> 뒤로가기나 앞으로가기하면 캐시에 저장된 페이지 정보를 불러옴
// 캐시 용량
//   : 뒤로가기공간 + 앞으로가기공간 + 현재접속중인페이지가 사용하고있는 용량
// 압축
//   : 뒤로가기 공간에 같은 번호 페이지가 연속해서 들어오면 하나로 줄이는 기능
// 초기
//   - 뒤, 앞 공간 모두 비어있음
//   - 어떤 페이지에도 접속해있지 않은 상태

// => Q개 작업을 모두 마친 뒤
//    현재 접속한 페이지, 뒤로가기공간, 앞으로가기공간에 저장된 페이지 번호 출력

let N; // 접속할 수 있는 웹페이지 종류 수
let Q; // 수행할 작업 개수
let C; // 최대 캐시 용량
let cap;
let cache = 0;
let back = [];
let front = [];
let frontCache = 0;
let backCache = 0;
let curPage = -1;

const backward = () => {
    if(back.length === 0) return;

    // 현재 페이지 -> 앞으로가기 공간
    front.unshift(curPage);
    frontCache += cap[curPage];
    // 뒤로가기 공간 -> 현재 페이지
    curPage = back.pop();
    backCache -= cap[curPage];
}

const frontward = () => {
    if(front.length === 0) return;

    // 현재 페이지 -> 뒤로가기 공간
    back.push(curPage);
    backCache += cap[curPage];
    // 앞으로가기공간 -> 현재 페이지
    curPage = front.shift();
    frontCache -= cap[curPage];
}

const access = (i) => {
    // 앞으로가기 공간 삭제
    front = [];
    cache -= frontCache;
    frontCache = 0;

    // 현재 페이지 뒤로가기 공간에 추가
    if(curPage !== -1) {
        back.push(curPage);
        backCache += cap[curPage];
    }
    curPage = i;
    cache += cap[i];

    // 최대 캐시 용량 초과
    while(cache > C) {
        const page = back.shift();
        backCache -= cap[page];
        cache -= cap[page];
    }
}

const compress = () => {
    const newBack = back.filter((el, idx) => el !== back[idx - 1]);
    back = [...newBack];
    let newBackCache = 0;
    back.forEach(el => {
        newBackCache += cap[el];
    })

    const diff = backCache - newBackCache;
    backCache = newBackCache;
    cache -= diff;
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');
    
    [N, Q, C] = input.shift().split(' ').map(el => +el);
    cap = input.shift().split(' ').map(el => +el);
    cap.unshift(0);
    // console.log(cap);

    input.forEach(row => {
        const [comm, i] = row.split(' ');
        switch(comm) {
            case 'B' : backward(); break;
            case 'F' : frontward(); break;
            case 'A' : access(i); break;
            case 'C' : compress(); break;
        }
    })

    let answer = '';
    answer += curPage + '\n';
    if(back.length > 0) {
        for(let i = back.length - 1; i >= 0; i--) {
            answer += back[i] + ' ';
        }
    } else answer += -1;
    answer += '\n';
    if(front.length > 0) {
        answer += front.join(' ');
    }else answer += -1;
    answer += '\n';

    console.log(answer);
}

main();