// 배열 A (3*3)
// 1초가 지날 때 마다 배열에 연산이 적용됨
//   - 행 개수 >= 열 개수 일 때
//       => R연산
//        : 모든 행에 대해 정렬 수행
//   - 행 개수 < 열 개수 일 때
//       => C연산
//        : 모든 열에 대해 정렬 수행
// 각 행 또는 열을 정렬할 때
//   - 각 행 또는 열의
//     (수, 등장 횟수) 해서
//     등장 횟수의 오름차순으로
//        -> 여러가지면 수의 오름차순으로 정렬

let X, Y, K;
let R, C;
let Map;

const arrSort = (arr) => {
    let cnt = {};
    arr.forEach(el => {
        if(el !== 0) {
            if(cnt[el]) {
                cnt[el] = cnt[el] + 1;
            } else cnt[el] = 1;
        }
    })

    let cntArr = [];
    for(const num in cnt) {
        cntArr.push([num, cnt[num]]);
    }
    cntArr.sort((a, b) => {
        if(a[1] === b[1]) {
            return a[0] - b[0];
        } else return a[1] - b[1];
    })

    let newArr = [];
    cntArr.forEach(el => {
        newArr.push(+el[0]);
        newArr.push(+el[1]);
    })

    return newArr;
}

const fillZero = (newMap) => {
    let maxLen = 0;
    newMap.forEach(row => {
        maxLen = Math.max(maxLen, row.length);
    })

    Map = [];
    newMap.forEach(row => {
        const newRow = [...row, ...new Array(maxLen - row.length).fill(0)];
        Map.push(newRow);
    })
}

const Rsort = () => {
    // console.log('------r')
    let newMap = [];

    Map.forEach(row => {
        newMap.push(arrSort(row));
    })

    fillZero(newMap);
}

const Csort = () => {
    // console.log('------c')
    
    let newMap = [];
    for(let c=0; c<C; c++) {
        let col = [];
        for(let r=0; r<R; r++) {
            col.push(Map[r][c]);
        }
        newMap.push(arrSort(col));
    }

    // Map에 새로 넣어서 옴
    fillZero(newMap);

    newMap = Map.map(row => [...row]);
    Map = new Array(newMap[0].length).fill(null).map(() => new Array(newMap.length));
    newMap.forEach((row, r) => {
        row.forEach((el, c) => {
            Map[c][r] = el;
        })
    })
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [X, Y, K] = input.shift().split(' ').map(el => +el);
    Map = input.map(row => row.split(' ').map(el => +el));
    
    let ans = -1;
    for(let k=0; k<=100; k++) {
        R = Map.length;
        C = Map[0].length;

        // console.log(Map.map(row => row.join(' ')).join('\n'))
        if(X <= R && Y <= C && Map[X - 1][Y - 1] === K) {
            ans = k;
            break;
        }

        // R연산
        if(R >= C) Rsort();
        
        // C연산
        else Csort();
    }

    console.log(ans);
}

main();