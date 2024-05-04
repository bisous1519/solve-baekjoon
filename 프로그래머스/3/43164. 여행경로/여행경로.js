// ICN 에서 출발 -> 주어진 항공권을 모두 이용하여 여행함
// => 출력
    // 방문하는 공항경로
    // 경로가 2개 이상이면, 알파벳 순서가 앞서는 경로
    // -> 객체의 value로 저장되는 배열을 정렬해서, 가장먼저 찾는 경로를 출력
// => 풀이
    // depth가 티켓 갯수와 같아야됨
// --------------
// 항공권에 중복 없다는 말이 없대,, ㅎ 맞네~
// 동일한 항공권이 여러개 있을 수 있다고 생각하고 풀어야함.
/*
// --Tickets--
ICN : [ATL, SFO]
SFO : [ATL]
ATL : [ICN, SFO]
*/

let Tickets = {};
let ticketLen;
let isFin = false;
let answer;

const dfs = (from, route, isVisited) => {
    if(isFin) return;
    if(route.length === ticketLen + 1) {
        answer = [...route];
        isFin = true;
        return;
    }
    
    Tickets[from]?.forEach(to => {
        const cnt = isVisited[from + to];
        if(cnt > 0) {
            dfs(to, [...route, to], {...isVisited, [from + to] : cnt - 1});
        }
    })
    
}

function solution(tickets) {
    ticketLen = tickets.length;
    
    // Tickets 연결리스트 같은 객체 구성
    tickets.forEach(row => {
        const [from, to] = row;
        if(Tickets[from]) Tickets[from].push(to);
        else Tickets[from] = [to];
    })
    
    // 가장먼저 찾는 루트가 알파벳순으로 앞서도록 정렬
    for(let key in Tickets) {
        let arr = Tickets[key];
        Tickets[key] = arr.sort();
    }
    
    // 티켓 사용여부 체크 객체 구성
    let isVisited = {};
    tickets.forEach(row => {
        const [from, to] = row;
        // isVisited = {...isVisited, [from + to] : false}; // 단순히 TF로 하면 안됨. 같은 티켓이 여러장 있을 수 있으므로.
        const cnt = isVisited[from + to];
        isVisited = {...isVisited, [from + to] : cnt ? cnt + 1 : 1}; // 사용 안한 티켓이 각각 몇개 남았는지를 저장
    })
    
    // dfs
    dfs('ICN', ['ICN'], {...isVisited});
    
    return answer;
}