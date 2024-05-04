// 코스요리 메뉴
//   - 최소 2가지 이상의 단품메뉴로 구성
//   - 후보 : 최소 2명 이상의 손님으로부터 주문된 조합
//           -> 그 중에 가장 많이 주문된 조합만 코스요리가 됨!
//              이거때문에 문제 이해하는데 오래걸림 ㅠ
// => 출력
//    새로 추가하게 될 코스요리의 메뉴 구성
// => 풀이
//    - 조합?
//      10C5(=252) * 20 = 5040
//      course 길이 만큼 반복 => 5040 * 10 = 50400
//    - orders 안에있는 문자열 정렬필요
//    - 각 코스 개수만큼 order 전부 돌면서 조합 만들고
//      만들어지는 조합마다 courseMap에 넣음 (없었으면 : 1, 있으면 : ++)
//      - courseMap 안의 요소 하나씩 돌면서 최댓값 갱신
//      - 최댓값인 조합 result에 넣음

let Orders = [];
let courseLen;
let courseMap;
let order;

const combi = (start, nth, selected) => {
    if(nth === courseLen) {
        let resultStr = selected.join('');
        if(courseMap[resultStr]) courseMap[resultStr] ++;
        else courseMap[resultStr] = 1;
        return;
    }
    
    for(let i=start; i<order.length; i++) {
        selected[nth] = order[i];
        combi(i + 1, nth + 1, [...selected]);
    }
    
}

function solution(orders, course) {
    // 조합 만들고 Map에 넣기위해 orders에 있는 문자열들 정렬 필요
    orders.forEach(str => {
        Orders.push(str.split('').sort().join(''));
    })
    
    // course 각 길이만큼씩 조합 만들어 답 구하기
    let answer = [];
    course.forEach(el => {
        courseLen = el;
        courseMap = {};
        
        // 조합만들고 나온개수 세기
        Orders.forEach(str => {
            order = str.split('');
            combi(0, 0, new Array(courseLen));
        })
        
        // 최소 2이상 선택된 메뉴중 가장 많이 선택된 메뉴 개수 구하기
        let max = Math.max(...Object.values(courseMap));
        if(max > 1) {
            for(let str in courseMap) {
                if(courseMap[str] === max) answer.push(str);
            }
        }
    })
    
    // answer 정렬
    answer.sort();
    
    return answer;
}