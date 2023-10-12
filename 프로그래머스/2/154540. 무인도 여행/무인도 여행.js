let R;
let C;
let Map;
let visited;
const dr = [-1, 1, 0, 0]; // 위 아래 왼 오
const dc = [0, 0, -1, 1];

// bfs로 연결되어있는 요소들의 합을 구함
const bfs = (startX, startY) => {
    const queue = [[startX, startY]];
    visited[startX][startY] = true;
    let totalDays = 0;

    while (queue.length) {
      const [curX, curY] = queue.shift();
      totalDays += Number(Map[curX][curY]); 

      for (let i = 0; i < 4; i++) {
        const goR = curX + dr[i];
        const goC = curY + dc[i];

        if (goR < 0 || goR >= R || goC < 0 || goC >= C) continue;

        if (visited[goR][goC] || Map[goR][goC] === "X") continue;

        visited[goR][goC] = true;
        queue.push([goR, goC]);
      }
    }

    return totalDays;
};

function solution(maps) {
  R = maps.length;
  C = maps[0].length;

  Map = [...maps.map(row => [...row])];
  visited = Array.from(Array(R), () => Array(C).fill(false));
  
  const answer = [];

  for (let i = 0; i < R; i++) {
    for (let j = 0; j < C; j++) {
      // x가 아니면서 방문을 안했다면 bfs함수 실행해서 answer에 push
      if (maps[i][j] !== "X" && !visited[i][j]) {
        const days = bfs(i, j);
        answer.push(days);
      }
    }
  }

  // 오름차순 정렬
  answer.sort((a, b) => a - b);

  if (answer.length === 0) return [-1];
  else return answer;
}