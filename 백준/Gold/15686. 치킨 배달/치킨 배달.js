// 도시 N*N
//   - 0 : 빈칸
//   - 1 : 집
//   - 2 : 치킨집
// 치킨 거리
//   - 집과 가장 가까운 치킨집 사이의 거리
//   - |r1 - r2| + |c1 - c2|
// 도시의 치킨 거리
//   - 모든 집의 치킨 거리의 합
// 가장 수익을 많이 낼 수 있는 치킨집 M개를 고르고 나머지는 모두 폐업
// => 출력
//    M개 고른 후 도시 치킨 거리의 최솟값
// => 풀이
//    - 모든 치킨집의 위치를 넣은 배열과, 모든 집 위치를 넣은 배열을 만듦
//    - 치킨집 중 m개 고르고 
//      -> 각 치킨집을 돌면서
//      -> 치킨집에서 각 집까지의 거리들을 측정
//      -> 각 집의 치킨 거리 갱신
//      => 도시 치킨 거리 갱신
//    - m===6 -> 치킨집갯수Cm * (m*50) = 1716 * 30 = 5만

let N, M;
let Map;
let Chicken = [];
let House = [];
let chickenLen;
let houseLen;
let MinCityDis = Number.MAX_SAFE_INTEGER;

const Loc = function(r, c) {
    this.r = r;
    this.c = c;
}

const measure = (selected) => {
    let dis = new Array(houseLen).fill(Number.MAX_SAFE_INTEGER);

    // 각 치킨집에서 모든 집을 돌면서 각 집의 치킨 거리 구하기
    selected.forEach(cIdx => {
        const cLoc = Chicken[cIdx];
        House.forEach((hLoc, hIdx) => {
            const d = Math.abs(cLoc.r - hLoc.r) + Math.abs(cLoc.c - hLoc.c);
            if(dis[hIdx] > d) {
                dis[hIdx] = d;
            }
        })
    })

    // 도시 치킨거리 갱신
    let cityDis = 0;
    dis.forEach(el => cityDis += el)
    MinCityDis = Math.min(MinCityDis, cityDis);
}

const combi = (start, nth, selected) => {
    if(nth === M) {
        // 각 치킨집 돌면서 모든 집의 치킨 거리 측정
        measure(selected);

        return;
    }

    for(let i=start; i<chickenLen; i++) {
        selected[nth] = i;
        combi(i+1, nth+1, [...selected]);
    }
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [N, M] = input.shift().split(' ').map(Number);
    Map = input.map(row => row.split(' ').map(Number));

    // 모든 치킨집 위치와 집 위치 넣은 배열 만들기
    Map.forEach((row, r) => row.forEach((el, c) => {
        if(el === 1) House.push(new Loc(r, c));
        else if(el === 2) Chicken.push(new Loc(r, c));
    }))
    houseLen = House.length;
    chickenLen = Chicken.length;

    // 하고많은 치킨집중에 m개 골라보자
    combi(0, 0, new Array(M).fill(0));
    
    console.log(MinCityDis);
}

main();