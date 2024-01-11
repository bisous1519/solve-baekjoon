// => 출력
//    주어진 귤 중에서 k개를 고를 때 귤 크기 종류가 최소가 되는 개수
// => 풀이
//    각 크기가 몇개인지 카운트함

function solution(k, tangerine) {
    let tan = new Map();
    
    tangerine.forEach(el => {
        const cnt = tan.has(el) ? tan.get(el) + 1 : 1;
        tan.set(el, cnt);
    });
    
    let cnt = [];
    tan.forEach((value, key) => {
        cnt.push(value);
    })
    
    cnt.sort((a, b) => b - a);
    console.log(cnt)
    for(let i=0, sum=0; i<cnt.length; i++) {
        sum += cnt[i];
        if(sum >= k) return i + 1;
    }
}