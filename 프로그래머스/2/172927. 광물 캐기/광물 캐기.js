// 광물캐기
// - 규칙을 지키면서 최소한의 피로도로 광물을 캐자
// - 각 곡갱이는 종류 상관없이 광물 5개를 캐면 더 이상 사용할 수 없음
// => 출력
//    작업을 끝내기까지 필요한 최소한의 피로도
//    (더 사용할 곡괭이가 없거나, 모든 광물을 캘 때 까지 반복)
// => 풀이
//    dfs
//    각 깊이마다 곡괭이를 하나씩 선택해서 캐고, 끝났을 때의 피로도를 갱신

let Minerals;
let mineralLen;
let min = Number.MAX_SAFE_INTEGER;

const goDia = (nthM) => {
    if(nthM + 5 <= mineralLen) return 5;
    return mineralLen - nthM;
}

const goIron = (nthM) => {
    let tired = 0;
    for(let i=nthM; i<nthM+5 && i<mineralLen; i++) {
        if(Minerals[i] === 'diamond') tired += 5;
        else tired ++;
    }
    return tired;
}

const goStone = (nthM) => {
    let tired = 0;
    for(let i=nthM; i<nthM+5 && i<mineralLen; i++) {
        if(Minerals[i] === 'diamond') tired += 25;
        else if(Minerals[i] === 'iron') tired += 5;
        else tired ++;
    }
    return tired;
}

const dfs = (dia, iron, stone, nthM, tired) => {
    if(nthM >= mineralLen || dia + iron + stone === 0) {
        min = Math.min(min, tired);
        return;
    }
    
    if(dia > 0) dfs(dia - 1, iron, stone, nthM + 5, tired + goDia(nthM));
    if(iron > 0) dfs(dia, iron - 1, stone, nthM + 5, tired + goIron(nthM));
    if(stone > 0) dfs(dia, iron, stone - 1, nthM + 5, tired + goStone(nthM));
}

function solution(picks, minerals) {
    Minerals = minerals;
    mineralLen = minerals.length;
    
    dfs(picks[0], picks[1], picks[2], 0, 0);
    
    return min;
}