// N*N 도시
//   (1, 1) ~ (r, c)
//   1*1 각 칸은 아래 중 하나
//     - 0 : 빈 칸
//     - 1 : 집
//     - 2 : 치킨 집
// 치킨 거리
//   : 집과 가장 가까운 치킨집 사이의 거리
//   (r1, c1)과 (r2, c2) 사이의 거리는 |r1-r2| + |c1-c2|
//   각 집은 치킨 거리를 가짐
// 도시의 치킨 거리
//   : 모든 집의 치킨 거리의 합
// 치킨 집 중 최대 M개를 고르고 나머지 치킨집은 모두 폐업
// -> 도시의 치킨 거리가 가장 작게 되는!
//
// => 치킨집 위치를 리스트에 저장
//    치킨집 고르는 조합! (1개 ~ M개)
//    조합 고를 때 마다 도시의 치킨 거리를 구함?

let Map;
let N, M;
let cList; // 치킨집 리스트
let cLen;
let hList; // 집 리스트
let cityDis = Number.MAX_SAFE_INTEGER;
const [BLANK, HOME, CHICKEN] = [0, 1, 2];

const Loc = function(r, c) {
    this.r = r;
    this.c = c;
}

const combi = (nth, isSelected, m) => {
    if(nth === cLen) {
        if(0 < m && m <= M) {
            // console.log(isSelected);
            // 선택된 치킨집 좌표 리스트 만들기
            let selectedClist = [];
            isSelected.forEach((el, idx) => {
                if(el) {
                    selectedClist.push(cList[idx]);
                }
            })

            // 각 집마다 치킨거리 구해서 더하기
            let sum = 0;
            for(hLoc of hList) {
                let min = Number.MAX_SAFE_INTEGER;
                selectedClist.forEach(cLoc => {
                    const dis = Math.abs(hLoc.r - cLoc.r) + Math.abs(hLoc.c - cLoc.c);
                    min = Math.min(min, dis);
                })

                sum += min;
                // 아직 치킨거리 누적하고있는데 도시거리보다 크면 더이상 의미없음
                if(sum >= cityDis) {
                    sum = -1;
                    break;
                }
            }

            // 도시의 치킨거리 구하기
            if(sum !== -1) {
                cityDis = Math.min(cityDis, sum);
            }
        }
        return;
    }

    isSelected[nth] = true;
    combi(nth + 1, [...isSelected], m + 1);

    isSelected[nth] = false;
    combi(nth + 1, [...isSelected], m);
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    [N, M] = input.shift().split(' ').map(el => +el);
    Map = input.map(row => row.split(' ').map(el => +el));

    cList = [];
    hList = [];
    Map.forEach((row, r) => row.forEach((el, c) => {
        // 치킨집 위치 리스트 저장
        if(el === CHICKEN) {
            cList.push(new Loc(r, c));
        }
        // 집 위치 리스트 저장
        else if(el === HOME) {
            hList.push(new Loc(r, c));
        }
    }));
    cLen = cList.length;

    // 폐업안시키는 치킨집 고르기
    combi(0, new Array(cLen).fill(false), 0);

    console.log(cityDis);
}

main();